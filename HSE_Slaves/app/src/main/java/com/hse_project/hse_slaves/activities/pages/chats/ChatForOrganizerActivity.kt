package com.hse_project.hse_slaves.activities.pages.chats

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.activities.pages.FeedActivity
import com.hse_project.hse_slaves.activities.pages.UserProfileActivity
import com.hse_project.hse_slaves.activities.pages.chats.event.CreateEventActivity
import kotlinx.android.synthetic.main.activity_chat_for_organizer.*
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_feed.menu

class ChatForOrganizerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_for_organizer)

        addEvent.setOnClickListener {
            startActivity(Intent(this@ChatForOrganizerActivity, CreateEventActivity::class.java))
        }

        addMenu()

    }


    private fun addMenu() {
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@ChatForOrganizerActivity, UserProfileActivity::class.java))
                }
                R.id.feed -> {
                    startActivity(Intent(this@ChatForOrganizerActivity, FeedActivity::class.java))
                }
                R.id.chats -> {
                    startActivity(Intent(this@ChatForOrganizerActivity, ChatForOrganizerActivity::class.java))
                }
            }
            false
        }

        menu.selectedItemId = R.id.chats;
        menu.setOnNavigationItemSelectedListener(listener);
    }
}