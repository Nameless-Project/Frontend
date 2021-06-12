package com.hse_project.hse_slaves.activities.pages

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.activities.MainActivity
import com.hse_project.hse_slaves.current.IS_TMP_USER
import com.hse_project.hse_slaves.current.TMP_USER_ID
import com.hse_project.hse_slaves.image.getBitmapByString
import com.hse_project.hse_slaves.model.Event
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_event.*
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicBoolean


class EventActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var data: Event
    private var isLikeSet: Boolean = false
    private var isCheckingLike: AtomicBoolean = AtomicBoolean(false)
    private var likeDelta: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        initApi()
        organizer_nik.setOnClickListener {
            IS_TMP_USER = true
            startActivity(Intent(this@EventActivity, MainActivity::class.java))
        }
        organizer.setOnClickListener {
            IS_TMP_USER = true
            startActivity(Intent(this@EventActivity, MainActivity::class.java))
        }
        setData()

        addListenerForLikes()
    }

    private fun addListenerForLikes() {
        likes_icon.setOnClickListener {
            Log.d("Aaa", "KKKKKKKKKKKKKKKK")
            if (isCheckingLike.compareAndSet(false, true)) {
                if (isLikeSet) {
                    viewModel.deleteLike(data.id)
                    viewModel.deleteLikeResponse.observe(this, { response1 ->
                        if (response1.isSuccessful) {
                            isLikeSet = false
                            changeLikeColor()
                        } else {
                            Log.d("Delete Like", response1.toString())
                        }
                        isCheckingLike.set(false)
                    })
                } else {
                    viewModel.postLike(data.id)
                    viewModel.postLikeResponse.observe(this, { response1 ->
                        if (response1.isSuccessful) {
                            isLikeSet = true
                            changeLikeColor()
                        } else {
                            Log.d("Post Like", response1.toString())
                        }
                        isCheckingLike.set(false)
                    })
                }
            }
        }
    }

    private fun update() {
        val inflater = LayoutInflater.from(this)
        nik_name.text = data.name
        description.text = data.description
        date.text = data.date.substring(0, 10)
        specialization.text = data.specialization
        ratio.text = data.rating.toString()
        geo.text = data.geoData

        changeLikeColor()

        val likesString = data.likes.size.toString() + " likes"
        likes_number.text = likesString

        for (i in data.images.indices) {
            val view = inflater.inflate(R.layout.item_event_photo, gallery, false)

            val imageView: ImageView = view.findViewById<ImageButton>(R.id.imageView)


            imageView.setImageBitmap(getBitmapByString(data.images[i]))
            gallery.addView(view)
        }

    }

    private fun changeLikeColor() {
        var likesString = ""
        likesString = if (isLikeSet) {
            ImageViewCompat.setImageTintList(
                likes_icon,
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            )
            (data.likes.size + likeDelta).toString() + " likes"
        } else {
            ImageViewCompat.setImageTintList(
                likes_icon,
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            )
            (data.likes.size + likeDelta - 1).toString() + " likes"
        }


        likes_number.text = likesString
    }

    fun setData() {
        viewModel.getEvent(1)
        viewModel.eventResponse.observe(this, { response ->
            if (response.isSuccessful) {
                data = Event(
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
                Log.d("PPPPPPPPPPPP", response.body()?.organizerId.toString())
                Log.d("PPPPPPPPPPPP", response.body()?.name.toString())
                Log.d("OOOOOOOOOOOO", data.organizerId.toString())
                getOrganizer()
                checkLike()
            } else {
                Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAA", "BBBBB")
                //Log.d("Response", conver(response))
            }
        })
        // application.assets.open("kek.txt").writeBytes(application.assets.open("sample.bmp").readBytes())

    }

    private fun getOrganizer() {
        Log.d("IIIIIIIIIIIIIIIIIII", data.organizerId.toString())
        viewModel.getUser(data.organizerId)
        TMP_USER_ID = data.organizerId
        viewModel.userResponse.observe(this, { response ->
            if (response.isSuccessful) {
                organizer_nik.text = response.body()?.username
            } else {
                Log.d("AAAAAAA", response.toString())
            }
        })
    }

    private fun checkLike() {
        viewModel.checkLike(data.id)
        viewModel.checkLikeResponse.observe(this, { response ->
            if (response.isSuccessful) {
                isLikeSet = response.body()!!
                if (isLikeSet) {
                    likeDelta = 0
                } else {
                    likeDelta = 1
                }
                update()
            } else {
                Log.d("AAAAAAAAAAAAAAAAAAAAAAA", conver(response))
            }
        })
    }

    private fun initApi() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private fun conver(response: Response<Boolean>): String {
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

    private fun conver1(response: Response<Void>): String {
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