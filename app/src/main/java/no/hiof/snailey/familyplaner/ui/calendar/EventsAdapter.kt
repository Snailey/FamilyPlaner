package no.hiof.snailey.familyplaner.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_event.view.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Event

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventViewModel>() {

    private var events = mutableListOf<Event>()
    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_event, parent, false)
    )

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewModel, position: Int) {
        holder.view.text_view_title.text = events[position].title
        holder.view.text_view_location.text = events[position].location
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, events[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, events[position])
        }
    }

    fun setEvents(Events: List<Event>) {
        this.events = Events as MutableList<Event>
        notifyDataSetChanged()
    }

    fun addEvent(event: Event) {
        if (!events.contains(event)) {
            events.add(event)
        } else {
            val index = events.indexOf(event)
            if (event.isDeleted) {
                events.removeAt(index)
            } else {
                events[index] = event
            }
        }
        notifyDataSetChanged()
    }

    class EventViewModel(val view: View) : RecyclerView.ViewHolder(view)
}