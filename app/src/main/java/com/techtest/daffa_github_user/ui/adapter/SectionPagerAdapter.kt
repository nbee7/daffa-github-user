package com.techtest.daffa_github_user.ui.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.ui.detail.FollowFragment

class SectionPagerAdapter(activity: FragmentActivity, private val username: String?) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position, username)
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.txt_followers, R.string.txt_followings)
    }
}