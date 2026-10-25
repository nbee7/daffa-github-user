package com.techtest.daffa_github_user.core.data.repository

import com.techtest.daffa_github_user.core.data.local.LocalDataSource
import com.techtest.daffa_github_user.core.data.local.room.entity.UserEntity
import com.techtest.daffa_github_user.core.data.mapper.DataMapper
import com.techtest.daffa_github_user.core.data.remote.RemoteDataSource
import com.techtest.daffa_github_user.core.data.remote.Result
import com.techtest.daffa_github_user.core.data.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.core.data.remote.response.UserResponse
import com.techtest.daffa_github_user.core.domain.repository.IUserRepository
import com.techtest.daffa_github_user.core.model.Resource
import com.techtest.daffa_github_user.core.model.User
import com.techtest.daffa_github_user.core.model.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataMapper: DataMapper
) : IUserRepository {
    override fun getListUser(): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun loadFromDB(): Flow<List<User>> = localDataSource.getListUser().map {
                dataMapper.mapUserListEntityToUserListDomain(it)
            }

            override fun shouldFetch(data: List<User>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<Result<List<UserResponse>>> =
                remoteDataSource.getListUser()

            override suspend fun saveCallResult(data: List<UserResponse>) {
                localDataSource.replaceHomeUsers(dataMapper.mapUserListResponseToUserListEntity(data))
            }

            override suspend fun saveEmptyCallResult() {
                localDataSource.replaceHomeUsers(listOf(UserEntity("home:empty", "", "")))
            }
        }.asFlow().map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data?.filter { it.username.isNotEmpty() }.orEmpty())
                is Resource.Loading -> Resource.Loading(resource.data)
                is Resource.Error -> resource
            }
        }

    override fun getDetailUser(name: String): Flow<Resource<UserDetail>> =
        object : NetworkBoundResource<UserDetail?, UserDetailResponse>() {
            override fun loadFromDB(): Flow<UserDetail?> = localDataSource.getDetailUser(name).map {
                it?.let(dataMapper::mapUserDetailEntityToUserDetailDomain)
            }

            override fun shouldFetch(data: UserDetail?): Boolean = data == null

            override suspend fun createCall(): Flow<Result<UserDetailResponse>> =
                remoteDataSource.getUserDetail(name)

            override suspend fun saveCallResult(data: UserDetailResponse) {
                localDataSource.insertUserDetail(dataMapper.mapUserDetailResponseToUserDetailEntity(name, data))
            }
        }.asFlow().map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading<UserDetail>()
                is Resource.Error -> Resource.Error(resource.message.orEmpty())
                is Resource.Success -> resource.data?.let { Resource.Success(it) }
                    ?: Resource.Error("User not found")
            }
        }

    override fun getFollowers(name: String): Flow<Resource<List<User>>> =
        cachedUsers("followers:$name:") { remoteDataSource.getFollowers(name) }

    override fun getFollowings(name: String): Flow<Resource<List<User>>> =
        cachedUsers("following:$name:") { remoteDataSource.getFollowings(name) }

    override fun searchUser(name: String): Flow<Resource<List<User>>> =
        cachedUsers("search:${name.length}:$name:") {
            remoteDataSource.searchUser(name).map { result ->
                when (result) {
                    is Result.Success -> Result.Success(result.data.items)
                    is Result.Empty -> Result.Empty
                    is Result.Error -> result
                }
            }
        }

    private fun cachedUsers(
        prefix: String,
        createRequest: suspend () -> Flow<Result<List<UserResponse>>>
    ): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun loadFromDB(): Flow<List<User>> = localDataSource.getCachedList(prefix).map {
                dataMapper.mapUserListEntityToUserListDomain(it)
            }

            override fun shouldFetch(data: List<User>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<Result<List<UserResponse>>> = createRequest()

            override suspend fun saveCallResult(data: List<UserResponse>) {
                localDataSource.replaceCachedList(
                    prefix,
                    if (data.isEmpty()) listOf(UserEntity(prefix + "empty", "", ""))
                    else dataMapper.mapUserListResponseToUserListEntity(data, prefix)
                )
            }

            override suspend fun saveEmptyCallResult() {
                localDataSource.replaceCachedList(prefix, listOf(UserEntity(prefix + "empty", "", "")))
            }
        }.asFlow().map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data?.filter { it.username.isNotEmpty() }.orEmpty())
                is Resource.Loading -> Resource.Loading(resource.data)
                is Resource.Error -> resource
            }
        }
}
