package com.hse_project.hse_slaves.repository

import com.hse_project.hse_slaves.api.RetrofitInstance
import com.hse_project.hse_slaves.model.Post

class Repository {

    suspend fun getPost(): retrofit2.Response<Post> {
        return RetrofitInstance.api.getPost()
    }

}