package no.hiof.snailey.familyplaner.ui.todo

import android.view.View
import no.hiof.snailey.familyplaner.data.ToDo


interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, toDo: ToDo)
}