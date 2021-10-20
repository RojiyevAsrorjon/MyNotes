package uz.gita.mynotes.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mynotes.R
import uz.gita.mynotes.data.entities.NoteData
import uz.gita.mynotes.databinding.ScreenDetailBinding
import uz.gita.mynotes.htmltext
import uz.gita.mynotes.ui.liveDatas.ScreenDetailLiveData

class ScreenDetail : Fragment(R.layout.screen_detail) {
    private val binding by viewBinding(ScreenDetailBinding::bind)
    private val liveData by viewModels<ScreenDetailLiveData>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titleText = binding.titleNewNote
        val richEditor = binding.richEditor
        var data = NoteData(0, "", "",
            0L, 1)
        richEditor.setPadding(10,10,10,10)
        arguments?.let {
            data = it.getSerializable("NOTE_DATA") as NoteData
            val spanSt = SpannableString(Html.fromHtml(data.description))
            titleText.text = data.title
            richEditor.setText(spanSt, TextView.BufferType.SPANNABLE)
        }

        titleText.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("NOTE_EDIT_DATA", data)
            bundle.putBoolean("FROM_DETAIL", true)
            findNavController().navigate(R.id.action_screenDetail_to_screenEdit, bundle)
        }
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        richEditor.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("NOTE_EDIT_DATA", data)
            findNavController().navigate(R.id.action_screenDetail_to_screenEdit, bundle)
        }

        binding.deleteButton.setOnClickListener {
            val dialog = EventDialogMain()
            dialog.setDeleteListener {
                data.isDeleted = 1
                liveData.deleteData(data)
                findNavController().popBackStack()
            }
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "delete_dialog") }

        }
        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val string = "Note : \n\t Title : ${data.title}\n\t Description : ${htmltext(data.description)}\n\t Time created : ${data.addedTime}"
            intent.putExtra(Intent.EXTRA_TEXT, string)
            startActivity(intent)
        }

    }
}