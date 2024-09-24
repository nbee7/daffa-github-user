package com.techtest.daffa_github_user.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.techtest.daffa_github_user.domain.usecase.UserUseCase

class DetailUserViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getDetailGame(name: String) = userUseCase.getDetailUser(name).asLiveData()
}