package no.hiof.snailey.familyplaner.ui.profile

import android.Manifest
import android.R.attr.thumb
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.nav_header_main.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.NODE_USER
import no.hiof.snailey.familyplaner.data.User
import java.io.File


class ProfileFragment : Fragment() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var userName: TextView? = null
    private var userFamily: TextView? = null
    private var saveBtn: Button? = null
    private var emailTxt: TextView? = null
    private var passwordTxt: TextView? = null
    private var deleteuserTxt: TextView? = null
    var selectedPhotoUri: Uri? = null
    lateinit var email: String
    lateinit var picture: String

    private val currentUser = FirebaseAuth.getInstance().currentUser
    var file: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child(NODE_USER)
        mAuth = FirebaseAuth.getInstance()

        userName = view.findViewById(R.id.edit_text_name)
        userFamily = view.findViewById(R.id.edit_text_family)
        saveBtn = view.findViewById(R.id.button_save)
        emailTxt = view.findViewById(R.id.text_email)
        passwordTxt = view.findViewById(R.id.text_password)
        deleteuserTxt = view.findViewById(R.id.text_deleteuser)

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName!!.text = snapshot.child("name").value as String
                userFamily!!.text = snapshot.child("family").value as String
                email = snapshot.child("email").value as String
                picture = snapshot.child("picture").value as String
                //val media = snapshot.child("picture").value as String
                if (picture != null) {
                    Glide.with(this@ProfileFragment)
                        .load(picture)
                        .into(image_view)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        image_view!!.setOnClickListener{
            //val intent = Intent(Intent.ACTION_PICK)
            //intent.type = "image/*"
            //startActivityForResult(intent, 0)

            takePicture()
        }

        saveBtn!!.setOnClickListener {
            updateUserToFirebaseDatabase(picture)
        }


        emailTxt!!.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, UpdateEmailFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        passwordTxt!!.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, UpdatePasswordFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        deleteuserTxt!!.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment, DeleteUserFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        // Declare the switch from the layout file
        val btn = view.findViewById<Switch>(R.id.switch1)

        // set the switch to listen on checked change
        btn.setOnCheckedChangeListener { _, isChecked ->

            // if the button is checked, i.e., towards the right or enabled
            // enable dark mode, change the text to disable dark mode
            // else keep the switch text to enable dark mode
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.text = "Disable dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn.text = "Enable dark mode"
            }
        }
}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)  {
            selectedPhotoUri = data.data

            val resolver = requireActivity().contentResolver
            val bitmap = MediaStore.Images.Media.getBitmap(resolver, selectedPhotoUri)

            image_view.setImageBitmap(bitmap)
            uploadImageToFirebaseStorage()

        } else if (requestCode == 102  && resultCode == Activity.RESULT_OK && data != null) {

            //GET URI


            

            val bitmap = data.extras?.get("data") as Bitmap



            //uploadImageToFirebaseStorage()
            image_view.setImageBitmap(bitmap)

        }
    }

    private fun uploadImageToFirebaseStorage() {
        val filename = mAuth!!.currentUser!!.uid.toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    updateUserToFirebaseDatabase(it.toString())
                }
                println("Bilde lastet opp")
            }
            .addOnFailureListener {
                println("ERROR - Bilde IKKE lastet opp")
            }

    }

    private fun updateUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")

        val user = User(
            uid,
            userName?.text.toString(),
            email,
            userFamily?.text.toString(),
            profileImageUrl
        )
        ref.setValue(user)
    }

    private fun takePicture() {
        askCameraPermission()
    }

    private fun askCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                101
            )
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 102)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Du må gi appen tilgang til kamera for å ta bilde",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
