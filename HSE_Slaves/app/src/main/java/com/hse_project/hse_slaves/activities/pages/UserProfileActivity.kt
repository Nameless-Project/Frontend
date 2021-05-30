package com.hse_project.hse_slaves.activities.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import java.util.concurrent.atomic.AtomicBoolean


class UserProfileActivity() : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var data: User
    private var isFollowSet: Boolean = false
    private var isCheckingSubscription: AtomicBoolean = AtomicBoolean(false)


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        initViewDependsOnUserType()

        initApi()
    }

    private fun checkSubscription() {
        if (!IS_TMP_USER || TMP_USER_ID == USER_ID || data.userRole == "USER") {
            IS_TMP_USER = false
            update()
            return
        }
        IS_TMP_USER = false
        //TODO когда появится метод который возвращает bool поменять на него
        viewModel.getAllSubscriptions(TMP_USER_ID)
        viewModel.getAllSubscriptionsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                isFollowSet = response.body()?.contains(data)!!
                Log.d("QQQQQQQQQQQQQQQ", isFollowSet.toString())
                addFollowListener()
                update()
            } else {
                Log.d("AAAAAAAAAAAAAAAAAAAAAAA", response.toString())
            }
        })
    }

    private fun addFollowListener() {
        follow.setOnClickListener {
            if (isCheckingSubscription.compareAndSet(false, true)) {
                Log.d("AAAA", isFollowSet.toString())
                if (isFollowSet) {
                    viewModel.deleteSubscription(data.id)
                    viewModel.deleteSubscriptionResponse.observe(this, { response1 ->
                        if (response1.isSuccessful) {
                            isFollowSet = false
                            follow.text = ("follow").toString()
                            follow.setBackgroundColor(
                                ContextCompat.getColor(
                                    this,
                                    R.color.purple_500
                                )
                            )
                        } else {
                            Log.d("Delete subscription", response1.toString())
                        }
                        isCheckingSubscription.set(false)
                    })
                } else {
                    viewModel.postSubscription(data.id)
                    viewModel.postSubscriptionResponse.observe(this, { response1 ->
                        if (response1.isSuccessful) {
                            isFollowSet = true
                            follow.text = ("unfollow").toString()
                            follow.setBackgroundColor(
                                ContextCompat.getColor(
                                    this,
                                    R.color.grey
                                )
                            )

                        } else {
                            Log.d("Post subscription", response1.toString())
                        }
                        isCheckingSubscription.set(false)
                    })
                }
            }
        }
    }

    private fun initViewDependsOnUserType() {
        Log.d(IS_TMP_USER.toString(), "SSSSSSSSSSSSSSSSSSSSS")
        if (!IS_TMP_USER || TMP_USER_ID == USER_ID) {
            addMenu()
//            follow.text = ("").toString()
//            follow.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
//            main_layout.removeView(follow)
            follow.visibility = View.INVISIBLE;

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
                    startActivity(
                        Intent(
                            this@UserProfileActivity,
                            ChatForOrganizerActivity::class.java
                        )
                    )
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


        viewModel.userResponse.observe(this, { response ->
            if (response.isSuccessful) {
                data = response.body()!!
                if (data.userRole == "USER") {
                    follow.visibility = View.INVISIBLE;
                }
                checkSubscription()
            } else {
                throw RuntimeException(response.toString())
            }
        })
    }

    private fun update() {
        val inflater = LayoutInflater.from(this)

        nik_name.text = data.username
        first_name.text = data.firstName
        second_name.text = data.lastName
        third_name.text = data.patronymic
        description.text = data.description
        type.text = data.userRole
        specialization.text = data.specialization
        ratio.text = data.rating.toString()

        if (isFollowSet) {
            follow.text = ("unfollow").toString()
            follow.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
        } else {
            follow.text = ("follow").toString()
            follow.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
        }

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