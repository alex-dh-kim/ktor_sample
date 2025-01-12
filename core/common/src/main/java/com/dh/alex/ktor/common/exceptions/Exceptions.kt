package com.dh.alex.ktor.common.exceptions

sealed class DHException(override val message: String) : Exception(message)

data class ApiException(
    val httpStatusCode: Int,
    override val message: String,
    override val cause: Throwable?
) : DHException(message)

data class UnExpectedException(
    override val cause: Throwable
) : DHException("알 수 없는 오류가 발생했습니다.")

data class UnconnectedNetworkException(
    override val cause: Throwable
) : DHException("네트워크 연결이 꺼져 있습니다.\n셀룰러 데이터 또는 Wi-Fi 연결 후 사용해주세요.")

data class SessionExpiredException(
    val apiException: ApiException
) : DHException(
    if ("Unauthorized".equals(
            apiException.message,
            true
        ) || apiException.message.isBlank()
    ) {
        "세션이 만료되었습니다."
    } else {
        apiException.message
    }
)
