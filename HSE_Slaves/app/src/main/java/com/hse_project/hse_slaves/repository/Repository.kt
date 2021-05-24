package com.hse_project.hse_slaves.repository

import com.hse_project.hse_slaves.api.RetrofitInstance
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.model.Post

class Repository {

    suspend fun getPost(): retrofit2.Response<Post> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getEvent(): retrofit2.Response<Event> {
        return RetrofitInstance.api.getEvent()
    }

    suspend fun getImage(): retrofit2.Response<List<String>> {
        return RetrofitInstance.api.getImage()
    }

    suspend fun postEvent(event: EventPostMain) {
        RetrofitInstance.api.postEvent(event)
    }

}