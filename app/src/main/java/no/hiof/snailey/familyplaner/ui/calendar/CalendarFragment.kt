package no.hiof.snailey.familyplaner.ui.calendar

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.Main
import no.hiof.snailey.familyplaner.R

import no.hiof.snailey.familyplaner.ui.shopping.EditDialogFragment
import java.time.LocalDateTime
import java.time.Year
import java.util.*


class CalendarFragment : Fragment() {

    val EVENT_NAME = "Event_title"
    val EVENT_LOCATION = "Location"
    val TAG = "TAG"

    var adapter: RecyclerView.Adapter<*>? = null
    var eventsList = ArrayList<Event>()

    var addEventButton: FloatingActionButton? = null

    var firebaseAuth: FirebaseAuth? = null
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
            println("Trykket på lag ny event")
            //EditDialogFragment(eventsLis
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

        /*
        //GET DATA
        super.onViewCreated(view, savedInstanceState)

        calendarView.datesIndicators =  createDataSet() //eventList //getEventformFirebase()

        calendarView.onDateClickListener = { date ->
            showDialogWithEventsForSpecificDate(date)
        }

        calendarView.onDateLongClickListener = { date ->
        }

        if (savedInstanceState == null) {
            calendarView.setupCalendar(selectionMode = CalendarView.SelectionMode.NONE)
        }

         */
    }



    /*
    fun createDataSet(): ArrayList<EventItem>{

        val eventList = ArrayList<EventItem>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot!!.exists()){
                    eventList.clear()
                    for (e in dataSnapshot.children){
                        val post = e.getValue(EventItem::class.java)
                        eventList.add(post!!)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        dbEvents.addValueEventListener(postListener)

        return eventList
    }


    private fun showDialogWithEventsForSpecificDate(date: CalendarDate) {

            val eventItems = calendarView.getDateIndicators(date)
                eventItems
                .filterIsInstance<EventItem>()
                .toTypedArray()

            if (eventItems.isNotEmpty()) {
                val adapter = EventDialogAdapter(requireContext(), eventItems as List<EventItem>)

                val builder = AlertDialog.Builder(requireContext())
                    .setTitle("$date")
                    .setAdapter(adapter, null)

                val dialog = builder.create()
                dialog.show()
            }
    }


        /*
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                menu.clear()
                dataSnapshot.children.mapNotNullTo(menu) { it.getValue<EventItem>(EventItem::class.java) }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        dbEvents.child("events").addListenerForSingleValueEvent(menuListener)

        val context = requireContext()
        val calendar = Calendar.getInstance()


        val eventItems = mutableListOf<EventItem>()
        eventItems.plusAssign(EventItem("1","Test1", CalendarDate(calendar.time), context.getColor(R.color.event_1_color) ))
        eventItems.plusAssign(EventItem("2","Test2", CalendarDate(calendar.time), context.getColor(R.color.event_2_color) ))
        eventItems.plusAssign(EventItem("3","Test3", CalendarDate(calendar.time), context.getColor(R.color.event_3_color) ))
        eventItems.plusAssign(EventItem("4","Test4", CalendarDate(calendar.time), context.getColor(R.color.event_4_color) ))
        eventItems.plusAssign(EventItem("5","Test5", CalendarDate(calendar.time), context.getColor(R.color.event_5_color) ))
        eventItems.plusAssign(EventItem("6","Test6", CalendarDate(calendar.time), context.getColor(R.color.event_1_color) ))
        eventItems.plusAssign(EventItem("7","Test7", CalendarDate(calendar.time), context.getColor(R.color.event_2_color) ))
        eventItems.plusAssign(EventItem("8","Test8", CalendarDate(calendar.time), context.getColor(R.color.event_3_color) ))
        eventItems.plusAssign(EventItem("9","Test9", CalendarDate(calendar.time), context.getColor(R.color.event_4_color) ))
        eventItems.plusAssign(EventItem("10","Test10", CalendarDate(calendar.time), context.getColor(R.color.event_5_color) ))

        return eventItems

        return eventList
    }
*/
*/

    fun onRecyclerViewItemClicked(view: View, event: Event) {
        when (view.id) {
            R.id.event_list_recycler_view -> {
                println("Trykket på event")
                //EditDialogFragment(eventsList).show(childFragmentManager, "")
            }
        }
    }

    private fun getEventsFromFirebaseDatabase(day: Int, month: Int, year: Int) {

        // Clear our ArrayList
        eventsList.clear()
        val Year = "" + year
        val date = "$day-$month"
        // Create a connection to our Firebase-Firestore Database
        firebaseFirestore = FirebaseFirestore.getInstance()

        // Execute a query to fetch data
        firebaseFirestore!!.collection("Events").document(Year).collection(date)
            .get().addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val split = document.id.split(":".toRegex()).toTypedArray()
                        // Log.d(TAG, split[0] + " => " + split[1] + " => " + document.getData());

                        // Check if there are any rows present
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
                    // Tell the adapter that the ArrayList is updated
                    // This will make it refresh the UI
                    adapter!!.notifyDataSetChanged()
                } else {
                    //Log.d(MainActivity.TAG, "Error getting documents: ", task.exception)
                }
            }).addOnFailureListener { }
    }
}

