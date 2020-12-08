package no.hiof.snailey.familyplaner.ui.calendar

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_calendar.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Event

class CalendarFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var viewModel: EventsViewModel
    private val adapter = EventsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_event.adapter = adapter

        viewModel.fetchEvents()
        viewModel.getRealtimeUpdates()

        viewModel.events.observe(viewLifecycleOwner, Observer {
            adapter.setEvents(it)
        })

        viewModel.event.observe(viewLifecycleOwner, Observer {
            adapter.addEvent(it)
        })

        button_add.setOnClickListener {
            AddEventDialogFragment()
                .show(childFragmentManager, "")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, event: Event) {
        when (view.id) {
            R.id.button_edit -> {
                EditDialogFragment(event).show(childFragmentManager, "")
            }
            R.id.button_delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.delete_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        viewModel.deleteEvent(event)
                    }
                }.create().show()
            }
        }
    }
}

