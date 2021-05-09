package com.hse_project.hse_slaves.activities.pages

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.posts.EventPost
import kotlinx.android.synthetic.main.activity_event.*


class EventActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var data: EventPost


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        setData()
        val inflater = LayoutInflater.from(this)

//        for (i in 0..5) {
//            val view = inflater.inflate(R.layout.item_profile_photo, gallery, false)
//
//            val imageView: ImageView = view.findViewById<ImageButton>(R.id.imageView)
//
//            imageView.setImageResource(R.drawable.kek)
//
//            gallery.addView(view)
//        }
        nik_name.text = data.name
        description.text = data.description
        date.text = data.date
        specialization.text = data.specialization
        ratio.text = data.ratio
        geo.text = data.geo

        for (i in data.images!!) {
            val view = inflater.inflate(R.layout.item_event_photo, gallery, false)

            val imageView : ImageView = view.findViewById<ImageButton>(R.id.imageView)


            val bmp1 = BitmapFactory.decodeByteArray(i, 0, i.size)

            assert(bmp1 != null)
            imageView.setImageBitmap(bmp1)

            gallery.addView(view)
        }

    }

    fun setData() {
//
//        val repository = Repository()
//        val viewModelFactory = MainViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
//        viewModel.getUserProfile()
//        viewModel.userProfileResponse.observe(this, { response ->
//            if (response.isSuccessful) {
//                data = (
//                        UserProfilePost(
//                            response.body()?.name.toString(),
//                            response.body()?.description.toString(),
//                            response.body()?.date.toString(),
//                            response.body()?.id.toString(),
//                            response.body()?.specialization.toString(),
//                            response.body()?.ratio.toString(),
//                            response.body()?.geo.toString(),
//                            response.body()?.images
//                        )
//                        )
//                scroll_view.notify()
//            } else {
//                Log.d("Response", response.errorBody().toString())
//            }
//        })
//    }


        data = (EventPost(
            "Boss of the gym",
            "Fisting is 300 bucks. Родился и вырос в Лонг-Айленде в семье учителя карате[1]. С детства занимался боксом, борьбой и карате.\n" +
                    "\n" +
                    "В 21 год потерял отца, в 24 года переехал в Нью-Йорк и, бросив боевые искусства, начал заниматься бодибилдингом, достигнув в этом деле определённых успехов. Через какое-то время он с помощью своего знакомого отправил свои фотографии в обнажённом виде в редакцию журнала Playgirl, что принесло ему награду «Настоящий мужчина месяца» и 500 долларов в качестве гонорара. Эти фотографии попали на глаза режиссёру Джеймсу Френчу, который два года спустя снял Херрингтона в главной роли в одном из своих гей-фильмов.\n" +
                    "\n" +
                    "Херрингтон стал актёром гей-фильмов, получив множество наград от соответствующих организаций[2] и известность в качестве одной из основных «звёзд» этого направления в конце 1990-х годов (по его словам, именно съёмки в этих фильмах помогли ему понять его бисексуальность[3]). Также участвовал во многих телешоу[4].\n" +
                    "\n" +
                    "Осенью 2002 года Херрингтон стал отцом. В первой половине 2000-х годов он работал стриптизёром в гей-клубах США[5][6], но в 2008 году объявил о завершении карьеры и уходе на работу в строительную компанию, принадлежащую его родственникам.\n" +
                    "\n" +
                    "Погиб в ДТП 3 марта 2018 года[7].\n" +
                    "\n" +
                    "О смерти Билли сообщила его мать, Кэтлин Вуд, в Facebook[8] ",
            "1337.69.69",
            "228",
            "Dungeon master",
            "1488",
            "GYM",
            arrayOf(
                application.assets.open("sample.bmp").readBytes(),
                application.assets.open("sample.bmp").readBytes(),
                application.assets.open("sample.bmp").readBytes()
            )
        )
                )
    }
}