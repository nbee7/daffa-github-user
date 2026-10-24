package com.techtest.daffa_github_user.core.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetailResponse(
    @Json(name = "company")
    val company: String?,

    @Json(name = "followers")
    val followers: Int,

    @Json(name = "avatar_url")
    val avatarUrl: String?,

    @Json(name = "following")
    val following: Int,

    @Json(name = "name")
    val name: String?,

    @Json(name = "location")
    val location: String?,

    @Json(name = "id")
    val id: Int
)
