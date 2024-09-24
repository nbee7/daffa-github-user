package com.techtest.daffa_github_user.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class SearchViewModel(private val useCase: UserUseCase) : ViewModel() {
    private val _listUser = MutableLiveData<Resource<List<User>>>()
    val listUser: LiveData<Resource<List<User>>> = _listUser


    fun searchUser(name: String) {
        viewModelScope.launch {
            useCase.searchUser(name).collect {
                _listUser.value = it
            }
        }
    }
}