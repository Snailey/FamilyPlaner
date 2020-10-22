package no.hiof.snailey.familyplaner.ui

import android.annotation.SuppressLint
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

class RegisterActivity : AppCompatActivity() {

    var SignUpMail: EditText? = null
    var SignUpPass:EditText? = null
    var text_name:EditText? = null
    var text_familycode:EditText? = null
    var SignUpButton: Button? = null
    var CancelButton: Button? = null
    private var auth: FirebaseAuth? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        SignUpMail = findViewById(R.id.text_email)
        SignUpPass = findViewById(R.id.text_password)
        text_name = findViewById(R.id.text_name)
        text_familycode = findViewById(R.id.text_familycode)
        auth = FirebaseAuth.getInstance()
        SignUpButton = findViewById<Button>(R.id.btn_register)
        CancelButton = findViewById<Button>(R.id.btn_cancel)

        SignUpButton!!.setOnClickListener(View.OnClickListener {
            val email = SignUpMail!!.text.toString()
            val pass: String = SignUpPass!!.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(
                    applicationContext,
                    "Please enter your E-mail address",
                    Toast.LENGTH_LONG
                ).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(applicationContext, "Please enter your Password", Toast.LENGTH_LONG)
                    .show()
            }
            if (pass.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter your Password", Toast.LENGTH_LONG)
                    .show()
            }
            if (pass.length < 6) {
                Toast.makeText(
                    applicationContext,
                    "Password must be more than 6 digit",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                auth!!.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this@RegisterActivity,
                        OnCompleteListener<AuthResult?> { task ->
                            if (!task.isSuccessful) {
                                Toast.makeText(this@RegisterActivity, "ERROR", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                startActivity(
                                    //Add user info to uid/user

                                    Intent(
                                        this@RegisterActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            }
                        })
            }
        })



        //take user to RegisterFragment
       CancelButton!!.setOnClickListener {
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
        }
    }
}