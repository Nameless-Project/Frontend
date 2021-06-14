package com.hse_project.hse_slaves.posts

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.activities.pages.EventActivity
import com.hse_project.hse_slaves.current.EVENT_ID
import com.hse_project.hse_slaves.image.getBitmapByString
import com.hse_project.hse_slaves.model.Event
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*


class BlogRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<Event>()

    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_blog_list_item, parent, false)

        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(event: List<Event>) {
        items.addAll(event)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class BlogViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){

        var id = 0;
        private val nikName: TextView = itemView.nik_name
        private val date: TextView = itemView.date
        private val specialization: TextView = itemView.specialization
        private val ratio: TextView = itemView.ratio
        private val geo: TextView = itemView.geo



        fun bind(event: Event) {
            nikName.text = event.name
            date.text = event.date.substring(0, 10)
            specialization.text = event.specialization
            ratio.text = event.rating.toString()
            geo.text = event.geoData

            id = event.id
            itemView.imageButton.setImageBitmap(getBitmapByString(event.images[0]))

            itemView.setOnClickListener {
                Log.d("PPPPPPPPPPPPPPPPPPP", "AAAAAAAAAAAAAAAAA")
                EVENT_ID = id
                itemView.context.startActivity(Intent(itemView.context, EventActivity::class.java))
            }
        }

    }
}