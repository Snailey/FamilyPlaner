package no.hiof.snailey.familyplaner.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_update_email.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.ui.auth.LogInActivity
import java.util.concurrent.TimeUnit


class DeleteUserFragment : Fragment() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("user")

        layoutPassword.visibility = View.VISIBLE
        layoutUpdateEmail.visibility = View.GONE


        button_authenticate.setOnClickListener {

            val password = edit_text_password.text.toString().trim()

            if (password.isEmpty()) {
                edit_text_password.error = "Skriv inn passord"
                edit_text_password.requestFocus()
                return@setOnClickListener
            }


            currentUser?.let { user ->
                val credential = EmailAuthProvider.getCredential(user.email!!, password)
                user.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                layoutPassword.visibility = View.GONE
                                layoutUpdateEmail.visibility = View.VISIBLE
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                edit_text_password.error = "Feil passord"
                                edit_text_password.requestFocus()
                            }
                        }
                    }
            }

        }

        button_delete_user.setOnClickListener { view ->

            // reltime database
            mDatabaseReference?.child(currentUser!!.uid)?.removeValue()

            //auth
            FirebaseAuth.getInstance().currentUser!!.delete()

            //pauser 2 sec for at mobilen skal få med seg at bruker blir slettet/logget ut
            TimeUnit.SECONDS.sleep(2L)

            val intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}
