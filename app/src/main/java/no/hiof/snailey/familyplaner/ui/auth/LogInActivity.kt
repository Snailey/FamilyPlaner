package no.hiof.snailey.familyplaner.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.ui.MainActivity


class LogInActivity : AppCompatActivity() {

    private var signInMail: EditText? = null
    private var signInPass: EditText? = null
    private var signInButton: Button? = null
    private var registerButton: Button? = null
    private var forgotPassword: TextView? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        auth!!.addAuthStateListener(authStateListener)

        signInMail = findViewById(R.id.text_email)
        signInPass = findViewById(R.id.text_password)
        signInButton = findViewById<Button>(R.id.btn_register)
        registerButton = findViewById<Button>(R.id.btn_cancel)
        forgotPassword = findViewById<TextView>(R.id.text_forgot)

        signInButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email = signInMail!!.text.toString()
                val password = signInPass!!.text.toString()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                        applicationContext,
                        "Skriv e-post",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "Skriv passord", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LogInActivity,
                        OnCompleteListener<AuthResult?> { task ->
                            if (!task.isSuccessful) {
                                if (password.length < 6) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Passord må ha minimum 6 tegn",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
            }
        })

        registerButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                navigateSignUp()
            }
        })

        forgotPassword!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                navigateForgotPassword()
            }
        })
    }

    private var authStateListener =
        AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser != null) {
                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    fun navigateSignUp() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun navigateForgotPassword() {
        val intent = Intent(this, ResetPasswordActivity::class.java)
        startActivity(intent)
    }
}