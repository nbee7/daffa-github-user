package com.techtest.daffa_github_user.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techtest.daffa_github_user.core.data.local.room.entity.UserDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDetailEntity)

    @Query("SELECT * FROM table_user_detail WHERE username = :username")
    fun getUser(username: String): Flow<UserDetailEntity?>
}