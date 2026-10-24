package com.techtest.daffa_github_user.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.core.BaseRobot

class SearchRobot : BaseRobot() {
    fun verifySearchScreenDisplayed() {
        checkViewIsDisplayed(R.id.sv_user)
    }

    fun searchForUser(query: String) {
        onView(withId(R.id.sv_user)).perform(click())
        typeTextIntoView(R.id.sv_user, query)
    }

    fun verifySearchResultDisplayed() {
        checkViewIsDisplayed(R.id.rv_list_user)
    }
}

fun searchRobot(func: SearchRobot.() -> Unit) = SearchRobot().apply(func)
