package com.hse_project.hse_slaves.posts

import org.json.JSONObject
import java.net.URL

class DataSource{



    companion object{

        fun createDataSet(url : String): ArrayList<BlogPost>{
            val list = ArrayList<BlogPost>()


            val obj : JSONObject = JSONObject(URL(url).readText())



                list.add(
                    BlogPost(
                        obj.getString("name"),
                        obj.getString("description"),
                        obj.getString("date"),
                        obj.getString("organizerid"),
                    )
                )

            return list
        }
    }




}