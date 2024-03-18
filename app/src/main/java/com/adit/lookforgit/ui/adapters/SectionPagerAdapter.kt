package com.adit.lookforgit.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.adit.lookforgit.ui.follower.FollowerFragment
import com.adit.lookforgit.ui.following.FollowingFragment

class SectionPagerAdapter(private val username: String, activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val mBundle = Bundle()
        val followerFragment = FollowerFragment()
        val followingFragment = FollowingFragment()
        mBundle.putString(EXTRA_USERNAME, username)
        followerFragment.arguments = mBundle
        followingFragment.arguments = mBundle
        when(position) {
            0 -> fragment = followingFragment
            1 -> fragment = followerFragment
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int = 2

    companion object {
        var EXTRA_USERNAME = "extra_username"
    }
}