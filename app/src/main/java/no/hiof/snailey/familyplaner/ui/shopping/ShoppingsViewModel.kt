package no.hiof.snailey.familyplaner.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import no.hiof.snailey.familyplaner.data.NODE_SHOPPING
import no.hiof.snailey.familyplaner.data.Shopping
import java.lang.Exception

class ShoppingsViewModel : ViewModel() {

    private val dbShopping = FirebaseDatabase.getInstance().getReference("Martinsen").child(NODE_SHOPPING)

    private val _shoppings = MutableLiveData<List<Shopping>>()
    val shoppings: LiveData<List<Shopping>>
        get() = _shoppings

    private val _shopping = MutableLiveData<Shopping>()
    val shopping: LiveData<Shopping>
        get() = _shopping

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addShopping(shopping: Shopping) {
        shopping.id = dbShopping.push().key
        dbShopping.child(shopping.id!!).setValue(shopping)
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
            val shopping = snapshot.getValue(Shopping::class.java)
            shopping?.id = snapshot.key
            _shopping.value = shopping
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val shopping = snapshot.getValue(Shopping::class.java)
            shopping?.id = snapshot.key
            _shopping.value = shopping
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val shopping = snapshot.getValue(Shopping::class.java)
            shopping?.id = snapshot.key
            shopping?.isDeleted = true
            _shopping.value = shopping
        }
    }

    fun getRealtimeUpdates() {
        dbShopping.addChildEventListener(childEventListener)
    }

    fun fetchShoppings() {
        dbShopping.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val shoppings = mutableListOf<Shopping>()
                    for (shoppingSnapshot in snapshot.children) {
                        val shopping = shoppingSnapshot.getValue(Shopping::class.java)
                        shopping?.id = shoppingSnapshot.key
                        shopping?.let { shoppings.add(it) }
                    }
                    _shoppings.value = shoppings
                }
            }
        })
    }

    fun updateShopping(shopping: Shopping) {
        dbShopping.child(shopping.id!!).setValue(shopping)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun deleteShopping(shopping: Shopping) {
        dbShopping.child(shopping.id!!).setValue(null)
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
        dbShopping.removeEventListener(childEventListener)
    }
}