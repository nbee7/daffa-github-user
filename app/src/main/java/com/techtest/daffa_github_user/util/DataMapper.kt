package com.techtest.daffa_github_user.util

import com.techtest.daffa_github_user.data.source.local.room.entity.UserDetailEntity
import com.techtest.daffa_github_user.data.source.local.room.entity.UserEntity
import com.techtest.daffa_github_user.data.source.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.data.source.remote.response.UserResponse
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.model.UserDetail

class DataMapper {
    fun mapUserListResponseToUserListEntity(users: List<UserResponse>): List<UserEntity> =
        users.map {
            UserEntity(
                username = it.username,
                avatar = it.avatarUrl
            )
        }

    fun mapUserListEntityToUserListDomain(users: List<UserEntity>): List<User> =
        users.map {
            User(
                username = it.username,
                avatarUrl = it.avatar
            )
        }

    fun mapUserDetailEntityToUserDetailDomain(user: UserDetailEntity): UserDetail =
        UserDetail(
            name = user.name,
            company = user.company,
            location = user.location,
            followers = user.followers,
            following = user.following,
            avatarUrl = user.avatarUrl
        )

    fun mapUserDetailResponseToUserDetailEntity(user: UserDetailResponse): UserDetailEntity =
        UserDetailEntity(
            name = user.name,
            company = user.company,
            location = user.location,
            followers = user.followers,
            following = user.following,
            avatarUrl = user.avatarUrl
        )
}