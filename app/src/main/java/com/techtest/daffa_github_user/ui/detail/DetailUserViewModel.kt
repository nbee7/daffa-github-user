package com.techtest.daffa_github_user.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtest.daffa_github_user.data.Resource
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.domain.model.UserDetail
import com.techtest.daffa_github_user.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailUserViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private var _followers = MutableLiveData<Resource<List<User>>>()
    val followers: LiveData<Resource<List<User>>> = _followers
    private var _followings = MutableLiveData<Resource<List<User>>>()
    val followings: LiveData<Resource<List<User>>> = _followings
    private var _userDetail = MutableLiveData<Resource<UserDetail>>()
    val userDetail: LiveData<Resource<UserDetail>> = _userDetail

    fun userFollowers(username: String) {
        viewModelScope.launch {
            userUseCase.getFollowers(username).collect {
                _followers.postValue(it)
            }
        }
    }

    fun userFollowing(username: String) {
        viewModelScope.launch {
            userUseCase.getFollowings(username).collect {
                _followings.postValue(it)
            }
        }
    }

    fun getDetailUser(name: String) {
        viewModelScope.launch {
            userUseCase.getDetailUser(name).collect {
                _userDetail.postValue(it)
            }
        }
    }

}