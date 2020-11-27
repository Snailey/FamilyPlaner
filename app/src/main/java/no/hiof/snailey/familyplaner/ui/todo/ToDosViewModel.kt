package no.hiof.snailey.familyplaner.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.nav_header_main.*
import no.hiof.snailey.familyplaner.data.EventList
import no.hiof.snailey.familyplaner.data.NODE_TODOS
import no.hiof.snailey.familyplaner.data.NODE_USER
import java.lang.Exception

class ToDosViewModel : ViewModel() {

    private val dbToDos = FirebaseDatabase.getInstance().getReference("Martinsen").child(NODE_TODOS)

    private val _todos = MutableLiveData<List<EventList>>()
    val todos: LiveData<List<EventList>>
        get() = _todos

    private val _todo = MutableLiveData<EventList>()
    val toDo: LiveData<EventList>
        get() = _todo

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addTodo(toDo: EventList) {
        toDo.id = dbToDos?.push()?.key
        dbToDos?.child(toDo.id!!)?.setValue(toDo)
            ?.addOnCompleteListener {
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
            val todo = snapshot.getValue(EventList::class.java)
            todo?.id = snapshot.key
            _todo.value = todo
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val todo = snapshot.getValue(EventList::class.java)
            todo?.id = snapshot.key
            _todo.value = todo
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val todo = snapshot.getValue(EventList::class.java)
            todo?.id = snapshot.key
            todo?.isDeleted = true
            _todo.value = todo
        }
    }

    fun getRealtimeUpdates() {
        dbToDos?.addChildEventListener(childEventListener)
    }

    fun fetchToDos() {
        dbToDos?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val todos = mutableListOf<EventList>()
                    for (todoSnapshot in snapshot.children) {
                        val todo = todoSnapshot.getValue(EventList::class.java)
                        todo?.id = todoSnapshot.key
                        todo?.let { todos.add(it) }
                    }
                    _todos.value = todos
                }
            }
        })
    }

    fun updateToDo(toDo: EventList) {
        dbToDos?.child(toDo.id!!)?.setValue(toDo)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun deleteTodo(toDo: EventList) {
        dbToDos?.child(toDo.id!!)?.setValue(null)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }


    override fun onCleared() {
        super.onCleared()
        dbToDos?.removeEventListener(childEventListener)
    }
}
