package com.techtest.daffa_github_user.e2e

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.techtest.daffa_github_user.app.MainActivity
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.core.MockServerDispatcher
import com.techtest.daffa_github_user.core.MockServerRule
import com.techtest.daffa_github_user.robot.searchRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.rules.RuleChain

@RunWith(AndroidJUnit4::class)
class SearchUserE2ETest {

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS))
        .around(MockServerRule(MockServerDispatcher.RequestDispatcher()))
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Test
    fun testSearchUserSuccessFlow() {
        onView(withId(R.id.searchFragment)).perform(click())

        onView(withId(androidx.appcompat.R.id.search_button)).perform(click())

        searchRobot {
            checkViewIsDisplayed(androidx.appcompat.R.id.search_src_text)
        }

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("octocat"), pressImeActionButton(), closeSoftKeyboard())

        searchRobot {
            verifySearchResultDisplayed()
            checkRecyclerHasItems(R.id.rv_list_user)
        }
    }
}
