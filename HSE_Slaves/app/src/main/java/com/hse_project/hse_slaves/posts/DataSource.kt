package com.hse_project.hse_slaves.posts

import org.json.JSONObject
import java.net.URL

class DataSource{



    companion object{

        fun createDataSet(url : String): ArrayList<BlogPost>{
            val list = ArrayList<BlogPost>()


            val obj : JSONObject = JSONObject(URL(url).readText())

            val blogArray = obj.getJSONArray("blogs")

            for (i in 0 until blogArray.length()) {
                val blog = blogArray.getJSONObject(i)

                list.add(
                    BlogPost(
                        blog.getString("title"),
                        blog.getString("body"),
                        blog.getString("image"),
                        blog.getString("username"),
                    )
                )
            }
            return list
        }
    }




}