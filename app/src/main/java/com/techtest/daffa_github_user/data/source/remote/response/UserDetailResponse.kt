package com.techtest.daffa_github_user.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("id")
    val id: Int
)
