package no.hiof.snailey.familyplaner.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import no.hiof.snailey.familyplaner.data.Event
import no.hiof.snailey.familyplaner.data.NODE_CALENDAR
import java.lang.Exception

class EventsViewModel : ViewModel() {

    private val dbEvent = FirebaseDatabase.getInstance().getReference("Martinsen")

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
        get() = _events

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addEvent(event: Event) {
        event.id = dbEvent.push().key
        dbEvent.child(event.id!!).setValue(event)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val event = snapshot.getValue(Event::class.java)
            event?.id = snapshot.key
            _event.value = event
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val event = snapshot.getValue(Event::class.java)
            event?.id = snapshot.key
            _event.value = event
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val event = snapshot.getValue(Event::class.java)
            event?.id = snapshot.key
            event?.isDeleted = true
            _event.value = event
        }
    }

    fun getRealtimeUpdates() {
        dbEvent.addChildEventListener(childEventListener)
    }

    fun fetchEvents() {
        dbEvent.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val events = mutableListOf<Event>()
                    for (eventSnapshot in snapshot.children) {
                        val event = eventSnapshot.getValue(Event::class.java)
                        event?.id = eventSnapshot.key
                        event?.let { events.add(it) }
                    }
                    _events.value = events
                }
            }
        })
    }

    fun updateEvent(event: Event) {
        dbEvent.child(event.id!!).setValue(event)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun deleteEvent(event: Event) {
        dbEvent.child(event.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        dbEvent.removeEventListener(childEventListener)
    }
}