package com.techtest.daffa_github_user.core.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techtest.daffa_github_user.core.data.local.room.Config.TABLE_USER_DETAIL

@Entity(tableName = TABLE_USER_DETAIL)
class UserDetailEntity(
    @PrimaryKey
    val username: String,

    val company: String,

    val location: String,

    val avatarUrl: String,

    val followers: Int,

    val following: Int,

    val name: String
)
