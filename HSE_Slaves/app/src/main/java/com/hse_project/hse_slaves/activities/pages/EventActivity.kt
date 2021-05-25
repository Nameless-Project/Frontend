package com.hse_project.hse_slaves.activities.pages

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.model.EventPostMain
import com.hse_project.hse_slaves.posts.EventPost
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_event.*
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class EventActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var data: EventPost


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        setData()
        //pushExampleEvent()
    }

    private fun update() {
        val inflater = LayoutInflater.from(this)
        nik_name.text = data.name
        description.text = data.description
        date.text = data.date
        specialization.text = data.specialization
        ratio.text = data.rating.toString()
        geo.text = data.geoData

        for (i in data.imageHashes) {
            val view = inflater.inflate(R.layout.item_event_photo, gallery, false)

            val imageView : ImageView = view.findViewById<ImageButton>(R.id.imageView)

            val bmp1 = BitmapFactory.decodeByteArray(i, 0, i.size)

            //Log.d("AAAAAAAA", bmp1.toString())
            imageView.setImageBitmap(bmp1)

            gallery.addView(view)
        }

    }
    fun setData() {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        pushExampleEvent()
        viewModel.getEvent()
        viewModel.eventResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val resp = response.body()?.images
                val tmp : ArrayList<ByteArray> = ArrayList()
                if (resp != null) {
                    for (i in resp) {
                        tmp.add(Base64.decode(i, DEFAULT))
                    }
                }
                data = (
                        EventPost(
                            response.body()?.id,
                            response.body()?.name.toString(),
                            response.body()?.description.toString(),
                            tmp,
                            response.body()?.organizerIDs,
                            response.body()?.participantsIDs,
                            response.body()?.rating,
                            response.body()?.geoData.toString(),
                            response.body()?.specialization.toString(),
                            response.body()?.date.toString()
                        )
                        )
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        }
        )
        viewModel.getImage()
        viewModel.imageResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val resp = response.body()
                val tmp : ArrayList<ByteArray> = ArrayList()
                if (resp != null) {
                    for (i in resp) {
                        tmp.add(Base64.decode(i, DEFAULT))
                    }
                }
                data.imageHashes = tmp
                update()
            } else {
                Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa", response.errorBody().toString())
            }
        }
        )
       // application.assets.open("kek.txt").writeBytes(application.assets.open("sample.bmp").readBytes())

    }


    fun pushExampleEvent() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val tmp = ArrayList<String>();
        tmp.add(Base64.encodeToString(application.assets.open("sample.bmp").readBytes(), DEFAULT));
        viewModel.postEvent(EventPostMain(
            com.hse_project.hse_slaves.model.EventPost(
                "Gay Party",
                "Fisting is 300 bucks",
                ArrayList(),
                ArrayList(),
                ArrayList(),
                0.228,
                "Gym",
                "LITERATURE",
                "2020-04-04"
            ),
          tmp
        ))
        Log.d("AAAAAAAAAAAA", "BBBBBBBBBBBbbbBBBb")
    }

    private fun conver(response: Response<EventPostMain>): String {
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