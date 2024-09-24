package com.techtest.daffa_github_user.data.source.remote.response

import com.squareup.moshi.Json

data class UserResponse(

    @Json(name = "login") val username: String,
    @Json(name = "avatar_url") val avatarUrl: String
)
