package com.hse_project.hse_slaves.repository

import com.hse_project.hse_slaves.api.RetrofitInstance
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPost
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.model.UserRegistration
import retrofit2.Response

class Repository {

    suspend fun getUser(HeaderMap : Map<String, String>, id : Int): retrofit2.Response<User> {
        return RetrofitInstance.api.getUser(HeaderMap, id)
    }

    suspend fun getEvent(HeaderMap : Map<String, String>, id : Int): retrofit2.Response<Event> {
        return RetrofitInstance.api.getEvent(HeaderMap, id)
    }

    suspend fun getImage(HeaderMap : Map<String, String>, id : Int, entity: String): retrofit2.Response<List<String>> {
        return RetrofitInstance.api.getImage(HeaderMap, id, entity)
    }

    suspend fun postEvent(HeaderMap : Map<String, String>, event: EventPost): Response<Void> {
        return RetrofitInstance.api.postEvent(HeaderMap, event)
    }

    suspend fun changeUser(HeaderMap: Map<String, String>, userId: Int, user: UserRegistration): Response<Void>? {
        return RetrofitInstance.api.changeUser(HeaderMap, userId, user)
    }

    //Security

    suspend fun getToken(username : String, password : String): Response<Int> {
        return RetrofitInstance.api.getToken(mapOf("username" to username, "password" to password))
    }

    suspend fun register(event: UserRegistration): Response<Void> {
        return RetrofitInstance.api.register(event)
    }

    //Likes

    suspend fun postLike(HeaderMap : Map<String, String>, userId : Int, eventId : Int): Response<Void> {
        return RetrofitInstance.api.postLike(HeaderMap, userId, eventId)
    }

    suspend fun deleteLike(HeaderMap : Map<String, String>, userId : Int, eventId : Int): Response<Void> {
        return RetrofitInstance.api.deleteLike(HeaderMap, userId, eventId)
    }

    suspend fun checkLike(HeaderMap : Map<String, String>, userId : Int, eventId : Int): Response<Boolean> {
        return RetrofitInstance.api.checkLike(HeaderMap, userId, eventId)
    }

    //Subscriptions

    suspend fun postSubscription(HeaderMap : Map<String, String>, userId : Int, subscriptionId: Int): Response<Void> {
        return RetrofitInstance.api.postSubscription(HeaderMap, mapOf("userId" to userId, "subscriptionId" to subscriptionId))
    }

    suspend fun deleteSubscription(HeaderMap : Map<String, String>, userId : Int, subscriptionId: Int): Response<Void> {
        return RetrofitInstance.api.deleteSubscription(HeaderMap, mapOf("userId" to userId, "subscriptionId" to subscriptionId))
    }

    suspend fun getAllSubscriptions(HeaderMap : Map<String, String>, userId : Int, subscriptionId: Int): Response<List<User>> {
        return RetrofitInstance.api.getAllSubscriptions(HeaderMap, mapOf("userId" to userId, "subscriptionId" to subscriptionId))
    }

    //Feed

    suspend fun getEvents(HeaderMap : Map<String, String>, offset : Int, size :Int, specializations : Set<String>): Response<List<Event>> {
        return RetrofitInstance.api.getEvents(HeaderMap, mapOf("offset" to offset, "size" to size), specializations)
    }

    suspend fun getEventsRecommendation(HeaderMap : Map<String, String>, userId : Int, offset : Int, size :Int, specializations : Set<String>): Response<List<Event>> {
        return RetrofitInstance.api.getEventsRecommendation(HeaderMap, userId, mapOf("offset" to offset, "size" to size), specializations)
    }

    suspend fun getCreators(HeaderMap : Map<String, String>, offset : Int, size :Int, specializations : Set<String>): Response<List<User>> {
        return RetrofitInstance.api.getCreators(HeaderMap, mapOf("offset" to offset, "size" to size), specializations)
    }

    //Application

    suspend fun sendApplicationToEvent(HeaderMap: Map<String, String>, userId: Int, eventId: Int, message : String) : Response<Void> {
        return RetrofitInstance.api.sendApplicationToEvent(HeaderMap, userId, eventId, message)
    }
}