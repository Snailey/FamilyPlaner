package no.hiof.snailey.familyplaner.ui.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.dialog_fragment_add_shopping.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.Shopping

class AddShoppingDialogFragment : DialogFragment() {

    private lateinit var viewModel: ShoppingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ShoppingsViewModel::class.java)
        return inflater.inflate(R.layout.dialog_fragment_add_shopping, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.shopping_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

        button_add.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            val shopping = Shopping()
            shopping.name = name
            shopping.number = 1
            viewModel.addShopping(shopping)
        }
    }
}