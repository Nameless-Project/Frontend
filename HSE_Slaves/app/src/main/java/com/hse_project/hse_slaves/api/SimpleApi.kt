package com.hse_project.hse_slaves.api

import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SimpleApi {

    @GET("/events/get?id=1")
    suspend fun getPost(): retrofit2.Response<Post>

    @GET("/api/events/1")
    suspend fun getEvent(): retrofit2.Response<Event>

    @GET("/api/images/1?entity=EVENT")
    suspend fun getImage(): retrofit2.Response<List<String>>

    @POST("/api/events")
    suspend fun postEvent(
        @Body event: EventPostMain
    )
}