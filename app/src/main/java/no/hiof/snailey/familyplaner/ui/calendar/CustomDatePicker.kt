// Denne koden er "lånt" fra  et eksempel på GitHub
package no.hiof.snailey.familyplaner.ui.calendar

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*


class CustomDatePicker : DialogFragment() {
    // This class shows a DatePicker Dialog Box when instantiated
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Get Java's built-in calendar instance
        val calendar = Calendar.getInstance()

        // Get current day, month and year
        // This will be used to set as the default date in our DatePicker
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH]
        val year = calendar[Calendar.YEAR]

        // Context is like a base on which the Dialog Box is created
        val context: Context? = activity

        // This is the listener that we implement in our activity
        // This is how the DatePicker knows which function to call, when the user has pressed Ok after selecting the date
        val onDateSetListener = activity as OnDateSetListener?

        // Instantiate a new DatePickerDialog with all the parameters above, and return it
        return DatePickerDialog(requireContext(), onDateSetListener, year, month, dayOfMonth)
    }
}