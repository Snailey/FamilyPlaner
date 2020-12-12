package no.hiof.snailey.familyplaner.ui.calendar

import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.snailey.familyplaner.Global
import no.hiof.snailey.familyplaner.R
import java.lang.Exception
import java.util.*

class AddEventActivity : AppCompatActivity(), OnDateSetListener, OnTimeSetListener {
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0
    private var firebaseFirestore: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_event)

        title = "New event"

        val showDatePicker = findViewById<ImageButton>(R.id.show_datepicker_button)
        showDatePicker.setOnClickListener {
            val customDatePicker = CustomDatePicker()
            customDatePicker.show(supportFragmentManager, "PICK DATE")
        }

        val showTimePicker = findViewById<ImageButton>(R.id.show_timepicker_button)
        showTimePicker.setOnClickListener {
            val customTimePicker = CustomTimePicker()
            customTimePicker.show(supportFragmentManager, "PICK TIME")
        }
        val saveButton = findViewById<Button>(R.id.new_event_save_button)
        saveButton.setOnClickListener {
            saveEventToFirebase()

            finish()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {

        val date = dayOfMonth.toString() + "/" + (month + 1) + "/" + year

        val dateView = findViewById<EditText>(R.id.new_event_dateview)

        dateView.setText(date)

        day = dayOfMonth
        this.month = month + 1
        this.year = year
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        val time = "$hourOfDay:$minute"
        val timeView = findViewById<EditText>(R.id.new_event_timeview)
        timeView.setText(time)

        hour = hourOfDay
        this.minute = minute
    }

    private fun saveEventToFirebase() {

        val eventTitleBox = findViewById<EditText>(R.id.new_event_title_edittext)
        val eventTitle = eventTitleBox.text.toString()
        val eventLoactionBox = findViewById<EditText>(R.id.new_event_location_edittext)
        val eventLocation = eventLoactionBox.text.toString()

        val Year = "" + year
        val date = day.toString() + "-" + month
        val time = hour.toString() + ":" + minute

        firebaseFirestore = FirebaseFirestore.getInstance()
        val documentReference =
            firebaseFirestore!!.collection(Global.setFamilyName).document(Year).collection(date).document(time)

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