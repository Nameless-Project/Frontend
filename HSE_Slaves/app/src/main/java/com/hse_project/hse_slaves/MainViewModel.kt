package com.hse_project.hse_slaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse_project.hse_slaves.model.Post
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val postResponse: MutableLiveData<retrofit2.Response<Post>> = MutableLiveData()
    val eventResponse: MutableLiveData<retrofit2.Response<Event>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            postResponse.value = response
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            val response = repository.getUserProfile()
            eventResponse.value = response
        }
    }
}