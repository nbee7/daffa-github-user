package com.techtest.daffa_github_user.core.domain.repository

import com.techtest.daffa_github_user.core.model.Resource
import com.techtest.daffa_github_user.core.model.User
import com.techtest.daffa_github_user.core.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getListUser(): Flow<Resource<List<User>>>

    fun getDetailUser(name: String): Flow<Resource<UserDetail>>

    fun getFollowers(name: String): Flow<Resource<List<User>>>

    fun getFollowings(name: String): Flow<Resource<List<User>>>

    fun searchUser(name: String): Flow<Resource<List<User>>>
}