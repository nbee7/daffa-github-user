package com.techtest.daffa_github_user.core

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.recyclerview.widget.RecyclerView

open class BaseRobot {
    fun clickOnView(viewId: Int) {
        onView(withId(viewId)).perform(click())
    }

    fun typeTextIntoView(viewId: Int, text: String) {
        onView(withId(viewId)).perform(typeText(text), closeSoftKeyboard())
    }

    fun checkViewIsDisplayed(viewId: Int, timeoutMs: Long = 10_000) {
        waitUntil(viewId, timeoutMs) { onView(withId(viewId)).check(matches(isDisplayed())) }
    }

    fun checkRecyclerHasItems(recyclerViewId: Int, minimumCount: Int = 1, timeoutMs: Long = 10_000) {
        waitUntil(recyclerViewId, timeoutMs) {
            onView(withId(recyclerViewId)).check(matches(hasMinimumChildCount(minimumCount)))
        }
    }

    fun checkViewText(viewId: Int, expectedText: String) {
        onView(withId(viewId)).check(matches(withText(expectedText)))
    }

    fun clickRecyclerViewItem(recyclerViewId: Int, position: Int) {
        checkRecyclerHasItems(recyclerViewId, position + 1)
        onView(withId(recyclerViewId))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    private fun waitUntil(viewId: Int, timeoutMs: Long, assertion: () -> Unit) {
        val deadline = SystemClock.uptimeMillis() + timeoutMs
        var lastError: Throwable? = null
        while (SystemClock.uptimeMillis() < deadline) {
            try {
                assertion()
                return
            } catch (e: Throwable) {
                lastError = e
                SystemClock.sleep(200)
            }
        }
        throw lastError ?: AssertionError("View $viewId never satisfied the assertion")
    }
}
