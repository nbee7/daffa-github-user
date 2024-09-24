package com.techtest.daffa_github_user.data.source.remote

import com.techtest.daffa_github_user.BuildConfig
import com.techtest.daffa_github_user.data.source.remote.network.ApiService
import com.techtest.daffa_github_user.data.source.remote.response.ListUserResponse
import com.techtest.daffa_github_user.data.source.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    private val token = "token " + BuildConfig.TOKEN

    suspend fun getListUser(): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getListUser(token)
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<Result<UserDetailResponse>> {
        return flow {
            try {
                val response = apiService.getDetail(username, token)
                if (response.name.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchUser(username: String): Flow<Result<ListUserResponse>> {
        return flow {
            try {
                val response = apiService.searchUser(username, token)
                if (response.items.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowers(username: String): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username, token)
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowings(username: String): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowings(username, token)
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }
}