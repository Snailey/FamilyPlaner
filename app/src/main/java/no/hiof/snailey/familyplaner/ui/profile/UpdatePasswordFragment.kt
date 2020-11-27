package no.hiof.snailey.familyplaner.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.fragment_update_password.*
import no.hiof.snailey.familyplaner.R


class UpdatePasswordFragment : Fragment() {

    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutPassword.visibility = View.VISIBLE
        layoutUpdatePassword.visibility = View.GONE

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
                                layoutUpdatePassword.visibility = View.VISIBLE
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                edit_text_password.error = "Passordet er feil"
                                edit_text_password.requestFocus()
                            }
                        }
                    }
            }

        }

        button_delete_user.setOnClickListener {

            val password = edit_text_new_password.text.toString().trim()

            if(password.isEmpty() || password.length < 6){
                edit_text_new_password.error = "Passordet må inneholde minst 6 tegn"
                edit_text_new_password.requestFocus()
                return@setOnClickListener
            }

            if(password != edit_text_new_password_confirm.text.toString().trim()){
                edit_text_new_password_confirm.error = "Passordene er ikke like"
                edit_text_new_password_confirm.requestFocus()
                return@setOnClickListener
            }

            currentUser?.let{ user ->
                user.updatePassword(password)
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
