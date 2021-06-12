package com.hse_project.hse_slaves

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hse_project.hse_slaves.activities.pages.chats.event.CreateEventActivity
import kotlinx.android.synthetic.main.fragment_chat_for_organizer.*

class ChatForOrganizerFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvent.setOnClickListener {
            startActivity(Intent(context, CreateEventActivity::class.java))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_for_organizer, container, false)
    }
}