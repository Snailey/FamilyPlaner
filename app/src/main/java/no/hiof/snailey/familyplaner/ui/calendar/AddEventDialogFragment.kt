package no.hiof.snailey.familyplaner.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.dialog_fragment_add_event.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Event

class AddEventDialogFragment : DialogFragment() {

    private lateinit var viewModel: EventsViewModel
    val hour = 0
    val minute = 0
    val day = 0
    val month = 0
    val year = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
        return inflater.inflate(R.layout.dialog_fragment_add_event, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.event_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

        button_add.setOnClickListener {
            val title = set_text_title.text.toString().trim()
            if (title.isEmpty()) {
                set_input_layout_location.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            val location = set_text_location.text.toString().trim()
            if (title.isEmpty()) {
                set_input_layout_location.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            val event = Event()
            event.title = title
            event.location = location
            event.hour = hour
            event.minute = minute
            event.day = day
            event.month = month
            event.year = year
            viewModel.addEvent(event)
        }
    }
}