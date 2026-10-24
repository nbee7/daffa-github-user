package com.techtest.daffa_github_user.core.data.mapper

import com.techtest.daffa_github_user.core.data.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.core.data.remote.response.UserResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMapperTest {

    private val dataMapper = DataMapper()

    @Test
    fun `mapUserListResponseToUserListEntity handles null values safely`() {
        val responseList = listOf(
            UserResponse(username = "user1", avatarUrl = "url1"),
            null
        )

        val entityList = dataMapper.mapUserListResponseToUserListEntity(responseList)

        assertEquals(2, entityList.size)
        assertEquals("user1", entityList[0].username)
        assertEquals("url1", entityList[0].avatar)
        assertEquals("", entityList[1].username)
        assertEquals("", entityList[1].avatar)
    }

    @Test
    fun `mapUserDetailResponseToUserDetail handles null fields gracefully`() {
        val response = UserDetailResponse(
            name = null,
            company = null,
            location = null,
            followers = 10,
            following = 5,
            avatarUrl = null,
            id = 123
        )

        val domain = dataMapper.mapUserDetailResponseToUserDetail(response)

        assertEquals("", domain.name)
        assertEquals("", domain.company)
        assertEquals("", domain.location)
        assertEquals(10, domain.followers)
        assertEquals(5, domain.following)
        assertEquals("", domain.avatarUrl)
    }
}
