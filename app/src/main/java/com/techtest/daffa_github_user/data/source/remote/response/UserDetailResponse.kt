package com.techtest.daffa_github_user.data.source.remote.response

import com.squareup.moshi.Json

data class UserDetailResponse(
    @Json(name = "company")
    val company: String,

    @Json(name = "public_repos")
    val publicRepos: Int,

    @Json(name = "followers")
    val followers: Int,

    @Json(name = "avatar_url")
    val avatarUrl: String,

    @Json(name = "following")
    val following: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "location")
    val location: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "login")
    val login: String
)
