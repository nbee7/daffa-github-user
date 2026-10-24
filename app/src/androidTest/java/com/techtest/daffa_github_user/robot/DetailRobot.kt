package com.techtest.daffa_github_user.robot

import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.core.BaseRobot

class DetailRobot : BaseRobot() {
    fun verifyDetailDisplayed() {
        checkViewIsDisplayed(R.id.tv_username)
    }

    fun verifyUserName(expectedName: String) {
        checkViewText(R.id.tv_username, expectedName)
    }

    fun verifyFollowersCount() {
        checkViewIsDisplayed(R.id.tv_followers)
    }

    fun verifyFollowingCount() {
        checkViewIsDisplayed(R.id.tv_followings)
    }
}

fun detailRobot(func: DetailRobot.() -> Unit) = DetailRobot().apply(func)
