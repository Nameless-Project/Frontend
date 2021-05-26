package com.hse_project.hse_slaves.activities.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.repository.Repository
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TestApiActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_api)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getToken("username3", "password")
        viewModel.tokenResponse.observe(this, { response ->
            if (response.isSuccessful) {
                viewModel.setNewToken(response.headers()["Authorization"].toString());
                Log.i("Token: ", viewModel.token)
            } else {
                Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa", response.toString())
            }
            Log.d("0SSSSSSSSSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSSSSSSS")
        }
        )


        Log.d("1SSSSSSSSSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSSSSSSS")

        viewModel.getUser();
        Log.d("2SSSSSSSSSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSSSSSSS")

        viewModel.userResponse.observe(this, { response ->
            Log.d("3SSSSSSSSSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSSSSSSS")

            if (response.isSuccessful) {
                Log.d("SSSSSSSSSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSSSSSSS")
                Log.d(response.body()?.description.toString(), "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")
                Log.d(response.body()?.password.toString(), "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")
                Log.d(response.body()?.username.toString(), "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")
                Log.d(response.body()?.authorities?.authority.toString(), "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")
            } else {

                Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa", "conver(response)")
                Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa", response.toString())
            }
            Log.d("4SSSSSSSSSSSSSSSSSSSSSSSSS", "SSSSSSSSSSSSSSSSSSSSSSSSSSSS")
        }
        )
    }


    private fun conver(response: Response<User>): String {
        var reader: BufferedReader? = null
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