package com.hse_project.hse_slaves.activities.pages

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.posts.BlogRecyclerAdapter
import com.hse_project.hse_slaves.posts.EventPostGet
import com.hse_project.hse_slaves.posts.TopSpacingItemDecoration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_feed.menu
import kotlinx.android.synthetic.main.activity_user_profile.*
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FeedActivity : AppCompatActivity() {

    private lateinit var blogAdapter: BlogRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var myLayoutManager: LinearLayoutManager

    private var isLoading = false

    private fun addMenu() {
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@FeedActivity, UserProfileActivity::class.java))
                }
                R.id.feed -> {
                    startActivity(Intent(this@FeedActivity, FeedActivity::class.java))
                }
                R.id.chats -> {
                    //TODO do chats activity
                }
            }
            false
        }

        menu.setOnNavigationItemSelectedListener(listener);
    }

    fun pushExampleEvent() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val tmp = ArrayList<String>();
        tmp.add(
            Base64.encodeToString(
                application.assets.open("sample.bmp").readBytes(),
                Base64.DEFAULT
            )
        );
        viewModel.postEvent(
            com.hse_project.hse_slaves.model.EventPost(
                "Gay Party",
                "Fisting is 300 bucks",
                0.228,
                "Gym",
                "LITERATURE",
                "2020-04-04",
                tmp
            )
        )
        Log.d("AAAAAAAAAAAA", "BBBBBBBBBBBbbbBBBb")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        addMenu()

        myLayoutManager = LinearLayoutManager(this)
        initRecyclerView()

        pushExampleEvent()


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        addDataSet()
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


        var eventPostGet: EventPostGet
        viewModel.getEvent(1)
        viewModel.eventResponse.observe(this, { response ->
            if (response.isSuccessful) {
                eventPostGet = EventPostGet(
                    response.body()?.id,
                    response.body()?.name.toString(),
                    response.body()?.description.toString(),
                    ArrayList(),
                    response.body()?.organizerIDs,
                    response.body()?.participantsIDs,
                    response.body()?.rating,
                    response.body()?.geoData.toString(),
                    response.body()?.specialization.toString(),
                    response.body()?.date.toString()
                )

                viewModel.getImage(1, "USER")
                viewModel.imageResponse.observe(this, { r ->
                    if (r.isSuccessful) {
                        val resp = r.body()
                        val tmp: ArrayList<ByteArray> = ArrayList()
                        if (resp != null) {
                            for (i in resp) {
                                tmp.add(Base64.decode(i, Base64.DEFAULT))
                            }
                        }
                        eventPostGet.imageHashes = tmp
                        blogAdapter.submitList(eventPostGet)
                    } else {
                        Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa", r.errorBody().toString())
                    }
                }
                )
            } else {
                Log.d("Response", conver(response))
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