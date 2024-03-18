package com.adit.lookforgit.network.retrofit

import com.adit.lookforgit.network.response.DetailUserResponse
import com.adit.lookforgit.network.response.GithubResponse
import com.adit.lookforgit.network.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
        @GET("search/users")
        fun searchUsers(
            @Query("q") username: String
        ): Call<GithubResponse>

        @GET("users/{username}")
        fun getDetailUser(
            @Path("username") username: String
        ): Call<DetailUserResponse>

        @GET("users/{username}/followers")
        fun getFollowers(
            @Path("username") username: String
        ) : Call<List<ItemsItem>>

        @GET("users/{username}/following")
        fun getFollowing(
            @Path("username") username: String
        ) : Call<List<ItemsItem>>
}