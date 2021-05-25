package com.hse_project.hse_slaves.api

import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.model.Post
import com.hse_project.hse_slaves.model.UserRegistration
import retrofit2.http.*

interface SimpleApi {

    @GET("/events/get?id=1")
    suspend fun getPost(
        @HeaderMap headers: Map<String, String>
    ): retrofit2.Response<Post>

    @GET("/api/events/1")
    suspend fun getEvent(
        @HeaderMap headers: Map<String, String>
    ): retrofit2.Response<Event>

    @GET("/api/images/1?entity=EVENT")
    suspend fun getImage(
        @HeaderMap headers: Map<String, String>
    ): retrofit2.Response<List<String>>

    @POST("/api/events")
    suspend fun postEvent(
        @HeaderMap headers: Map<String, String>,
        @Body event: EventPostMain
    )

    @POST("/api/registration")
    suspend fun register(
        @Body event: UserRegistration
    )

    @POST("/api/authentication{username}{password}")
    suspend fun getToken(
        @Path("username") username: String,
        @Path("password") password: String
    ): retrofit2.Response<Any>
}