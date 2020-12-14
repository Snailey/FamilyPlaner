package no.hiof.snailey.familyplaner.ui.shopping

import android.view.View
import no.hiof.snailey.familyplaner.data.Shopping


interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, shopping: Shopping)
}