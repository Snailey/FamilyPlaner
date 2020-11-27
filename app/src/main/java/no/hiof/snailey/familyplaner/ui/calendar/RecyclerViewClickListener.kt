package no.hiof.snailey.familyplaner.ui.calendar

import android.view.View

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, event: Event)
}