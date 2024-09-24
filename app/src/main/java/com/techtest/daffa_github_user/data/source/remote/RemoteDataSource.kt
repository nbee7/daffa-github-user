package com.techtest.daffa_github_user.data.source.remote

import com.techtest.daffa_github_user.data.source.remote.network.ApiService
import com.techtest.daffa_github_user.data.source.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.data.source.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getListUser(): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getListUser()
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }
    }

    suspend fun getUserDetail(username: String): Flow<Result<UserDetailResponse>> {
        return flow {
            try {
                val response = apiService.getDetail(username)
                if (response.name.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }
    }

    suspend fun searchUser(username: String): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.searchUser(username)
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }
    }

    suspend fun getFollowers(username: String): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }
    }

    suspend fun getFollowings(username: String): Flow<Result<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                if (response.isNotEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Empty)
                }
            } catch (e: Exception) {
                emit(Result.Error("unexpected Error ${e.message}"))
            }
        }
    }
}