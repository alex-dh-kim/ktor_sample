package com.dh.alex.ktor.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiErrorResponse(
    @SerialName("errorCode")
    val errorCode: Int? = -1,
    @SerialName("message")
    val message: String? = ""
)
