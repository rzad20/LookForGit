package com.adit.lookforgit.ui.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.lookforgit.base.BaseFragment
import com.adit.lookforgit.databinding.FragmentFollowerBinding
import com.adit.lookforgit.ui.adapters.SectionPagerAdapter
import com.adit.lookforgit.ui.adapters.UserAdapter

class FollowerFragment : BaseFragment<FragmentFollowerBinding>(){
    private val followersViewModel by viewModels<FollowerViewModel>()
    private lateinit var userAdapter: UserAdapter
    private var username = ""
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFollowerBinding {
        return FragmentFollowerBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        if (arguments != null) {
            val uName = arguments?.getString(SectionPagerAdapter.EXTRA_USERNAME)
            if (uName != null) {
                username = uName
            }
        }
    }

        override fun initUI() {
        userAdapter = UserAdapter()

        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userAdapter
        }
    }

    override fun initProcess() {
        followersViewModel.getFollowers(username)
        followersViewModel.userFollowers.observe(viewLifecycleOwner) { followerList ->
            userAdapter.submitList(followerList)
        }
        followersViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}