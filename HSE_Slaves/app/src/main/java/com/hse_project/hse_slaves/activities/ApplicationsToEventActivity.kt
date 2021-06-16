package com.hse_project.hse_slaves.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.image.getBitmapByString
import com.hse_project.hse_slaves.model.User
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.item_creators.view.*

class ApplicationsToEventActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    private val creators = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applications_to_event)
        initApi()

        addListView()

    }

    private fun addListView() {
        //TODO получаем список криэйтеров подавших заявку
    }

    private inner class CreatorAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return creators.size
        }

        override fun getItem(position: Int): Any {
            return Any()
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.item_creators, null)
            view.item_creators_text.text = creators[position].username

            if (creators[position].images.isNotEmpty()) {
                view.item_creators_button.setImageBitmap(
                    getBitmapByString(
                        creators[position].images[0]
                    )
                )
            }

            view.accept.setOnClickListener {
                //TODO обрабатываем нажатие
            }

            view.decline.setOnClickListener {

            }

            return view
        }

    }

    private fun initApi() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


}