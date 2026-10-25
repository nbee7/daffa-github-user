package com.techtest.daffa_github_user.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.techtest.daffa_github_user.core.data.local.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUser(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM table_user WHERE cache_key LIKE 'home:%'")
    fun getListUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM table_user WHERE substr(cache_key, 1, length(:prefix)) = :prefix ORDER BY rowid")
    fun getCachedList(prefix: String): Flow<List<UserEntity>>

    @Query("DELETE FROM table_user WHERE substr(cache_key, 1, length(:prefix)) = :prefix")
    suspend fun deleteCachedList(prefix: String)

    @Transaction
    suspend fun replaceHomeUsers(users: List<UserEntity>) {
        deleteHomeUsers()
        insertListUser(users)
    }

    @Query("DELETE FROM table_user WHERE cache_key LIKE 'home:%'")
    suspend fun deleteHomeUsers()

    @Transaction
    suspend fun replaceCachedList(prefix: String, users: List<UserEntity>) {
        deleteCachedList(prefix)
        insertListUser(users)
    }
}