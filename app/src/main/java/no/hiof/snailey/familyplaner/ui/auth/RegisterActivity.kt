package no.hiof.snailey.familyplaner.ui.auth

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

    private var signUpMail: EditText? = null
    private var signUpPass:EditText? = null
    private var textName:EditText? = null
    private var textFamily:EditText? = null
    private var signUpButton: Button? = null
    private var cancelButton: Button? = null
    private var auth: FirebaseAuth? = null

    private val dbUser = FirebaseDatabase.getInstance().getReference(NODE_USER)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        signUpMail = findViewById(R.id.text_email)
        signUpPass = findViewById(R.id.text_password)
        textName = findViewById(R.id.text_name)
        textFamily = findViewById(R.id.edit_text_family)
        auth = FirebaseAuth.getInstance()
        signUpButton = findViewById<Button>(R.id.btn_register)
        cancelButton = findViewById<Button>(R.id.btn_cancel)

        signUpButton!!.setOnClickListener(View.OnClickListener {
            val email = signUpMail!!.text.toString()
            val pass: String = signUpPass!!.text.toString()
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

       cancelButton!!.setOnClickListener {
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
        }
    }

    private fun registerUserInfo() {
        val name = textName?.text.toString().trim()
        val email = signUpMail?.text.toString().trim()
        val family = textFamily?.text.toString().trim()
        val picture = "https://firebasestorage.googleapis.com/v0/b/familyplaner-3842b.appspot.com/o/profile.jpg?alt=media&token=91495986-b255-46b0-be3a-43cb0c115223"

        val user = auth?.currentUser
        val Uid = user!!.uid

        val newUser = User(Uid, name, email, family, picture)

        dbUser.child(Uid).setValue(newUser)
    }
}

