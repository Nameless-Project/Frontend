package com.hse_project.hse_slaves.api

import com.hse_project.hse_slaves.model.Post
import retrofit2.http.GET

interface SimpleApi {

    @GET("/events/Another")
    suspend fun getPost(): retrofit2.Response<Post>
}