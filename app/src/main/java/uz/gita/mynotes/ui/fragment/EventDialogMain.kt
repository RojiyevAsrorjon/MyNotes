package uz.gita.mynotes.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.mynotes.R
import uz.gita.mynotes.databinding.LayoutDialog1Binding
import uz.gita.mynotes.databinding.LayoutDialogBinding

class EventDialogMain : BottomSheetDialogFragment() {
    private var deleteListener: (() -> Unit)? = null
    private val _binding by viewBinding(LayoutDialog1Binding::bind)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_dialog_1, container, false)
        view.setBackgroundColor(Color.TRANSPARENT)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding.deleteButton.setOnClickListener {
            deleteListener?.invoke()
            dismiss()
        }

        _binding.cancelButton.setOnClickListener {
            dismiss()
        }


    }

    fun setDeleteListener(f: (() -> Unit)) {
        deleteListener = f
    }

}