package com.adit.lookforgit.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.lookforgit.base.BaseFragment
import com.adit.lookforgit.databinding.FragmentFollowingBinding
import com.adit.lookforgit.ui.adapters.SectionPagerAdapter.Companion.EXTRA_USERNAME
import com.adit.lookforgit.ui.adapters.UserAdapter

class FollowingFragment : BaseFragment<FragmentFollowingBinding>(){
    private val followingViewModel by viewModels<FollowingViewModel>()
    private lateinit var userAdapter: UserAdapter
    private var username = ""
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFollowingBinding {
        return FragmentFollowingBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        if(arguments != null) {
            val uName = arguments?.getString(EXTRA_USERNAME)
            if (uName != null) {
                username = uName
            }
        }
    }
    override fun initUI() {
        userAdapter = UserAdapter()

        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userAdapter
        }
    }

    override fun initProcess() {
        followingViewModel.getFollowing(username)
        followingViewModel.userFollowing.observe(viewLifecycleOwner) { followingList ->
            userAdapter.submitList(followingList)
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}