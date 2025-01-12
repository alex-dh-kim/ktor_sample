package com.dh.alex.ktor.network.api

import arrow.core.Either
import com.dh.alex.ktor.common.alias.DHResult
import com.dh.alex.ktor.common.exceptions.UnconnectedNetworkException
import com.dh.alex.ktor.network.constatns.NetworkConstants
import com.dh.alex.ktor.network.di.toResult
import com.dh.alex.ktor.network.model.response.github.GithubRepoResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

interface ApiService {
    suspend fun getRepositories(): DHResult<List<GithubRepoResponse>>
}

internal class ApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : ApiService {
    override suspend fun getRepositories(): DHResult<List<GithubRepoResponse>> {
        try {
            val response: HttpResponse = client.get(NetworkConstants.URI_REPOSITORIES)

            return response.toResult()
        } catch (e: Exception) {
            return Either.Left(UnconnectedNetworkException(e))
        }
    }

}
