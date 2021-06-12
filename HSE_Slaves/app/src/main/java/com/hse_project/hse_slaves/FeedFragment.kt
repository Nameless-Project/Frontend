package com.hse_project.hse_slaves

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.posts.BlogRecyclerAdapter
import com.hse_project.hse_slaves.posts.TopSpacingItemDecoration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {


    private lateinit var blogAdapter: BlogRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var myLayoutManager: LinearLayoutManager

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addScrollListener()
        initRecyclerView()
        initApi()
        addDataSet()
    }


    private fun addScrollListener() {
        myLayoutManager = LinearLayoutManager(context)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    }

    private fun initApi() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    fun addDataSet() {
        isLoading = true
        //progressBar.visibil


        var event: Event
        viewModel.getEvent(1)
        viewModel.eventResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                event = Event(
                    response.body()?.id!!,
                    response.body()?.name.toString(),
                    response.body()?.description.toString(),
                    response.body()?.images!!,
                    response.body()?.organizerId!!,
                    response.body()?.participantsIDs!!,
                    response.body()?.rating!!,
                    response.body()?.geoData.toString(),
                    response.body()?.specialization.toString(),
                    response.body()?.date.toString(),
                    response.body()?.likes!!
                )
                blogAdapter.submitList(event)
            } else {
                Log.d("BLYAAAAAAAAAAAAAAAAAAAAAAAAA", "EBANAROT")
                //Log.d("Response", conver(response))
            }
        })
        isLoading = false
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }
}