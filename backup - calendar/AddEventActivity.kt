package no.hiof.snailey.familyplaner.ui.calendar

import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.*
import java.lang.Exception
import java.util.*

class AddEventActivity : AppCompatActivity(), OnDateSetListener, OnTimeSetListener {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0
    var firebaseFirestore: FirebaseFirestore? = null
    private val dbShopping = FirebaseDatabase.getInstance().getReference("Martinsen").child(NODE_CALENDAR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Attaches the layout design file to this activity
        setContentView(R.layout.activity_add_event)

        // This name appears on the Action Bar (at the top) when this Activity is launched
        title = "New event"

        // Get the ImageButton and add a click listener
        // This lets us know when the button is clicked
        val showDatePicker = findViewById<ImageButton>(R.id.show_datepicker_button)
        showDatePicker.setOnClickListener { // Launch our DatePicker when the button is clicked
            val customDatePicker = CustomDatePicker()
            customDatePicker.show(supportFragmentManager, "PICK DATE")
        }

        // Similar to above
        val showTimePicker = findViewById<ImageButton>(R.id.show_timepicker_button)
        showTimePicker.setOnClickListener { // Launch Time Picker
            val customTimePicker = CustomTimePicker()
            customTimePicker.show(supportFragmentManager, "PICK TIME")
        }
        val saveButton = findViewById<Button>(R.id.new_event_save_button)
        saveButton.setOnClickListener { // Save our entered info in the local SQLite Database
            saveEventToFirebase()

            // Close this activity and return to the previous page
            finish()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        // This is a callback function, as in this gets called when the date is set

        // Format the date in whatever pattern we want
        val date = dayOfMonth.toString() + "/" + (month + 1) + "/" + year

        // Get the dateView which is an EditText
        val dateView = findViewById<EditText>(R.id.new_event_dateview)
        // And set our formatted date
        dateView.setText(date)

        // Also save the date to global variables so we can use it later
        day = dayOfMonth
        this.month = month + 1
        this.year = year
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // This is a callback function, similar to the one above

        // Format time as needed, and set this string to our timeView
        val time = "$hourOfDay:$minute"
        val timeView = findViewById<EditText>(R.id.new_event_timeview)
        timeView.setText(time)

        // Also save the time to global variables so we can use it later
        hour = hourOfDay
        this.minute = minute
    }

    private fun saveEventToFirebase() {
        // This function used the global variables day, month, year etc
        // And created an event entry in the database

        // Get the title and location of our event
        val eventTitleBox = findViewById<EditText>(R.id.new_event_title_edittext)
        val eventTitle = eventTitleBox.text.toString()
        val eventLoactionBox = findViewById<EditText>(R.id.new_event_location_edittext)
        val eventLocation = eventLoactionBox.text.toString()
        val Year = "" + year
        val date = day.toString() + "-" + month
        val time = hour.toString() + ":" + minute

        // Create a connection to our Firebase-Firestore Database
        firebaseFirestore = FirebaseFirestore.getInstance()
        val documentReference =
            firebaseFirestore!!.collection("Events").document(Year).collection(date).document(time)

        // Connent to FirebaseDatabase

        val event = Event(eventTitle, eventLocation, day, month, year, hour, minute)

            dbShopping.push().key
            dbShopping.child(Year).child(date).child(time).setValue(event)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _result.value = null
                    } else {
                        _result.value = it.exception
                    }
                }

        // Insert all event info in a Hashmap
        val user: MutableMap<String, String> = HashMap()
        user["Event_title"] = eventTitle
        user["Location"] = eventLocation
        documentReference.set(user)
            .addOnSuccessListener { Log.d(TAG, "onSuccess: Event is created") }
            .addOnFailureListener { e -> Log.d(TAG, "onFailure: $e") }
    }

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    companion object {
        const val TAG = "TAG"
    }
}