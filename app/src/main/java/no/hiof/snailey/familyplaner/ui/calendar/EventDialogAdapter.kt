package no.hiof.snailey.familyplaner.ui.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.EventItem

class EventDialogAdapter(
    context: Context,
    events: Array<EventItem>
) : ArrayAdapter<EventItem>(context, 0, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var eventCard: View? = null

        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog_event, parent, false)

        val eventItem = getItem(position)

        if (eventItem != null) {

            eventCard = view.findViewById(R.id.EventCard);
            view.findViewById<View>(R.id.colorView).setBackgroundColor(eventItem.color)
            view.findViewById<TextView>(R.id.eventNameView).text = eventItem.name

            eventCard!!.setOnClickListener(View.OnClickListener {
                Toast.makeText(context,  "Du trykket på " + eventItem.name , Toast.LENGTH_SHORT).show()

            })

        }

        return view
    }

}

