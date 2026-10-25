package com.techtest.daffa_github_user.e2e

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.app.MainActivity
import com.techtest.daffa_github_user.core.MockServerDispatcher
import com.techtest.daffa_github_user.core.MockServerRule
import com.techtest.daffa_github_user.robot.detailRobot
import com.techtest.daffa_github_user.robot.homeRobot
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.rules.RuleChain

@RunWith(AndroidJUnit4::class)
class HomeToDetailE2ETest {

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(MockServerRule(MockServerDispatcher.RequestDispatcher()))
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun testNavigateFromHomeToDetailUser() {
        homeRobot {
            verifyHomeDisplayed()
            clickUserAt(0)
        }

        detailRobot {
            verifyDetailDisplayed()
        }
    }

    @Test
    fun selectingHomeAfterVisitingSearchReturnsToHomeList() {
        homeRobot { clickUserAt(0) }
        onView(withId(R.id.searchFragment)).perform(click())
        onView(withId(R.id.homeFragment)).perform(click())

        homeRobot { verifyHomeDisplayed() }
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_view)).check { view, _ ->
            assertThat((view as BottomNavigationView).selectedItemId, equalTo(R.id.homeFragment))
        }
    }
}
