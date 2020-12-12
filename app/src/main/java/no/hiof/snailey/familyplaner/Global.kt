package no.hiof.snailey.familyplaner

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class Global : Application() {

    companion object {
        lateinit var setFamilyName: String

       @JvmField
       var familyName = String
    }


    fun setFamileyName(name: String) {
        familyName = String
    }

}
