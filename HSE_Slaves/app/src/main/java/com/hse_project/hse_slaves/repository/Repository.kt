package com.hse_project.hse_slaves.repository

import com.hse_project.hse_slaves.api.RetrofitInstance
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.model.Post
import retrofit2.Response

class Repository {

    suspend fun getPost(HeaderMap : Map<String, String>): retrofit2.Response<Post> {
        return RetrofitInstance.api.getPost(HeaderMap)
    }

    suspend fun getEvent(HeaderMap : Map<String, String>): retrofit2.Response<Event> {
        return RetrofitInstance.api.getEvent(HeaderMap)
    }

    suspend fun getImage(HeaderMap : Map<String, String>): retrofit2.Response<List<String>> {
        return RetrofitInstance.api.getImage(HeaderMap)
    }

    suspend fun postEvent(HeaderMap : Map<String, String>, event: EventPostMain) {
        RetrofitInstance.api.postEvent(HeaderMap, event)
    }

    suspend fun getToken(username : String, password : String): Response<Any> {
        return RetrofitInstance.api.getToken(username, password)
    }

}