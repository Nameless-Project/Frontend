package com.hse_project.hse_slaves.posts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hse_project.hse_slaves.R
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*


class BlogRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<EventPostGet>()

    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_blog_list_item, parent, false)
        )
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

    fun submitList(eventPostGet: EventPostGet) {
        items.add(eventPostGet)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class BlogViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val nikName: TextView = itemView.nik_name
        private val date: TextView = itemView.date
        private val specialization: TextView = itemView.specialization
        private val ratio: TextView = itemView.ratio
        private val geo: TextView = itemView.geo

        fun bind(eventPostGet: EventPostGet) {
            nikName.text = eventPostGet.name
            date.text = eventPostGet.date
            specialization.text = eventPostGet.specialization
            ratio.text = eventPostGet.rating.toString()
            geo.text = eventPostGet.geoData

            val img = eventPostGet.imageHashes[4]
            Log.d(img.size.toString(), "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa")
            val bmp1 = BitmapFactory.decodeByteArray(img, 0, img.size)
            val height: Int = bmp1.height * 512 / bmp1.width
            val scale = Bitmap.createScaledBitmap(bmp1, 512, height, true)
            itemView.imageButton.setImageBitmap(scale)
        }
    }
}