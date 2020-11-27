package no.hiof.snailey.familyplaner.ui.todo

import android.view.View
import no.hiof.snailey.familyplaner.data.EventList

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, toDo: EventList)
}