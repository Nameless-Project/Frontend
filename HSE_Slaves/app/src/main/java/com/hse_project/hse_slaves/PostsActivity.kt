package com.hse_project.hse_slaves

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.posts.BlogPost
import com.hse_project.hse_slaves.posts.BlogRecyclerAdapter
import com.hse_project.hse_slaves.repository.Repository

class PostsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
    }

    fun getNewData(blogAdapter: BlogRecyclerAdapter) {
        val data = ArrayList<BlogPost>()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getEvent()
        viewModel.eventResponse.observe(this, { response ->
            if (response.isSuccessful) {
                data.add(
                    BlogPost(
                        response.body()?.name.toString(),
                        response.body()?.name.toString(),
                        response.body()?.name.toString(),
                        response.body()?.name.toString(),
                    )
                )
                //blogAdapter.submitList(data)
                blogAdapter.notifyDataSetChanged()
            } else {
                Log.d("Response", "")
            }
        })
    }
}