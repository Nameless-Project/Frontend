package com.hse_project.hse_slaves.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hse_project.hse_slaves.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

                    val email: String = editTextTextEmailAddress.text.toString().trim { it <= ' ' }
                    val password: String = editTextTextPassword.text.toString().trim { it <= ' ' }

                    /*
                    Создаем нового юзера
                     */
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                                /*
                                Если вход прошел успешно
                                 */
                                if (task.isSuccessful) {

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You are logged in successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    /*
                                    Создаем событие в которе запоминаем юзера и почту
                                    но это не точно...
                                     */
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra(
                                        "user_id",
                                        FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                }


            }
        }
    }
}