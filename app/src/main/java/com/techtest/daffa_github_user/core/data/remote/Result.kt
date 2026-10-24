package com.techtest.daffa_github_user.core.data.remote

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    object Empty : Result<Nothing>()
}
