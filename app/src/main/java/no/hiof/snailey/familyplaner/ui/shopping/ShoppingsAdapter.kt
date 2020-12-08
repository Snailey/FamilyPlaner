package no.hiof.snailey.familyplaner.ui.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_shopping.view.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Shopping

class ShoppingsAdapter : RecyclerView.Adapter<ShoppingsAdapter.ShoppingViewModel>() {

    private var shoppings = mutableListOf<Shopping>()
    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShoppingViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_shopping, parent, false)
    )

    override fun getItemCount() = shoppings.size

    override fun onBindViewHolder(holder: ShoppingViewModel, position: Int) {
        holder.view.text_view_location.text = shoppings[position].name
        holder.view.text_view_title.text = shoppings[position].number.toString()
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, shoppings[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, shoppings[position])
        }
        holder.view.add.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, shoppings[position])
        }
        holder.view.subtract.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, shoppings[position])
        }
    }

    fun setShoppings(Shoppings: List<Shopping>) {
        this.shoppings = Shoppings as MutableList<Shopping>
        notifyDataSetChanged()
    }

    fun addShopping(shopping: Shopping) {
        if (!shoppings.contains(shopping)) {
            shoppings.add(shopping)
        } else {
            val index = shoppings.indexOf(shopping)
            if (shopping.isDeleted) {
                shoppings.removeAt(index)
            } else {
                shoppings[index] = shopping
            }
        }
        notifyDataSetChanged()
    }

    class ShoppingViewModel(val view: View) : RecyclerView.ViewHolder(view)
}