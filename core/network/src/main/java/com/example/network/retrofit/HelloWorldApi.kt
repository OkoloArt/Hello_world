package com.example.network.retrofit

import com.example.network.model.friends.FriendResponse
import com.example.network.model.user.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HelloWorldApi {

    @FormUrlEncoded
    @POST("users")
    suspend fun createUser(
        @Field("uid") uid: String ,
        @Field("name") name: String ,
    ): UserResponse

    @GET("users/{uid}/friends")
    suspend fun getFriendsList(
        @Path("uid") uid: String
    ): FriendResponse
}