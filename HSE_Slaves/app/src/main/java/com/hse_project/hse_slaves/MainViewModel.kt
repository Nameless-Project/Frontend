package com.hse_project.hse_slaves

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hse_project.hse_slaves.current.USER_ID
import com.hse_project.hse_slaves.current.USER_TOKEN
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.model.EventPost
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.model.UserRegistration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val tokenResponse: MutableLiveData<retrofit2.Response<Int>> = MutableLiveData()
    val postEventResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val registerResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val changeUserResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val eventResponse: MutableLiveData<retrofit2.Response<Event>> = MutableLiveData()
    val userResponse: MutableLiveData<retrofit2.Response<User>> = MutableLiveData()
    val imageResponse: MutableLiveData<retrofit2.Response<List<String>>> = MutableLiveData()

    val postLikeResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val deleteLikeResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val checkLikeResponse: MutableLiveData<retrofit2.Response<Boolean>> = MutableLiveData()

    val postSubscriptionResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val deleteSubscriptionResponse: MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()
    val getAllSubscriptionsResponse: MutableLiveData<retrofit2.Response<List<User>>> = MutableLiveData()

    var getEventsResponse: MutableLiveData<retrofit2.Response<List<Event>>> = MutableLiveData()
    val getEventsRecommendationResponse: MutableLiveData<retrofit2.Response<List<Event>>> = MutableLiveData()
    var getCreatorsResponse: MutableLiveData<retrofit2.Response<List<User>>> = MutableLiveData()

    val sendApplicationToEventResponse : MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()

    val getFutureEventsOfOrganizerResponse : MutableLiveData<retrofit2.Response<List<Event>>> = MutableLiveData()

    val inviteCreatorToEventResponse : MutableLiveData<retrofit2.Response<Void>> = MutableLiveData()

    val checkIfCreatorHasInvitationToEventResponse : MutableLiveData<retrofit2.Response<Boolean>> = MutableLiveData()

    val checkIfCreatorHasApplicationFromEventResponse : MutableLiveData<retrofit2.Response<Boolean>> = MutableLiveData()

    var userId: Int = USER_ID


    fun setNewUserId(id: Int) {
        this.userId = id
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        assert(USER_TOKEN != "")
        headerMap["Authorization"] = USER_TOKEN;
        return headerMap
    }

    fun getUser(id: Int) {
        viewModelScope.launch {
            val response = repository.getUser(getHeaderMap(), id)
            userResponse.value = response
        }
    }

    fun getMyUser() {
        viewModelScope.launch {
            val response = repository.getUser(getHeaderMap(), userId)
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

    fun changeUser(user: UserRegistration) {
        viewModelScope.launch {
            val response = repository.changeUser(getHeaderMap(), userId, user)
            changeUserResponse.value = response
        }
    }

    //Security

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

    //Likes

    fun postLike(eventId : Int) {
        viewModelScope.launch {
            val response = repository.postLike(getHeaderMap(), userId, eventId)
            postLikeResponse.value = response
        }
    }


    fun deleteLike(eventId : Int) {
        viewModelScope.launch {
            val response = repository.deleteLike(getHeaderMap(), userId, eventId)
            deleteLikeResponse.value = response
        }
    }

    fun checkLike(eventId : Int) {
        viewModelScope.launch {
            val response = repository.checkLike(getHeaderMap(), userId, eventId)
            checkLikeResponse.value = response
        }
    }

    //Subscriptions

    fun postSubscription(subscriptionId : Int) {
        viewModelScope.launch {
            val response = repository.postSubscription(getHeaderMap(), userId, subscriptionId)
            postSubscriptionResponse.value = response
        }
    }


    fun deleteSubscription(subscriptionId : Int) {
        viewModelScope.launch {
            val response = repository.deleteSubscription(getHeaderMap(), userId, subscriptionId)
            deleteSubscriptionResponse.value = response
        }
    }

    fun getAllSubscriptions(subscriptionId : Int) {
        viewModelScope.launch {
            val response = repository.getAllSubscriptions(getHeaderMap(), userId, subscriptionId)
            getAllSubscriptionsResponse.value = response
        }
    }

    //Feed

    fun getEvents(offset : Int, size : Int, specializations : Set<String>) {
        viewModelScope.launch {
            val response = repository.getEvents(getHeaderMap(), offset, size, specializations)
            getEventsResponse.value = response
        }
    }

    fun getEventsRecommendation(offset : Int, size : Int, specializations : Set<String>) {
        viewModelScope.launch {
            val response = repository.getEventsRecommendation(getHeaderMap(), userId, offset, size, specializations)
            getEventsRecommendationResponse.value = response
        }
    }

    fun getCreators(offset : Int, size : Int, specializations : Set<String>) {
        viewModelScope.launch {
            val response = repository.getCreators(getHeaderMap(), offset, size, specializations)
            getCreatorsResponse.value = response
        }
    }

    //Application

    fun sendApplicationToEvent(eventId: Int, message: String) {
        viewModelScope.launch {
            val response = repository.sendApplicationToEvent(getHeaderMap(), userId, eventId, message)
            sendApplicationToEventResponse.value = response
        }
    }

    //Organizers

    fun getFutureEventsOfOrganizer() {
        viewModelScope.launch {
            val response = repository.getFutureEventsOfOrganizer(getHeaderMap(), userId)
            getFutureEventsOfOrganizerResponse.value  = response
        }
    }

    fun inviteCreatorToEvent(creatorId : Int, eventId: Int, message: String) {
        viewModelScope.launch {
            val response = repository.inviteCreatorToEvent(getHeaderMap(), userId, creatorId, eventId, message)
            inviteCreatorToEventResponse.value = response
        }
    }

    fun checkIfCreatorHasInvitationToEvent(eventId: Int) {
        viewModelScope.launch {
            val response = repository.checkIfCreatorHasInvitationToEvent(getHeaderMap(), userId, eventId)
            checkIfCreatorHasInvitationToEventResponse.value = response
        }
    }

    fun checkIfCreatorHasApplicationFromEvent(eventId: Int) {
        viewModelScope.launch {
            val response = repository.checkIfCreatorHasApplicationFromEvent(getHeaderMap(), userId, eventId)
            checkIfCreatorHasApplicationFromEventResponse.value = response
        }
    }

}