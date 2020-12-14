package no.hiof.snailey.familyplaner.ui.profile

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.fragment_update_email.*
import no.hiof.snailey.familyplaner.R


class UpdateEmailFragment : Fragment() {

    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                                edit_text_password.error = "Passordet er feil"
                                edit_text_password.requestFocus()
                            }
                        }
                    }
            }
        }

        button_delete_user.setOnClickListener { view ->
            val email = edit_text_email.text.toString().trim()

            if (email.isEmpty()) {
                edit_text_email.error = "Skriv inn Epost"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edit_text_email.error = "Benytt gyldig epost"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            currentUser?.let { user ->
                user.updateEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            transaction?.replace(R.id.fragment, ProfileFragment())
                            transaction?.disallowAddToBackStack()
                            transaction?.commit()
                        }
                    }
            }
        }
    }
}