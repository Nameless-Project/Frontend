package com.hse_project.hse_slaves.activities.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.activities.SettingsActivity
import com.hse_project.hse_slaves.activities.pages.chats.ChatForOrganizerActivity
import com.hse_project.hse_slaves.image.getBitmapByString
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.menu


class UserProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var data: User


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        settings.setOnClickListener {
            startActivity(Intent(this@UserProfileActivity, SettingsActivity::class.java))
        }
        addMenu()

        initApi()
    }

    private fun addMenu() {
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@UserProfileActivity, UserProfileActivity::class.java))
                }
                R.id.feed -> {
                    startActivity(Intent(this@UserProfileActivity, FeedActivity::class.java))
                }
                R.id.chats -> {
                    startActivity(Intent(this@UserProfileActivity, ChatForOrganizerActivity::class.java))
                }
            }
            false
        }


        menu.selectedItemId = R.id.home;
        menu.setOnNavigationItemSelectedListener(listener);
    }

    private fun initApi() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getMyUser()
        viewModel.userResponse.observe(this, { response ->
            if (response.isSuccessful) {
                data = response.body()!!
                run()
            } else {
                throw RuntimeException(response.toString())
            }
        })
    }

    private fun run() {
        val inflater = LayoutInflater.from(this)

        nik_name.text = data.username
        first_name.text = data.firstName
        second_name.text = data.lastName
        third_name.text = data.patronymic
        description.text = data.description
        type.text = data.userRole
        specialization.text = data.specialization
        ratio.text = data.rating.toString()

        for (i in 0 until data.images.size) {
            val view = inflater.inflate(R.layout.item_event_photo, gallery, false)

            val imageView: ImageView = view.findViewById<ImageButton>(R.id.imageView)

            if (i == 0) {
                image.setImageBitmap(getBitmapByString(data.images[i]))
            } else {
                imageView.setImageBitmap(getBitmapByString(data.images[i]))
                gallery.addView(view)
            }
        }
    }
}