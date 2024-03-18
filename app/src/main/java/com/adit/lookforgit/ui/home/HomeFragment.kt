package com.adit.lookforgit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.lookforgit.base.BaseFragment
import com.adit.lookforgit.databinding.FragmentHomeBinding
import com.adit.lookforgit.ui.adapters.UserAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var userAdapter: UserAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            rvUsers.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                userAdapter = UserAdapter().apply {
                    setOnItemClickListener { user ->
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(user.login)
                        )
                    }
                }
                adapter = userAdapter

            }
            searchView.setupWithSearchBar(searchUsers)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchUsers.setText(searchView.text)
                searchView.hide()
                mainViewModel.searchUsers(searchView.text.toString())
                false
            }

        }
    }

    override fun initProcess() {
        mainViewModel.userList.observe(viewLifecycleOwner) { userList ->
            userAdapter.submitList(userList)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        mainViewModel.searchuserList.observe(viewLifecycleOwner) { searchUserList ->
            userAdapter.submitList(searchUserList)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}