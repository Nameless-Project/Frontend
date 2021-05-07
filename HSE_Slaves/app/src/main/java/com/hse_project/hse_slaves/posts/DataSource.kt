package com.hse_project.hse_slaves.posts


class DataSource{



    companion object{

        fun createDataSet(url : String): ArrayList<BlogPost>{
            val list = ArrayList<BlogPost>()




            //val obj = JSONArray(URL(url).readText())
//            var x = 0
//
//            while (x < obj.length()) {
//
//                val tmpObj = obj.getJSONObject(x)
//
//                list.add(
//                    BlogPost(
//                        tmpObj.getString("ID"),
//                        tmpObj.getString("Party"),
//                        tmpObj.getString("FullName"),
//                        tmpObj.getString("Terms"),
//                    )
//                )
//            }

            list.add(
                BlogPost(
                    "obj",
                    "description",
                    "date",
                    "organizerid",
                )
            )

            return list
        }
    }




}