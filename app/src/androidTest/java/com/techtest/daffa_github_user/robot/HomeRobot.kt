package com.techtest.daffa_github_user.robot

import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.core.BaseRobot

class HomeRobot : BaseRobot() {
    fun verifyHomeDisplayed() {
        checkViewIsDisplayed(R.id.rv_list_user)
        checkRecyclerHasItems(R.id.rv_list_user)
    }

    fun clickUserAt(position: Int) {
        clickRecyclerViewItem(R.id.rv_list_user, position)
    }

    fun verifyEmptyOrErrorState() {
        checkViewIsDisplayed(R.id.lt_user_not_found)
    }
}

fun homeRobot(func: HomeRobot.() -> Unit) = HomeRobot().apply(func)
