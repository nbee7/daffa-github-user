package com.techtest.daffa_github_user.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.usecase.UserUseCase
import com.techtest.daffa_github_user.ui.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userUseCase: UserUseCase

    private lateinit var homeViewModel: HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(userUseCase)

        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getListUser should update listUser LiveData`() = runTest {
        val userList = listOf(User("John Doe", "example.com"), User("Jane Doe", "example.com"))
        val resource = Resource.Success(userList)

        `when`(userUseCase.getListUser()).thenReturn(flow {
            emit(resource)
        })

        val observer = mock(Observer::class.java) as Observer<Resource<List<User>>>
        homeViewModel.listUser.observeForever(observer)

        homeViewModel.getListUser()

        verify(observer).onChanged(resource)
        assertEquals(resource, homeViewModel.listUser.value)

        homeViewModel.listUser.removeObserver(observer)
    }
}