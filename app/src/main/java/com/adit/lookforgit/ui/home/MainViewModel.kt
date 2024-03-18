package com.adit.lookforgit.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adit.lookforgit.network.response.GithubResponse
import com.adit.lookforgit.network.response.ItemsItem
import com.adit.lookforgit.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _searchuserList = MutableLiveData<List<ItemsItem>>()
    val searchuserList : LiveData<List<ItemsItem>> = _searchuserList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }
    init {
        findUsers()
    }

    private fun findUsers(query: String? = null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query ?: randomQuery())
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (query != null) {
                        _searchuserList.value = response.body()?.items
                    }
                    else {
                        _userList.value = response.body()?.items?.shuffled()
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun searchUsers(query: String) {
        findUsers(query)
    }
    private fun randomQuery(): String {
        return ('a'..'z').random().toString()
    }

}