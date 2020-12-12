package no.hiof.snailey.familyplaner.ui.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Event
import no.hiof.snailey.familyplaner.ui.calendar.EventsAdapter.MyViewHolder
import java.util.*

class EventsAdapter(eventsList: ArrayList<*>) : RecyclerView.Adapter<MyViewHolder>() {
    private val eventsList: ArrayList<Event>
    var listener: RecyclerViewClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_event_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val event = eventsList[position]

        holder.titleView.text = event.title + "\t-\t" + event.location
        holder.dateView.text = event.day.toString() + "/" + event.month + "/" + event.year
        holder.timeView.text = event.hour.toString() + ":" + event.minute
        holder.itemView.setOnClickListener{
            Log.d("EVENT", "onBindViewHolder: du trykket på " + eventsList[position])
        }
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById(R.id.event_list_title)
        var dateView: TextView = itemView.findViewById(R.id.event_list_date)
        var timeView: TextView = itemView.findViewById(R.id.event_list_time)
    }

    init {
        this.eventsList = eventsList as ArrayList<Event>
    }

}