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

    @PUT("/api/users/{userId}")
    suspend fun changeUser(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int,
        @Body event: UserRegistration
    ): Response<Void>

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

    //Subscriptions
    @POST("/api/subscriptions")
    suspend fun postSubscription(
        @HeaderMap headers: Map<String, String>,
        @QueryMap filters: Map<String, Int>
    ): Response<Void>

    @DELETE("/api/subscriptions")
    suspend fun deleteSubscription(
        @HeaderMap headers: Map<String, String>,
        @QueryMap filters: Map<String, Int>
    ): Response<Void>

    @GET("/api/subscriptions")
    suspend fun getAllSubscriptions(
        @HeaderMap headers: Map<String, String>,
        @QueryMap filters: Map<String, Int>
    ): Response<List<User>>

    //Feed
    @GET("/api/feed/events")
    suspend fun getEvents(
        @HeaderMap headers: Map<String, String>,
        @QueryMap filters: Map<String, Int>,
        @Query("specializations") specializations : Set<String>
    ): Response<List<Event>>

    @GET("/api/feed/{userId}/events/recommendations")
    suspend fun getEventsRecommendation(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int,
        @QueryMap filters: Map<String, Int>,
        @Query("specializations") specializations : Set<String>
    ): Response<List<Event>>

    @GET("/api/feed/creators")
    suspend fun getCreators(
        @HeaderMap headers: Map<String, String>,
        @QueryMap filters: Map<String, Int>,
        @Query("specializations") specializations : Set<String>
    ): Response<List<User>>

    //Application

    @POST("/api/creators/{creatorId}/applications/{eventId}")
    suspend fun sendApplicationToEvent(
        @HeaderMap headers: Map<String, String>,
        @Path("creatorId") userId: Int,
        @Path("eventId") eventId: Int,
        @Body message: String
    ) : Response<Void>

    //Organizers

    @GET("/api/organizers/{organizerId}/futureEvents")
    suspend fun getFutureEventsOfOrganizer(
        @HeaderMap headers: Map<String, String>,
        @Path("organizerId") userId: Int
    ) : Response<List<Event>>

    @POST("/api/organizers/{organizerId}/invites/{creatorId}")
    suspend fun inviteCreatorToEvent(
        @HeaderMap headers: Map<String, String>,
        @Path("organizerId") organizerId: Int,
        @Path("creatorId") creatorId: Int,
        @Query("eventId") eventId: Int,
        @Body message: String
    ) : Response<Void>
}