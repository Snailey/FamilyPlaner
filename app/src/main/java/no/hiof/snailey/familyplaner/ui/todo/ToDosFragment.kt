package no.hiof.snailey.familyplaner.ui.todo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_todos.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.EventList

class ToDosFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var viewModel: ToDosViewModel
    private val adapter = ToDosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ToDosViewModel::class.java)
        return inflater.inflate(R.layout.fragment_todos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_todos.adapter = adapter

        viewModel.fetchToDos()
        viewModel.getRealtimeUpdates()

        viewModel.todos.observe(viewLifecycleOwner, Observer {
            adapter.setTodos(it)
        })

        viewModel.toDo.observe(viewLifecycleOwner, Observer {
            adapter.addTodos(it)
        })

        button_add.setOnClickListener {
            AddToDoDialogFragment()
                .show(childFragmentManager, "")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, toDo: EventList) {
        when (view.id) {
            R.id.button_edit -> {
                EditToDoDialogFragment(toDo).show(childFragmentManager, "")
            }
            R.id.button_delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.delete_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        viewModel.deleteTodo(toDo)
                    }
                }.create().show()
            }
        }
    }
}
