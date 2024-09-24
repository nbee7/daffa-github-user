package com.techtest.daffa_github_user.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techtest.daffa_github_user.data.source.local.room.entity.UserDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserDetailEntity)

    @Query("SELECT * FROM table_user_detail WHERE name = :name")
    fun getUser(name: String): Flow<UserDetailEntity>
}