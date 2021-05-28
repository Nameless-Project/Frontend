package com.hse_project.hse_slaves.activities.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.model.EventPost
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.model.UserRegistration
import com.hse_project.hse_slaves.repository.Repository
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TestApiActivity : AppCompatActivity() {
    /*
     * Сейчас тестируются:
     * Events
     * getEvent()
     * postEvent()
     *
     * ===================
     *
     * Feed
     *
     * ===================
     *
     * Image
     *
     * ===================
     *
     * User
     * getUser()
     *
     * ===================
     *
     * Security
     * register()
     * getToken()
     */

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_api)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        /*
         *  Если запускаем первый раз после билда,
         * то есть база данных пустая, необходимо ее заполнить:
         * addUsers(1)
         * из него вызываем
         * getToken()
         * из него вызываем
         * addEvents() там творится что-то странное
         * из него вызываем
         * run()
         * потом переделываем все обратно и запускаем еще раз
         */
        getToken()
    }

    private fun addUsers(i: Int) {
        Log.d("AddUser", i.toString())
        viewModel.register(
            UserRegistration(
                "USER",
                "firstName$i",
                "lastName$i",
                "patronymic$i",
                "username$i",
                "password$i",
                "ART",
                "description$i",
                ArrayList(),
            )
        )
        viewModel.registerResponse.observe(this, { response ->
            if (response.isSuccessful) {
                if (i == 10) {
                    getToken()
                } else {
                    addUsers(i + 1)
                }
            } else {
                Log.d("Error response", response.toString())
            }
        })
    }

    private fun getToken() {
        Log.d("getToken", "AAA")
        viewModel.getToken("username3", "password3")
        viewModel.tokenResponse.observe(this, { response ->
            if (response.isSuccessful) {
                viewModel.setNewToken(response.headers()["Authorization"].toString())
                Log.i("Token: ", viewModel.token)
                run()
            } else {
                Log.d("Error response", response.toString())
            }
        }
        )
    }

    private fun run() {
        Log.d("run", "AAA")
        viewModel.getUser(2)
        viewModel.userResponse.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d(response.body()?.description.toString(), ": Description")
                Log.d(response.body()?.password.toString(), ": Password")
                Log.d(response.body()?.username.toString(), ": Username")
                Log.d(response.body()?.authorities?.get(0).toString(), ": Authority")
            } else {
                Log.d("Error response", response.toString())
            }
        }
        )

        viewModel.getEvent(4)
        viewModel.eventResponse.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d(response.body()?.description.toString(), ": Description")
                Log.d(response.body()?.name.toString(), ": Name")
                Log.d(response.body()?.date.toString(), ": Date")
                Log.d(response.body()?.rating.toString(), ": Rating")
            } else {
                Log.d("Error response", response.toString())
            }
        })
    }

    private fun addEvents(i: Int) {
        Log.d("addEvents$i", "AAA")
        viewModel.postEvent(
            EventPost(
                "name$i",
                "description$i",
                i.toDouble() / 10,
                "geoData$i",
                "ART",
                "2020-04-04",
                ArrayList()
            )
        )
        viewModel.postEventResponse.observe(this, { response ->
            if (response.isSuccessful) {
                if (i == 10) {
                    run()
                } else {
                    addEvents(i + 1)
                }
                /*
                 *  Тут творится какая-то неведомая дичь
                 */
            } else {
                Log.d("Error response", response.toString())
            }
        })
    }

    private fun convert(response: Response<User>): String {
        val reader: BufferedReader?
        val sb = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(response.errorBody()?.byteStream()))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return sb.toString()
    }
}