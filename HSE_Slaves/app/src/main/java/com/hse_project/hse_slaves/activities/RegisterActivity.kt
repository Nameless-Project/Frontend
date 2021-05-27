package com.hse_project.hse_slaves.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.model.UserRegistration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel


    private var userRole: String = "USER"
    private var specialization: String = "ART"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val optionsSpecialization = arrayOf("ART", "LITERATURE", "MUSIC", "PHOTOGRAPHY")
        specialization_.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, optionsSpecialization)
        specialization_.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                userRole = optionsSpecialization[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                userRole = optionsSpecialization[0]
            }
        }

        val optionsUserRole = arrayOf("USER", "ORGANIZER", "CREATOR")
        user_role.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, optionsUserRole)
        user_role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                userRole = optionsUserRole[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                userRole = optionsUserRole[0]
            }
        }

        initApi()
        run()
    }

    private fun run() {

        login.setOnClickListener {

            //startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            onBackPressed()
        }

        register.setOnClickListener {
            when {
                /*
                Проверяем что поле с почтой заполнено
                 */
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                /*
                Проверяем что поле с паролем заполнено
                */
                TextUtils.isEmpty(editTextTextPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(editTextTextFirstName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter first name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(editTextTextLastName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter last name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(editTextTextPatronymic.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter patronymic.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(editTextTextDescription.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter description.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val userName: String = editTextTextEmailAddress.text.toString().trim { it <= ' ' }
                    val password: String = editTextTextPassword.text.toString().trim { it <= ' ' }
                    val firstName: String = editTextTextFirstName.text.toString().trim { it <= ' ' }
                    val lastName: String = editTextTextLastName.text.toString().trim { it <= ' ' }
                    val patronymic: String = editTextTextPatronymic.text.toString().trim { it <= ' ' }
                    val description: String = editTextTextDescription.text.toString().trim { it <= ' ' }
                    viewModel.register(
                        UserRegistration(
                            userRole,
                            firstName,
                            lastName,
                            patronymic,
                            userName,
                            password,
                            specialization,
                             1.0,
                            description,
                            ArrayList(),
                        )
                    )
                    viewModel.registerResponse.observe(this, { response ->
                        if (response.isSuccessful) {
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        } else {
                            throw RuntimeException(response.toString())
                        }
                    })

                }
            }
        }
    }

    fun initApi() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
}