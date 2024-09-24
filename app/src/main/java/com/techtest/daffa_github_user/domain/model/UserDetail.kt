package com.techtest.daffa_github_user.domain.model

data class UserDetail(
    val company: String,

    val followers: Int,

    val avatarUrl: String,

    val following: Int,

    val name: String,

    val location: String
)