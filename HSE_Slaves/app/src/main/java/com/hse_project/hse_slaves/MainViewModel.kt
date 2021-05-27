package com.hse_project.hse_slaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPost
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.model.UserRegistration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val tokenResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val postEventResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val registerResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val eventResponse: MutableLiveData<retrofit2.Response<Event>> = MutableLiveData()
    val userResponse: MutableLiveData<retrofit2.Response<User>> = MutableLiveData()
    val imageResponse: MutableLiveData<retrofit2.Response<List<String>>> = MutableLiveData()


    var token: String = ""

    fun setNewToken(token: String) {
        this.token = token
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        assert(token != "")
        headerMap["Authorization"] = token;
        return headerMap
    }

    fun getUser(id: Int) {
        viewModelScope.launch {
            val response = repository.getUser(getHeaderMap(), id)
            userResponse.value = response
        }
    }


    fun getEvent(id: Int) {
        viewModelScope.launch {
            val response = repository.getEvent(getHeaderMap(), id)
            eventResponse.value = response
        }
    }

    fun getImage(id: Int, entity: String) {
        viewModelScope.launch {
            val response = repository.getImage(getHeaderMap(), id, entity)
            imageResponse.value = response
        }
    }

    fun postEvent(event: EventPost) {
        viewModelScope.launch {
            val response = repository.postEvent(getHeaderMap(), event)
            postEventResponse.value = response
        }
    }

    fun getToken(username: String, password: String) {
        viewModelScope.launch {
            val response = repository.getToken(username, password)
            tokenResponse.value = response
        }
    }

    fun register(event: UserRegistration) {
        viewModelScope.launch {
            val response = repository.register(event)
            registerResponse.value = response
        }
    }
}