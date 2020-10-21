package no.hiof.snailey.familyplaner.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import no.hiof.snailey.familyplaner.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val btn_register = findViewById<Button>(R.id.btn_login)
        val btn_cancel = findViewById<Button>(R.id.btn_register)

        //take user to MainActivity
        btn_register.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


        //take user to RegisterFragment
        btn_cancel.setOnClickListener {
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}