package com.hse_project.hse_slaves.activities.pages

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.posts.UserProfilePost
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var data: UserProfilePost


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
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
        nik_name.text = data.nikName
        first_name.text = data.firstName
        second_name.text = data.secondName
        third_name.text = data.thirdName
        description.text = data.description
        type.text = data.type
        specialization.text = data.specialization
        ratio.text = data.ratio

        for (i in data.images!!) {
            val view = inflater.inflate(R.layout.item_event_photo, gallery, false)

            val imageView : ImageView = view.findViewById<ImageButton>(R.id.imageView)


            val bmp1 = BitmapFactory.decodeByteArray(i, 0, i.size)

            assert(bmp1 != null)
            imageView.setImageBitmap(bmp1)

            image.setImageBitmap(bmp1)
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


        data = (UserProfilePost(
            "Mikhail",
            "Zubenko",
            "Petrovich",
            "Maphioznik",
            "Мафиозник — герой вирусного видео про мужчину в розовом костюме, который представляется как вор в законе по кличке Мафиозник.\nПроисхождение\n" +
                    "\n" +
                    "Видео про мафиозника стало популярным после обзора Макса +100500 в конце января 2018 года (выпуск “Песец и мойва”). До этого большого вирусного эффекта ролик не имел.\n" +
                    "\n" +
                    "Оригинал был загружен 20 июня 2015 года пользователем Y. Poruchnyk. Автор 15-секундного ролика “Во все времена” устроил “допрос” мужчине в розовом пиджаке и шляпе такого же цвета.\n— Фамилия, имя, отчество?\n" +
                    "\n" +
                    "— Зубенко Михаил Петрович\n" +
                    "\n" +
                    "— Кем являетесь?\n" +
                    "\n" +
                    "— Вор в законе.\n" +
                    "\n" +
                    "— Где именно?\n— Шумиловский городок.\n" +
                    "\n" +
                    "— Кличка?\n" +
                    "\n" +
                    "— Мафиозник.\nАвторское видео набрало всего 6 тысяч просмотров за 2,5 года, зато его скопировали десятки других аккаунтов. Самой популярной стала копия от 6 сентября, набравшая почти 50 тысяч просмотров.\n" +
                    "\n" +
                    "Еще раньше, в феврале 2015 года на ютубе вышло видео “Циганский барон из Ташкента“. Его герой – тот же самый человек. Но ролик стал вирусным только через несколько лет, и то благодаря перезаливам.\nПро “Мафиозника” мало что известно. Как указал автор, его зовут Михаил Петрович Зубенко, мужчина также известен как Михась. Шумиловский городок, о котором он говорит в видео, находится в Ташкенте. Так что, судя по всему, действие происходит в Узбекистане.\n",
            "ContentMaker",
            "1337",
            "Gangsta",
            "228",
            arrayOf(
                application.assets.open("zubenko.bmp").readBytes(),
                application.assets.open("zubenko.bmp").readBytes(),
                application.assets.open("zubenko.bmp").readBytes(),
                application.assets.open("zubenko.bmp").readBytes(),
                application.assets.open("zubenko.bmp").readBytes()
            )
        )
                )
    }
}