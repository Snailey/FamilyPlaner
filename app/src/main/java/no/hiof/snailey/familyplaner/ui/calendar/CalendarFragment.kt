package no.hiof.snailey.familyplaner.ui.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*
import no.hiof.snailey.familyplaner.Global
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.*
import java.lang.Exception

import java.util.*


class CalendarFragment : Fragment() {

    var adapter: RecyclerView.Adapter<*>? = null
    var eventsList = ArrayList<Event>()

    var addEventButton: FloatingActionButton? = null

    var firebaseFirestore: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        addEventButton = view.findViewById<FloatingActionButton>(R.id.add_event_fab)

        addEventButton!!.setOnClickListener {
            val intent = Intent(activity, AddEventActivity::class.java)

            startActivity(intent)
        }

        val eventsRecyclerView: RecyclerView =
            view.findViewById<RecyclerView>(R.id.event_list_recycler_view)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        adapter = EventsAdapter(eventsList)

        eventsRecyclerView.adapter = adapter
        eventsRecyclerView.layoutManager = layoutManager

        val calendarView: CalendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            getEventsFromFirebaseDatabase(dayOfMonth, month + 1, year)
        }
    }

    private fun getEventsFromFirebaseDatabase(day: Int, month: Int, year: Int) {

        eventsList.clear()
        val Year = "" + year
        val date = "$day-$month"

        firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore!!.collection(Global.setFamilyName).document(Year).collection(date)
            .get().addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val split = document.id.split(":".toRegex()).toTypedArray()

                        if (document.data.isEmpty()) {
                            return@OnCompleteListener
                        }
                        val event = Event(
                            document.data[EVENT_NAME].toString(),
                            document.data[EVENT_LOCATION].toString(),
                            day,
                            month,
                            year,
                            split[0].toInt(),
                            split[1].toInt()
                        )
                        eventsList.add(event)
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }).addOnFailureListener { }
    }
}

