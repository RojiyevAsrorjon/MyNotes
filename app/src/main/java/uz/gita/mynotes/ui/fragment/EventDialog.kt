package uz.gita.mynotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.mynotes.R
import uz.gita.mynotes.databinding.LayoutDialogBinding

class EventDialog : BottomSheetDialogFragment() {
    private var deleteListener: (() -> Unit)? = null
    private var restoreListener: (() -> Unit)? = null
    private val _binding by viewBinding(LayoutDialogBinding::bind)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding.deleteButton.setOnClickListener {
            deleteListener?.invoke()
            dismiss()
        }

        _binding.restoreButton.setOnClickListener {
            restoreListener?.invoke()
            dismiss()
        }
    }

    fun setRestoreListener(f: (() -> Unit)) {
        restoreListener = f
    }

    fun setDeleteListener(f: (() -> Unit)) {
        deleteListener = f
    }

}