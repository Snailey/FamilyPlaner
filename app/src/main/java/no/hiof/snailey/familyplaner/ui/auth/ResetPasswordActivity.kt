package no.hiof.snailey.familyplaner.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*
import no.hiof.snailey.familyplaner.R


class ResetPasswordActivity : AppCompatActivity() {
    private var resetPassword: Button? = null
    private var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        resetPassword = findViewById<Button>(R.id.btn_reset_password)
        cancelButton = findViewById<Button>(R.id.btn_cancel)

        resetPassword!!.setOnClickListener {
            val email = text_email.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Skriv inn epost", Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(applicationContext, "Skriv inn gyldig epost", Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Sjekk etter epost", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, task.exception?.message!!, Toast.LENGTH_SHORT)
                    }
                }
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)

        }
        cancelButton!!.setOnClickListener {
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}
