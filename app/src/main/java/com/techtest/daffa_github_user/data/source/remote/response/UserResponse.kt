package com.techtest.daffa_github_user.data.source.remote.response

import com.squareup.moshi.Json

data class UserResponse(

    @Json(name = "items")
    val items: List<UserItem>
)

data class UserItem(
    @Json(name = "login") val username: Int,
    @Json(name = "avatar_url") val avatarUrl: String
)
