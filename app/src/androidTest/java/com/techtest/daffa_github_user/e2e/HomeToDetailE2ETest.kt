package com.techtest.daffa_github_user.e2e

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.techtest.daffa_github_user.MainActivity
import com.techtest.daffa_github_user.core.MockServerDispatcher
import com.techtest.daffa_github_user.core.MockServerRule
import com.techtest.daffa_github_user.robot.detailRobot
import com.techtest.daffa_github_user.robot.homeRobot
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
}
