package no.hiof.snailey.familyplaner.ui.shopping

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_shopping.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Shopping

class ShoppingsFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var viewModel: ShoppingsViewModel
    private val adapter = ShoppingsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ShoppingsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_shopping.adapter = adapter

        //viewModel.fetchFilteredShopping(6)
        viewModel.fetchShoppings()
        viewModel.getRealtimeUpdates()

        viewModel.shoppings.observe(viewLifecycleOwner, Observer {
            adapter.setShoppings(it)
        })

        viewModel.shopping.observe(viewLifecycleOwner, Observer {
            adapter.addShopping(it)
        })

        button_add.setOnClickListener {
            AddShoppingDialogFragment()
                .show(childFragmentManager, "")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, shopping: Shopping) {
        when (view.id) {
            R.id.button_edit -> {
                EditDialogFragment(shopping).show(childFragmentManager, "")
            }
            R.id.button_delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.delete_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        viewModel.deleteShopping(shopping)
                    }
                }.create().show()
            }
            R.id.add -> {
                var number = shopping.number
                number = number?.plus(1)
                shopping.number = number
                viewModel.updateShopping(shopping)
            }
            R.id.subtract -> {
                var number = shopping.number
                number = number?.minus(1)
                shopping.number = number
                viewModel.updateShopping(shopping)

            }
        }
    }
}
