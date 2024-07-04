package com.example.grazercodingtest.data.remote.repository

import com.example.grazercodingtest.data.remote.GrazerApi
import com.example.grazercodingtest.data.remote.response.UserDto

class UserRepositoryImpl(private val authApi: GrazerApi) : UserRepository {
    override suspend fun getUserList(token: String): Result<List<UserDto>> {
        return try {
            val response = authApi.getUsers("Bearer $token")
            if (response.isSuccessful) {
                val userList = response.body()?.data?.users
                if (userList != null) {
                    Result.success(userList)
                } else {
                    Result.failure(Exception("User list is null"))
                }
            } else {
                Result.failure(Exception("Retrieve user list failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}