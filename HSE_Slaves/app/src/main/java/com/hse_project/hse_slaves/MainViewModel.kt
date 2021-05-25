package com.hse_project.hse_slaves

import android.util.Log
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
    private var token: String = ""

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token;
        return headerMap
    }


    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost(getHeaderMap())
            postResponse.value = response
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
            token = response.headers()["Authorization"].toString()
            Log.d(token, "TTOOOKKKEEENNN")
            Log.d(response.toString(), "       TTOOOKKKEEENNN")
        }
    }
}