package no.hiof.snailey.familyplaner.ui.calendar

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class CustomTimePicker : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Similar to CustomDatePicker, get Java's built-in Calendar
        // to know the current time
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        // Context is like a base on which the Dialog Box is created
        val context: Context? = activity

        // This is the listener that we implement in our activity
        // This is how the DatePicker knows which function to call, when the user has pressed Ok after selecting the date
        val onTimeSetListener = activity as OnTimeSetListener?

        // Instantiate a new TimePickerDialog with all the parameters above, and return it
        return TimePickerDialog(context, onTimeSetListener, hour, minute, true)
    }
}