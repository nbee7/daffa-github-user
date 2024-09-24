package com.techtest.daffa_github_user.domain.usecase

import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getListUser(): Flow<Resource<List<User>>>

    fun getDetailUser(name: String): Flow<Resource<UserDetail>>

    fun getFollowers(name: String): Flow<Resource<List<User>>>

    fun getFollowings(name: String): Flow<Resource<List<User>>>

    fun searchUser(name: String): Flow<Resource<List<User>>>
}