package com.techtest.daffa_github_user.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techtest.daffa_github_user.core.data.local.room.Config.TABLE_USER

@Entity(tableName = TABLE_USER)
class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "cache_key")
    var cacheKey: String,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar")
    var avatar: String
)