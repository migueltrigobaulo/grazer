package com.example.grazercodingtest.data.remote.repository

import com.example.grazercodingtest.data.remote.response.UserDto

interface UserRepository {
    suspend fun getUserList(token:String): Result<List<UserDto>>
}