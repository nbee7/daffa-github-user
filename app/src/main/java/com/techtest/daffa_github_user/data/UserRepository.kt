package com.techtest.daffa_github_user.data

import com.techtest.daffa_github_user.data.source.local.LocalDataSource
import com.techtest.daffa_github_user.data.source.remote.RemoteDataSource
import com.techtest.daffa_github_user.data.source.remote.Result
import com.techtest.daffa_github_user.data.source.remote.response.UserResponse
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.model.UserDetail
import com.techtest.daffa_github_user.domain.repository.IUserRepository
import com.techtest.daffa_github_user.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataMapper: DataMapper
) : IUserRepository {
    override fun getListUser(): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.getListUser().map {
                    dataMapper.mapUserListEntityToUserListDomain(it)
                }
            }

            override suspend fun createCall(): Flow<Result<List<UserResponse>>> {
                return remoteDataSource.getListUser()
            }

            override suspend fun saveCallResult(data: List<UserResponse>) {
                val mapper = dataMapper.mapUserListResponseToUserListEntity(data)
                localDataSource.insetUserList(mapper)
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return data.isNullOrEmpty() || data.isEmpty()
            }
        }.asFlow()

    override fun getDetailUser(name: String): Flow<Resource<UserDetail>> {
        return flow {
            remoteDataSource.getUserDetail(name).collect {
                when (it) {
                    is Result.Success -> {
                        emit(Resource.Success(dataMapper.mapUserDetailResponseToUserDetail(it.data)))
                    }

                    is Result.Empty -> {
                        emit(Resource.Error(it.toString(), null))
                    }

                    is Result.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                }
            }
        }
    }


    override fun getFollowers(name: String): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getFollowers(name).collect {
                when (it) {
                    is Result.Success -> {
                        emit(Resource.Success(dataMapper.mapUserListResponseToUserListDomain(it.data)))
                    }

                    is Result.Empty -> {
                        emit(Resource.Success(listOf()))
                    }

                    is Result.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                }
            }
        }
    }

    override fun getFollowings(name: String): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getFollowings(name).collect {
                when (it) {
                    is Result.Success -> {
                        emit(Resource.Success(dataMapper.mapUserListResponseToUserListDomain(it.data)))
                    }

                    is Result.Empty -> {
                        emit(Resource.Success(listOf()))
                    }

                    is Result.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                }
            }
        }
    }

    override fun searchUser(name: String): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.searchUser(name).collect {
                when (it) {
                    is Result.Success -> {
                        emit(
                            Resource.Success(
                                dataMapper.mapSearchUserListResponseToUserListDomain(
                                    it.data
                                )
                            )
                        )
                    }

                    is Result.Empty -> {
                        emit(Resource.Success(listOf()))
                    }

                    is Result.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                }
            }
        }
    }
}