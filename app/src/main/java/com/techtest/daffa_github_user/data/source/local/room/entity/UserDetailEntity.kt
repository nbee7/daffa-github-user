package com.techtest.daffa_github_user.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techtest.daffa_github_user.data.source.local.room.Config.TABLE_USER_DETAIL

@Entity(tableName = TABLE_USER_DETAIL)
class UserDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userid: Int = 0,

    val company: String,

    val location: String,

    val avatarUrl: String,

    val followers: Int,

    val following: Int,

    val name: String
)
