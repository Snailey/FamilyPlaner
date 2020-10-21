package no.hiof.snailey.familyplaner.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import no.hiof.snailey.familyplaner.R


class LogInActivity : AppCompatActivity() {

    private var SignInMail: EditText? = null
    private var SignInPass:EditText? = null
    private var SignInButton: Button? = null
    private var RegisterButton: Button? = null
    private var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance();

        SignInMail = findViewById(R.id.text_email);
        SignInPass = findViewById(R.id.text_password);
        SignInButton = findViewById<Button>(R.id.btn_register)
        RegisterButton = findViewById<Button>(R.id.btn_cancel)

        SignInButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val email = SignInMail!!.text.toString()
                val password = SignInPass!!.text.toString()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                        applicationContext,
                        "Enter your mail address",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "Enter your password", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                //authenticate user
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LogInActivity,
                        OnCompleteListener<AuthResult?> { task ->
                            if (!task.isSuccessful) {
                                // there was an error
                                if (password.length < 8) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Password must be more than 8 digit",
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

        RegisterButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                NavigateSignUp()
            }
        })
    }

    fun NavigateSignUp() {
        val inent = Intent(this, RegisterActivity::class.java)
        startActivity(inent)
    }

    /*fun NavigateForgetMyPassword(v: View?) {
        val inent = Intent(this, ResetPasswordActivity::class.java)
        startActivity(inent)
    }*/
}