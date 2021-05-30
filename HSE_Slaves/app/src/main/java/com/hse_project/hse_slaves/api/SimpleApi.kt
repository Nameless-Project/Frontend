package com.hse_project.hse_slaves.api

import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPost
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.model.UserRegistration
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    //Events
    @GET("/api/events/{id}")
    suspend fun getEvent(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int
    ): Response<Event>

    @POST("/api/events")
    suspend fun postEvent(
        @HeaderMap headers: Map<String, String>,
        @Body event: EventPost
    ): Response<Void>

    //Feed

    //Image
    @GET("/api/images/{id}")
    suspend fun getImage(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int,
        @Query("entity") entity: String
    ): Response<List<String>>

    //User

    @GET("/api/users/{id}")
    suspend fun getUser(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int
    ): Response<User>

    //Security

    @POST("/api/registration")
    suspend fun register(
        @Body event: UserRegistration
    ): Response<Void>

    @POST("/api/authentication")
    suspend fun getToken(
        @QueryMap filters: Map<String, String>
    ): Response<Int>

    //Likes

    @POST("/api/users/{userId}/likes/{eventId}")
    suspend fun postLike(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int,
        @Path("eventId") eventId: Int
    ): Response<Void>

    @DELETE("/api/users/{userId}/likes/{eventId}")
    suspend fun deleteLike(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int,
        @Path("eventId") eventId: Int
    ): Response<Void>

    @GET("/api/users/{userId}/likes/{eventId}")
    suspend fun checkLike(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int,
        @Path("eventId") eventId: Int
    ): Response<Boolean>

    @PUT("/api/users/{userId}")
    suspend fun changeUser(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int,
        @Body event: UserRegistration
    ): Response<Void>
}