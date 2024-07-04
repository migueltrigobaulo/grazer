package com.example.grazercodingtest.data.remote.repository

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<String>
}