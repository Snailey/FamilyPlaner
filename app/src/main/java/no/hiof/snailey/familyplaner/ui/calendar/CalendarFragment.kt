package no.hiof.snailey.familyplaner.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_calendar.*
import no.hiof.snailey.familyplaner.R
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import ru.cleverpumpkin.calendar.extension.getColorInt
import no.hiof.snailey.familyplaner.data.EventItem
import java.util.*

class CalendarFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView.datesIndicators = generateEventItems()

        calendarView.onDateClickListener = { date ->
            showDialogWithEventsForSpecificDate(date)
        }

        calendarView.onDateLongClickListener = { date ->
        }

        if (savedInstanceState == null) {
            calendarView.setupCalendar(selectionMode = CalendarView.SelectionMode.NONE)
        }
    }

    private fun showDialogWithEventsForSpecificDate(date: CalendarDate) {
            val eventItems = calendarView.getDateIndicators(date)
                .filterIsInstance<EventItem>()
                .toTypedArray()

            if (eventItems.isNotEmpty()) {
                val adapter = EventDialogAdapter(requireContext(), eventItems)

                val builder = AlertDialog.Builder(requireContext())
                    .setTitle("$date")
                    .setAdapter(adapter, null)

                val dialog = builder.create()
                dialog.show()
            }
    }


    private fun generateEventItems(): List<EventItem> {
        val context = requireContext()
        val calendar = Calendar.getInstance()

        val eventItems = mutableListOf<EventItem>()

        repeat(10) {
            eventItems += EventItem(
                name = "Event #1",
                date = CalendarDate(calendar.time),
                color = context.getColorInt(R.color.event_1_color)
            )

            eventItems += EventItem(
                name = "Event #2",
                date = CalendarDate(calendar.time),
                color = context.getColorInt(R.color.event_2_color)
            )

            eventItems += EventItem(
                name = "Event #3",
                date = CalendarDate(calendar.time),
                color = context.getColorInt(R.color.event_3_color)
            )

            eventItems += EventItem(
                name = "Event #4",
                date = CalendarDate(calendar.time),
                color = context.getColorInt(R.color.event_4_color)
            )

            eventItems += EventItem(
                name = "Event #5",
                date = CalendarDate(calendar.time),
                color = context.getColorInt(R.color.event_5_color)
            )

            calendar.add(Calendar.DAY_OF_MONTH, 5)
        }

        return eventItems
    }

}