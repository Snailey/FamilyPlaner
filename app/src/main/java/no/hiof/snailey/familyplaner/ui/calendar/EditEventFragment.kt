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
import kotlinx.android.synthetic.main.dialog_fragment_add_event.button_add
import kotlinx.android.synthetic.main.dialog_fragment_edit_event.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Event

class EditDialogFragment(
    private val event: Event
) : DialogFragment() {

    private lateinit var viewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
        return inflater.inflate(R.layout.dialog_fragment_edit_event, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edit_text_title.setText(event.title)
        edit_text_location.setText(event.location)

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
            val title = edit_text_title.text.toString().trim()
            if (title.isEmpty()) {
                edit_input_layout_title.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            val location = edit_text_location.text.toString().trim()
            if (title.isEmpty()) {
                edit_input_layout_location.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            event.title = title
            event.location = location
            viewModel.updateEvent(event)
        }
    }
}