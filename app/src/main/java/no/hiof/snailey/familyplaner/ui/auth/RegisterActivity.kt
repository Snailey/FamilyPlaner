package no.hiof.snailey.familyplaner.ui.auth

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
import com.google.firebase.database.FirebaseDatabase
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.NODE_USER
import no.hiof.snailey.familyplaner.data.User
import no.hiof.snailey.familyplaner.ui.MainActivity


class RegisterActivity : AppCompatActivity() {

    private var SignUpMail: EditText? = null
    private var SignUpPass:EditText? = null
    private var text_name:EditText? = null
    private var text_family:EditText? = null
    private var SignUpButton: Button? = null
    private var CancelButton: Button? = null
    private var auth: FirebaseAuth? = null

    private val dbUser = FirebaseDatabase.getInstance().getReference(NODE_USER)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        SignUpMail = findViewById(R.id.text_email)
        SignUpPass = findViewById(R.id.text_password)
        text_name = findViewById(R.id.text_name)
        text_family = findViewById(R.id.edit_text_family)
        auth = FirebaseAuth.getInstance()
        SignUpButton = findViewById<Button>(R.id.btn_register)
        CancelButton = findViewById<Button>(R.id.btn_cancel)

        SignUpButton!!.setOnClickListener(View.OnClickListener {
            val email = SignUpMail!!.text.toString()
            val pass: String = SignUpPass!!.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(
                    applicationContext,
                    "Skriv inn e-post på nytt",
                    Toast.LENGTH_LONG
                ).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(applicationContext, "Skriv inn passord", Toast.LENGTH_LONG)
                    .show()
            }
            if (pass.isEmpty()) {
                Toast.makeText(applicationContext, "Skriv inn passord", Toast.LENGTH_LONG)
                    .show()
            }
            if (pass.length < 6) {
                Toast.makeText(
                    applicationContext,
                    "Passord m å bestå av mer en 6 tegn",
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
                                registerUserInfo()
                                startActivity(
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

       CancelButton!!.setOnClickListener {
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
        }
    }

    private fun registerUserInfo() {
        val name = text_name?.text.toString().trim()
        val email = SignUpMail?.text.toString().trim()
        val family = text_family?.text.toString().trim()
        val picture = "https://firebasestorage.googleapis.com/v0/b/familyplaner-3842b.appspot.com/o/profile.jpg?alt=media&token=91495986-b255-46b0-be3a-43cb0c115223"

        val user = auth?.currentUser
        val Uid = user!!.uid

        val newUser = User(Uid, name, email, family, picture)

        dbUser.child(Uid).setValue(newUser)
    }
}

