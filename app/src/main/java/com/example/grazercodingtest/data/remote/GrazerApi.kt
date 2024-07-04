package com.example.grazercodingtest.data.remote

import com.example.grazercodingtest.data.remote.request.LoginRequestDto
import com.example.grazercodingtest.data.remote.response.LoginResponseDto
import com.example.grazercodingtest.data.remote.response.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GrazerApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): Response<LoginResponseDto>

    @GET("users")
    suspend fun getUsers(@Header("Authorization") authToken: String): Response<UserResponseDto>
}