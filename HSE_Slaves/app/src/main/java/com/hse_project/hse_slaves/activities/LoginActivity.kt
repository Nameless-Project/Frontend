package com.hse_project.hse_slaves.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initApi()
        run()
    }

    private fun run() {
        register.setOnClickListener {

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        login.setOnClickListener {
            when {
                /*
                Проверяем что поле с почтой заполнено
                 */
                TextUtils.isEmpty(editTextTextEmailAddress.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                /*
                Проверяем что поле с паролем заполнено
                */
                TextUtils.isEmpty(editTextTextPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val username: String = editTextTextEmailAddress.text.toString().trim { it <= ' ' }
                    val password: String = editTextTextPassword.text.toString().trim { it <= ' ' }

                    /*
                    Создаем нового юзера
                     */

                    viewModel.getToken(username, password)
                    viewModel.tokenResponse.observe(this, { response ->
                        if (response.isSuccessful) {
                            viewModel.setNewToken(response.headers()["Authorization"].toString())
                            Log.i("Token: ", viewModel.token)
                            //TODO авторизация прошла успешна запускаем дальше приложение
                        } else {
                            throw RuntimeException(response.toString())
                        }
                    }
                    )

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