package com.hse_project.hse_slaves.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hse_project.hse_slaves.MainViewModel
import com.hse_project.hse_slaves.MainViewModelFactory
import com.hse_project.hse_slaves.R
import com.hse_project.hse_slaves.enums.Specialization
import com.hse_project.hse_slaves.enums.UserRole
import com.hse_project.hse_slaves.model.UserRegistration
import com.hse_project.hse_slaves.repository.Repository
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var userRole: UserRole = UserRole.USER
    private var specialization: Specialization = Specialization.ART


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
                else -> {
                    val userName: String = editTextTextEmailAddress.text.toString().trim { it <= ' ' }
                    val password: String = editTextTextPassword.text.toString().trim { it <= ' ' }
                    viewModel.register(
                        UserRegistration(
                            getUserRole(),
                            "firstName",
                            "lastName",
                            "userName",
                            userName,
                            password,
                            getSpecialization(),
                             1.0,
                            "description",
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

    private fun getUserRole(): String {
        return when (userRole) {
            UserRole.USER -> {
                "USER"
            }
            UserRole.ORGANIZER -> {
                "ORGANIZER"
            }
            else -> {
                "CREATOR"
            }
        }
    }

    private fun getSpecialization(): String {
        return when (specialization) {
            Specialization.ART -> {
                "ART"
            }
            Specialization.LITERATURE -> {
                "LITERATURE"
            }
            Specialization.MUSIC -> {
                "MUSIC"
            }
            else -> {
                "PHOTOGRAPHY"
            }
        }
    }

    fun initApi() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
}