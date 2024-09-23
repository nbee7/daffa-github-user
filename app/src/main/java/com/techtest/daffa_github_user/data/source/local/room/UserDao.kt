package com.techtest.daffa_github_user.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListUser(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM table_user")
    fun getListUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM table_user WHERE user_id = :id")
    fun getDetailUser(id: Int): Flow<UserEntity>
}