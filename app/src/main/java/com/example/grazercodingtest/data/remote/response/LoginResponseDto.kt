package com.example.grazercodingtest.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("status") val status: Int,
    @SerializedName("status_desc") val statusDesc: String,
    @SerializedName("auth") val auth: AuthDto
)

data class AuthDto(val data: AuthDataDto)
data class AuthDataDto(val token: String)