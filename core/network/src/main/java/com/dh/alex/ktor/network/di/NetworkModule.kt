package com.dh.alex.ktor.network.di

import android.util.Log
import arrow.core.Either
import com.dh.alex.ktor.common.alias.DHResult
import com.dh.alex.ktor.common.exceptions.ApiException
import com.dh.alex.ktor.common.exceptions.SessionExpiredException
import com.dh.alex.ktor.common.exceptions.UnExpectedException
import com.dh.alex.ktor.network.constatns.NetworkConstants
import com.dh.alex.ktor.network.model.response.ApiErrorResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkProvidesModule {

    private const val CONNECT_TIMEOUT_MILLIS = 60_000L
    private const val REQUEST_TIMEOUT_MILLIS = 30_000L
    private const val SOCKET_TIMEOUT_MILLIS = 30_000L

    @Singleton
    @Provides
    fun provideNonAuthenticatedClient(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = false
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NetworkConstants.HOST
                }
                headers {
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Authorization, NetworkConstants.GITHUB_API_KEY)
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_MILLIS
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Ktor Logger", message)
                    }
                }
                // NOTE: Flavor 에 따라 다르게 설정
                LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true          // Json string을 읽기 편하게 만들어줌
                        isLenient = true            // 따옴표 규칙 완화
                        encodeDefaults = true       // null 또는 잘못된 값이 들어간 경우 default property value로 대체
                        ignoreUnknownKeys = true    // Field 값이 없는 경우 무시할지 (모델에 없고, json에 있는 경우 해당 key 무시)
                    }
                )
            }
            //응답의 상태를 기록하는 Observer
            install(ResponseObserver) {
                onResponse { response -> Log.d("HTTP status: ", "${response.status.value}") }
            }
        }
    }
}

internal suspend inline fun <reified T : Any> HttpResponse.toResult(): DHResult<T> {
    val code = status.value
    return when (code) {
        200 -> Either.Right(body<T>())
        else -> {
            try {
                val apiErrorResponse = body<ApiErrorResponse>()

                val message = apiErrorResponse.message.orEmpty()

                val apiException = ApiException(
                    code,
                    message.ifEmpty { "알 수 없는 오류가 발생했습니다." },
                    null
                )

                if (code == 401) {
                    Either.Left(SessionExpiredException(apiException))
                } else {
                    Either.Left(apiException)
                }
            } catch (e: Exception) {
                Either.Left(UnExpectedException(e))
            }
        }
    }
}
