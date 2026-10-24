package com.techtest.daffa_github_user.core.data.remote

import com.techtest.daffa_github_user.core.data.remote.network.ApiService
import com.techtest.daffa_github_user.core.data.remote.response.UserDetailResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException

class RemoteDataSourceTest {
    @Test
    fun `timeout returns no internet error`() = runBlocking {
        val api = mock<ApiService>()
        whenever(api.getDetail(any(), any())).thenAnswer {
            throw SocketTimeoutException("timed out")
        }

        val result = RemoteDataSource(api).getUserDetail("octocat").first()

        assertTrue(result is Result.Error && result.errorMessage == "No internet")
    }

    @Test
    fun `profile with no display name is still a successful response`() = runBlocking {
        val api = mock<ApiService>()
        whenever(api.getDetail(any(), any())).thenReturn(
            UserDetailResponse(null, 7, "avatar", 3, null, null, 123)
        )

        val result = RemoteDataSource(api).getUserDetail("octocat").first()

        assertTrue(result is Result.Success)
    }
}
