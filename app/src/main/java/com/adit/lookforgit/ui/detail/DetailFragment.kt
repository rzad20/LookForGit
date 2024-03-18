package com.adit.lookforgit.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.adit.lookforgit.R
import com.adit.lookforgit.base.BaseFragment
import com.adit.lookforgit.databinding.FragmentDetailBinding
import com.adit.lookforgit.ui.adapters.SectionPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val detailViewModel by viewModels<DetailViewModel>()
    private var username = ""
    private var sectionsPagerAdapter: SectionPagerAdapter? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun initIntent() {
        val safeArgs = arguments?.let {DetailFragmentArgs.fromBundle(it)}
        username = safeArgs?.userId ?: ""
    }
    override fun initUI() {
        val userId = arguments?.getString("userId")
        userId?.let {
            detailViewModel.getUserDetails(it)
        }
        sectionsPagerAdapter = SectionPagerAdapter(username, requireActivity())
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    override fun initProcess() {
        detailViewModel.userDetail.observe(viewLifecycleOwner) { userDetail ->
            binding.detailName.text = userDetail.name
            binding.detailUserName.text = userDetail.login
            binding.detailFollowing.text = "${userDetail.following} Following"
            binding.detailFollowers.text = "${userDetail.followers} Followers"
            Glide.with(binding.userImage.context)
                .load(userDetail.avatarUrl)
                .into(binding.userImage)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }

}