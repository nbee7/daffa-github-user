package com.techtest.daffa_github_user.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtest.daffa_github_user.core.model.Resource
import com.techtest.daffa_github_user.core.model.User
import com.techtest.daffa_github_user.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _listUser = MutableLiveData<Resource<List<User>>>()
    val listUser: LiveData<Resource<List<User>>> = _listUser

    fun getListUser() {
        viewModelScope.launch {
            userUseCase.getListUser().collect {
                _listUser.value = it
            }
        }
    }
}