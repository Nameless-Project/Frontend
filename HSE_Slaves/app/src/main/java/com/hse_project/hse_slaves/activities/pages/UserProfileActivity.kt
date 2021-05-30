package com.hse_project.hse_slaves.activities.pages

import android.annotation.SuppressLint
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.activities.SettingsActivity
import com.hse_project.hse_slaves.activities.pages.chats.ChatForOrganizerActivity
import com.hse_project.hse_slaves.current.IS_TMP_USER
import com.hse_project.hse_slaves.current.TMP_USER_ID
import com.hse_project.hse_slaves.current.USER_ID
import com.hse_project.hse_slaves.image.getBitmapByString
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.description
import kotlinx.android.synthetic.main.activity_user_profile.gallery
import kotlinx.android.synthetic.main.activity_user_profile.menu
import kotlinx.android.synthetic.main.activity_user_profile.nik_name
import kotlinx.android.synthetic.main.activity_user_profile.ratio
import kotlinx.android.synthetic.main.activity_user_profile.specialization


class UserProfileActivity() : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var data: User
    private var isFollowSet: Boolean = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        follow.setOnClickListener {
            //TODO вызывать методы подписка/отписка
            if (isFollowSet) {
                isFollowSet = false
                follow.text = ("unfollow").toString()
                follow.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            } else {
                isFollowSet = true
                follow.text = ("follow").toString()
                follow.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            }
        }

        initViewDependsOnUserType()

        initApi()
    }

    private fun initViewDependsOnUserType() {
        Log.d(IS_TMP_USER.toString(), "SSSSSSSSSSSSSSSSSSSSS")
        if (!IS_TMP_USER || TMP_USER_ID == USER_ID) {
            addMenu()
            settings.setOnClickListener {
                startActivity(Intent(this@UserProfileActivity, SettingsActivity::class.java))
            }
        } else {

            ImageViewCompat.setImageTintList(
                settings,
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            )

            main_layout.removeView(settings)
            main_layout.removeView(menu)
        }
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

        if (IS_TMP_USER) {
            viewModel.getUser(TMP_USER_ID)
        } else {
            viewModel.getMyUser()
        }

        IS_TMP_USER = false

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