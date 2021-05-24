package com.hse_project.hse_slaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.model.Post
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val postResponse: MutableLiveData<retrofit2.Response<Post>> = MutableLiveData()
    val eventResponse: MutableLiveData<retrofit2.Response<Event>> = MutableLiveData()
    val imageResponse: MutableLiveData<retrofit2.Response<List<String>>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            postResponse.value = response
        }
    }

    fun getEvent() {
        viewModelScope.launch {
            val response = repository.getEvent()
            eventResponse.value = response
        }
    }

    fun getImage() {
        viewModelScope.launch {
            val response = repository.getImage()
            imageResponse.value = response
        }
    }

    fun postEvent(event: EventPostMain) {
        viewModelScope.launch {
            repository.postEvent(event)
        }
    }
}