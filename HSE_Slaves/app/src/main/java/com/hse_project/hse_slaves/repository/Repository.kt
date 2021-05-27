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

    suspend fun getToken(username : String, password : String): Response<Void> {
        return RetrofitInstance.api.getToken(mapOf("username" to username, "password" to password))
    }

    suspend fun register(event: UserRegistration): retrofit2.Response<Void> {
        return RetrofitInstance.api.register(event)
    }
}