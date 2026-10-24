package com.techtest.daffa_github_user.core.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "login") val username: String,
    @Json(name = "avatar_url") val avatarUrl: String
)

@JsonClass(generateAdapter = true)
data class ListUserResponse(
    @Json(name = "items")
    val items: List<UserResponse>
)
