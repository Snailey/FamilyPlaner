package no.hiof.snailey.familyplaner.ui.calendar

import android.view.View
import no.hiof.snailey.familyplaner.data.Event

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, event: Event)
}