package com.hse_project.hse_slaves.activities.pages

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.posts.BlogPost
import com.hse_project.hse_slaves.posts.BlogRecyclerAdapter
import com.hse_project.hse_slaves.posts.TopSpacingItemDecoration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_feed.*
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FeedActivity : AppCompatActivity() {

    private lateinit var blogAdapter: BlogRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var myLayoutManager: LinearLayoutManager

    private var isLoading = false

    private val data = ArrayList<BlogPost>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        myLayoutManager = LinearLayoutManager(this)

        initRecyclerView()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        addDataSet()
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = myLayoutManager.childCount
                val pastVisibleItem = myLayoutManager.findFirstVisibleItemPosition()
                val total = blogAdapter.itemCount
                if (!isLoading) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        addDataSet()
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }

        })
//        while (true) {
//            if (blogAdapter.isFull) {
//                addDataSet()
//                blogAdapter.isFull = false
//            }
//        }

    }

    fun addDataSet() {
        isLoading = true
        //progressBar.visibil


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
                blogAdapter.submitList(data)
                isLoading = false
            } else {
                Log.d("Response", conver(response))
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            recycler_view.layoutManager = myLayoutManager
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            blogAdapter = BlogRecyclerAdapter()
            recycler_view.adapter = blogAdapter
        }

    }

    private fun conver(response: Response<Event>): String {
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