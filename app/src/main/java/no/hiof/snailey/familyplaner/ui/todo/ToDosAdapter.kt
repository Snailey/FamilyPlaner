package no.hiof.snailey.familyplaner.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_todo.view.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.ToDo


class ToDosAdapter : RecyclerView.Adapter<ToDosAdapter.ToDoViewModel>() {

    private var todos = mutableListOf<ToDo>()
    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ToDoViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_todo, parent, false)
    )

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: ToDoViewModel, position: Int) {
        holder.view.text_view_name.text = todos[position].name
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, todos[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, todos[position])
        }
    }

    fun setTodos(toDos: List<ToDo>) {
        this.todos = toDos as MutableList<ToDo>
        notifyDataSetChanged()
    }

    fun addTodos(toDo: ToDo) {
        if (!todos.contains(toDo)) {
            todos.add(toDo)
        } else {
            val index = todos.indexOf(toDo)
            if (toDo.isDeleted) {
                todos.removeAt(index)
            } else {
                todos[index] = toDo
            }
        }
        notifyDataSetChanged()
    }

    class ToDoViewModel(val view: View) : RecyclerView.ViewHolder(view)
}