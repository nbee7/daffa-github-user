package com.techtest.daffa_github_user.core.data.local

import com.techtest.daffa_github_user.core.data.local.room.dao.UserDao
import com.techtest.daffa_github_user.core.data.local.room.dao.UserDetailDao
import com.techtest.daffa_github_user.core.data.local.room.entity.UserDetailEntity
import com.techtest.daffa_github_user.core.data.local.room.entity.UserEntity

class LocalDataSource(private val userDao: UserDao, private val userDetailDao: UserDetailDao) {
    suspend fun insetUserList(users: List<UserEntity>) = userDao.insertListUser(users)

    suspend fun insertUserDetail(user: UserDetailEntity) = userDetailDao.insertUser(user)

    fun getListUser() = userDao.getListUser()

    fun getDetailUser(username: String) = userDetailDao.getUser(username)
}