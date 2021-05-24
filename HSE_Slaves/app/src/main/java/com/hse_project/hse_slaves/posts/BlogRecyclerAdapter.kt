package com.hse_project.hse_slaves.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.activities.pages.FeedActivity
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class BlogRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<BlogPost> = ArrayList()
    lateinit var act: FeedActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BlogViewHolder -> {
                if (position >= items.size - 2) {
                    act.addDataSet()
                }
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList : List<BlogPost>){
        items = blogList
        notifyDataSetChanged()
    }

    class BlogViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

        private val blogDate :TextView = itemView.blog_data
        private val blogName :TextView = itemView.blog_name
        private val blogOrganizerid :TextView = itemView.blog_organizerid
        private val blogDescription :TextView = itemView.blog_description

        fun bind(blogPost: BlogPost){
            blogName.text = blogPost.name
            blogOrganizerid.text = blogPost.organizerid
            blogDate.text = blogPost.date


            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

//            Glide.with(itemView.context)
//                .applyDefaultRequestOptions(requestOptions)
//                .load(blogPost.date)
//                .into(blogImage)
        }
    }
}