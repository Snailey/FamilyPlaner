package no.hiof.snailey.familyplaner.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.Exclude

data class User(

   // @get:Exclude
    var Uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var family: String? = null,
    var picture: String? = null,

    )
