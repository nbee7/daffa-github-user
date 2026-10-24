package com.techtest.daffa_github_user.core.data.mapper

import com.techtest.daffa_github_user.core.data.local.room.entity.UserDetailEntity
import com.techtest.daffa_github_user.core.data.local.room.entity.UserEntity
import com.techtest.daffa_github_user.core.data.remote.response.ListUserResponse
import com.techtest.daffa_github_user.core.data.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.core.data.remote.response.UserResponse
import com.techtest.daffa_github_user.core.model.User
import com.techtest.daffa_github_user.core.model.UserDetail
import com.techtest.daffa_github_user.core.data.util.ElvisUtil.orZero

class DataMapper {
    fun mapUserListResponseToUserListEntity(users: List<UserResponse?>): List<UserEntity> =
        users.map {
            UserEntity(
                username = it?.username.orEmpty(),
                avatar = it?.avatarUrl.orEmpty()
            )
        }

    fun mapUserListEntityToUserListDomain(users: List<UserEntity?>): List<User> =
        users.map {
            User(
                username = it?.username.orEmpty(),
                avatarUrl = it?.avatar.orEmpty()
            )
        }

    fun mapUserListResponseToUserListDomain(users: List<UserResponse?>): List<User> =
        users.map {
            User(
                username = it?.username.orEmpty(),
                avatarUrl = it?.avatarUrl.orEmpty()
            )
        }

    fun mapSearchUserListResponseToUserListDomain(users: ListUserResponse): List<User> =
        users.items.map {
            User(
                username = it?.username.orEmpty(),
                avatarUrl = it?.avatarUrl.orEmpty()
            )
        }


    fun mapUserDetailEntityToUserDetailDomain(user: UserDetailEntity?): UserDetail =
        UserDetail(
            name = user?.name.orEmpty(),
            company = user?.company.orEmpty(),
            location = user?.location.orEmpty(),
            followers = user?.followers.orZero(),
            following = user?.following.orZero(),
            avatarUrl = user?.avatarUrl.orEmpty()
        )

    fun mapUserDetailResponseToUserDetailEntity(user: UserDetailResponse?): UserDetailEntity =
        UserDetailEntity(
            name = user?.name.orEmpty(),
            company = user?.company.orEmpty(),
            location = user?.location.orEmpty(),
            followers = user?.followers.orZero(),
            following = user?.following.orZero(),
            avatarUrl = user?.avatarUrl.orEmpty()
        )

    fun mapUserDetailResponseToUserDetail(user: UserDetailResponse?): UserDetail =
        UserDetail(
            name = user?.name.orEmpty(),
            company = user?.company.orEmpty(),
            location = user?.location.orEmpty(),
            followers = user?.followers.orZero(),
            following = user?.following.orZero(),
            avatarUrl = user?.avatarUrl.orEmpty()
        )
}