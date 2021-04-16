package com.hse_project.hse_slaves.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hse_project.hse_slaves.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        /*
        Делаем текст домашней страницы актуальным для текущего юсера
        в будушем наверно будем передавать через intent json
         */
        user_id.text = "User ID :: $userId"
        email_id.text = "Email ID :: $emailId"

        logout.setOnClickListener {
            /*
            Выход из своего аккаунта
             */
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}