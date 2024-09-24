package com.techtest.daffa_github_user.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.model.UserDetail
import com.techtest.daffa_github_user.domain.usecase.UserUseCase
import com.techtest.daffa_github_user.ui.detail.DetailUserViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DetailUserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: DetailUserViewModel
    private val userUseCase: UserUseCase = mock()
    private val followersObserver: Observer<Resource<List<User>>> = mock()
    private val followingsObserver: Observer<Resource<List<User>>> = mock()
    private val userDetailObserver: Observer<Resource<UserDetail>> = mock()

    @Before
    fun setup() {
        viewModel = DetailUserViewModel(userUseCase)
        viewModel.followers.observeForever(followersObserver)
        viewModel.followings.observeForever(followingsObserver)
        viewModel.userDetail.observeForever(userDetailObserver)

        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test userFollowers success`() = runTest {
        val username = "testuser"
        val expectedFollowers =
            Resource.Success(listOf(User("follower1", "test.com"), User("follower2", "test.com")))

        whenever(userUseCase.getFollowers(username)).thenReturn(flow {
            emit(expectedFollowers)
        })

        viewModel.userFollowers(username)

        verify(followersObserver).onChanged(expectedFollowers)
    }

    @Test
    fun `test userFollowing success`() = runTest {
        val username = "testuser"
        val expectedFollowings =
            Resource.Success(listOf(User("follower1", "test.com"), User("follower2", "test.com")))

        whenever(userUseCase.getFollowings(username)).thenReturn(flow {
            emit(expectedFollowings)
        })

        viewModel.userFollowing(username)

        verify(followingsObserver).onChanged(expectedFollowings)
    }

    @Test
    fun `test getDetailUser success`() = runTest {
        val username = "testuser"
        val expectedUserDetail =
            Resource.Success(UserDetail("testuser", 1, "dada", 3, "aaa", "aaa"))

        whenever(userUseCase.getDetailUser(username)).thenReturn(flow {
            emit(expectedUserDetail)
        })

        viewModel.getDetailUser(username)

        verify(userDetailObserver).onChanged(expectedUserDetail)
    }

    @Test
    fun `test userFollowers error`() = runTest {
        val username = "testuser"
        val expectedError = Resource.Error<List<User>>("Error fetching followers")

        whenever(userUseCase.getFollowers(username)).thenReturn(flow {
            emit(expectedError)
        })

        viewModel.userFollowers(username)

        verify(followersObserver).onChanged(expectedError)
    }

    @Test
    fun `test userFollowing error`() = runTest {
        val username = "testuser"
        val expectedError = Resource.Error<List<User>>("Error fetching followings")

        whenever(userUseCase.getFollowings(username)).thenReturn(flow {
            emit(expectedError)
        })

        viewModel.userFollowing(username)

        verify(followingsObserver).onChanged(expectedError)
    }

    @Test
    fun `test getDetailUser error`() = runTest {
        val username = "testuser"
        val expectedError = Resource.Error<UserDetail>("Error fetching user detail")

        whenever(userUseCase.getDetailUser(username)).thenReturn(flow {
            emit(expectedError)
        })

        viewModel.getDetailUser(username)

        verify(userDetailObserver).onChanged(expectedError)
    }
}
