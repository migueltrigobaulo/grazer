package com.example.grazercodingtest.data.remote.repository

import com.example.grazercodingtest.data.remote.GrazerApi
import com.example.grazercodingtest.data.remote.request.LoginRequestDto

class LoginRepositoryImpl(private val authApi: GrazerApi) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = authApi.login(LoginRequestDto(email, password))
            if (response.isSuccessful) {
                val token = response.body()?.auth?.data?.token
                if (token != null) {
                    Result.success(token)
                } else {
                    Result.failure(Exception("Token is null"))
                }
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}