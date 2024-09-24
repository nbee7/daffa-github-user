package com.techtest.daffa_github_user.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("login") val username: String,
    @field:SerializedName("avatar_url") val avatarUrl: String
)

data class ListUserResponse(
    @field:SerializedName("items")
    val items: List<UserResponse>
)
