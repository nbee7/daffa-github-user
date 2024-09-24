package com.techtest.daffa_github_user.domain.usecase

import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.model.UserDetail
import com.techtest.daffa_github_user.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val repository: IUserRepository) : UserUseCase {
    override fun getListUser(): Flow<Resource<List<User>>> = repository.getListUser()

    override fun getDetailUser(name: String): Flow<Resource<UserDetail>> =
        repository.getDetailUser(name)

    override fun getFollowers(name: String): Flow<Resource<List<User>>> =
        repository.getFollowers(name)

    override fun getFollowings(name: String): Flow<Resource<List<User>>> =
        repository.getFollowings(name)

    override fun searchUser(name: String): Flow<Resource<List<User>>> = repository.searchUser(name)

}