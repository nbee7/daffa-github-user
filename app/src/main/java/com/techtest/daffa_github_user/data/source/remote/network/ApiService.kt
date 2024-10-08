package com.techtest.daffa_github_user.data.source.remote.network

import com.techtest.daffa_github_user.BuildConfig
import com.techtest.daffa_github_user.data.source.remote.response.ListUserResponse
import com.techtest.daffa_github_user.data.source.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getListUser(
        @Header("Authorization") token: String = BuildConfig.TOKEN
    ): List<UserResponse>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String,
        @Header("Authorization") token: String = BuildConfig.TOKEN
    ): ListUserResponse

    @GET("users/{username}")
    suspend fun getDetail(
        @Path("username") username: String,
        @Header("Authorization") token: String = BuildConfig.TOKEN
    ): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String = BuildConfig.TOKEN
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowings(
        @Path("username") username: String,
        @Header("Authorization") token: String = BuildConfig.TOKEN
    ): List<UserResponse>
}