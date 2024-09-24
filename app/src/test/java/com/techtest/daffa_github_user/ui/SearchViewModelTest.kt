package com.techtest.daffa_github_user.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.usecase.UserUseCase
import com.techtest.daffa_github_user.ui.search.SearchViewModel
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

class SearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: SearchViewModel
    private val useCase: UserUseCase = mock()
    private val listUserObserver: Observer<Resource<List<User>>> = mock()

    @Before
    fun setup() {
        viewModel = SearchViewModel(useCase)
        viewModel.listUser.observeForever(listUserObserver)
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test searchUser success`() = runTest {
        val username = "testuser"
        val expectedUsers = Resource.Success(listOf(User("user1", "sasa"), User("user2", "sasa")))

        whenever(useCase.searchUser(username)).thenReturn(flow {
            emit(expectedUsers)
        })

        viewModel.searchUser(username)

        verify(listUserObserver).onChanged(expectedUsers)
    }

    @Test
    fun `test searchUser error`() = runTest {
        val username = "testuser"
        val expectedError = Resource.Error<List<User>>("Error fetching users")

        whenever(useCase.searchUser(username)).thenReturn(flow {
            emit(expectedError)
        })

        viewModel.searchUser(username)

        verify(listUserObserver).onChanged(expectedError)
    }

    @Test
    fun `test searchUser empty result`() = runTest {
        val username = "testuser"
        val expectedEmptyResult = Resource.Success(emptyList<User>())

        whenever(useCase.searchUser(username)).thenReturn(flow {
            emit(expectedEmptyResult)
        })

        viewModel.searchUser(username)

        verify(listUserObserver).onChanged(expectedEmptyResult)
    }
}

