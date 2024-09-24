package com.techtest.daffa_github_user.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techtest.daffa_github_user.data.source.local.room.dao.UserDao
import com.techtest.daffa_github_user.data.source.local.room.dao.UserDetailDao
import com.techtest.daffa_github_user.data.source.local.room.entity.UserDetailEntity
import com.techtest.daffa_github_user.data.source.local.room.entity.UserEntity

@Database(
    entities = [UserEntity::class, UserDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun userDetailDao(): UserDetailDao
}