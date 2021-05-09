package com.hse_project.hse_slaves

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.repository.Repository

class PostsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.postResponse.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.name.toString())
                Log.d("Response", response.body()?.description.toString())
                Log.d("Response", response.body()?.date!!)
                Log.d("Response", response.body()?.organizerid!!)
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }
}