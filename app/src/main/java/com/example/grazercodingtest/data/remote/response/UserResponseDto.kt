package com.example.grazercodingtest.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("status") val status: Int,
    @SerializedName("status_desc") val statusDesc: String,
    @SerializedName("data") val data: DataDto
)

data class DataDto(
    val users: List<UserDto>,
    val meta: MetaDto
)

data class UserDto(
    val name: String,
    @SerializedName("date_of_birth") val dateOfBirth: Long,
    @SerializedName("profile_image") val profileImage: String
)

data class MetaDto(
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("current_page") val currentPage: Int
)