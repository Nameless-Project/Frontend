package com.hse_project.hse_slaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.model.Post
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val postResponse: MutableLiveData<retrofit2.Response<Post>> = MutableLiveData()
    val tokenResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val eventResponse: MutableLiveData<retrofit2.Response<Event>> = MutableLiveData()
    val userResponse: MutableLiveData<retrofit2.Response<User>> = MutableLiveData()
    val imageResponse: MutableLiveData<retrofit2.Response<List<String>>> = MutableLiveData()
    var token: String = ""

    fun setNewToken(token : String) {
        this.token = token
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        assert(token != "")
        headerMap["Authorization"] = token;
        return headerMap
    }

    fun getUser() {
        viewModelScope.launch {
            val response = repository.getUser(getHeaderMap())
            userResponse.value = response
        }
    }


    fun getEvent() {
        viewModelScope.launch {
            val response = repository.getEvent(getHeaderMap())
            eventResponse.value = response
        }
    }

    fun getImage() {
        viewModelScope.launch {
            val response = repository.getImage(getHeaderMap())
            imageResponse.value = response
        }
    }

    fun postEvent(event: EventPostMain) {
        viewModelScope.launch {
            repository.postEvent(getHeaderMap(), event)
        }
    }

    fun getToken(username : String, password : String) {
        viewModelScope.launch {
            val response = repository.getToken(username, password)
            tokenResponse.value = response
        }
    }
}