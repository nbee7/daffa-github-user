package com.techtest.daffa_github_user.data.source.local.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techtest.daffa_github_user.data.source.local.room.Config.TABLE_USER

@Entity(tableName = TABLE_USER)
class UserEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar")
    var avatar: String
)