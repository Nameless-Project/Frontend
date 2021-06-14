package com.hse_project.hse_slaves

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.posts.BlogRecyclerAdapter
import com.hse_project.hse_slaves.posts.TopSpacingItemDecoration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.fragment_feed.*
import java.util.concurrent.atomic.AtomicBoolean

class FeedFragment : Fragment() {


    private lateinit var blogAdapter: BlogRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var myLayoutManager: LinearLayoutManager

    private var offset: Int = 0
    private var isLoading : AtomicBoolean = AtomicBoolean(false)

    private val filterSet : MutableSet<String> = HashSet()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFilterListener()
        addScrollListener()
        initRecyclerView()
        initApi()
        addDataSet()
    }

    private fun addFilterListener() {
        art_chip.isChecked = filterSet.contains("ART")
        music_chip.isChecked = filterSet.contains("MUSIC")
        literature_chip.isChecked = filterSet.contains("LITERATURE")
        photography_chip.isChecked = filterSet.contains("PHOTOGRAPHY")

        art_chip.setOnClickListener {
            if (art_chip.isChecked) {
                filterSet.add("ART")
            } else {
                filterSet.remove("ART")
            }
            refresh()
        }

        music_chip.setOnClickListener {
            if (music_chip.isChecked) {
                filterSet.add("MUSIC")
            } else {
                filterSet.remove("MUSIC")
            }
            refresh()
        }

        literature_chip.setOnClickListener {
            if (literature_chip.isChecked) {
                filterSet.add("LITERATURE")
            } else {
                filterSet.remove("LITERATURE")
            }
            refresh()
        }

        photography_chip.setOnClickListener {
            if (photography_chip.isChecked) {
                filterSet.add("PHOTOGRAPHY")
            } else {
                filterSet.remove("PHOTOGRAPHY")
            }
            refresh()
        }
    }

    private fun refresh() {
        blogAdapter.clear()
        offset = 0
        addDataSet()
    }


    private fun addScrollListener() {
        myLayoutManager = LinearLayoutManager(context)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = myLayoutManager.childCount
                val pastVisibleItem = myLayoutManager.findFirstVisibleItemPosition()
                val total = blogAdapter.itemCount
                if (!isLoading.get()) {
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
        if (!isLoading.compareAndSet(false, true)) {
            return
        }
        //progressBar.visibil
        Log.d("QQQQQQQQQQQQ", offset.toString())
        var event: List<Event>
        viewModel.getEventsResponse = MutableLiveData()
        viewModel.getEvents(offset, 10, filterSet)
        viewModel.getEventsResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {

                offset += response.body()!!.size
                event = response.body()!!
                Log.d("WWWWWWWWWWWWWWWWW", event.size.toString())
                recycler_view.post {
                    blogAdapter.submitList(event)
                }

                //blogAdapter.submitList(event)
            } else {
                Log.d("QQQQQQQQQQQQQ", response.toString())
                //Log.d("Response", conver(response))
            }
            isLoading.set(false)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }
}