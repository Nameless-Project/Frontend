package com.hse_project.hse_slaves.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.posts.BlogRecyclerAdapter
import com.hse_project.hse_slaves.posts.DataSource
import com.hse_project.hse_slaves.posts.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    private lateinit var blogAdapter: BlogRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet() {
        val data = DataSource.createDataSet("OUR URL")
        blogAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            blogAdapter = BlogRecyclerAdapter()
            recycler_view.adapter = blogAdapter
        }
    }

}