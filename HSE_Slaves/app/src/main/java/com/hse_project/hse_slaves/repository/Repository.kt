package com.hse_project.hse_slaves.repository

import com.hse_project.hse_slaves.api.RetrofitInstance
import com.hse_project.hse_slaves.model.Post
import com.hse_project.hse_slaves.model.Event

class Repository {

    suspend fun getPost(): retrofit2.Response<Post> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getUserProfile(): retrofit2.Response<Event> {
        return RetrofitInstance.api.getEvent()
    }

}