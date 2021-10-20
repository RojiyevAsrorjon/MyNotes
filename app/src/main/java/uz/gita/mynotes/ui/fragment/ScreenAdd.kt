package uz.gita.mynotes.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import jp.wasabeef.richeditor.RichEditor
import uz.gita.mynotes.R
import uz.gita.mynotes.data.entities.NoteData
import uz.gita.mynotes.databinding.ScreenAddBinding
import uz.gita.mynotes.hideKeyBoard
import uz.gita.mynotes.ui.liveDatas.ScreenAddLiveData

class ScreenAdd : Fragment(R.layout.screen_add) {
    private val viewModel by viewModels<ScreenAddLiveData>()
    private lateinit var titleEdittext: EditText
    private lateinit var richEdit: RichEditor
    private var isBold = false
    private var isItalic = false
    private var isUnderlined = false

    private var _binding: ScreenAddBinding? = null
    private val binding get() = _binding!!

    private var isSaved = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenAddBinding.bind(view)
        richEdit = binding.richEditor
        titleEdittext = binding.titleNewNote
        setEditorType()
        binding.saveButton.setOnClickListener {
            save()
            isSaved = true
            hideKeyBoard()
            findNavController().popBackStack()
        }

        binding.backButton.setOnClickListener {
            hideKeyBoard()
            isSaved = true
            findNavController().popBackStack()
        }
    }

    private fun save() {
        if (richEdit.html != null && !checkText(richEdit.html.toString())) {
            val textDesc = if (richEdit.html == null) "Empty note" else richEdit.html
            val textTitle = if (titleEdittext.text.toString().isEmpty()) "Untitled" else titleEdittext.text.toString()
            val currentTime = System.currentTimeMillis()
            NoteData(0, textTitle, textDesc, currentTime, 0).let { viewModel.insert(it) }
        }
    }
    private fun checkText(text: String) : Boolean {
        val replace = text.replace(" ", "")
        return replace.isEmpty()
    }

    override fun onPause() {
        super.onPause()
        if (!isSaved) {
            save()
            isSaved = false
        }
    }


    private fun setEditorType() {
        val colorButton = binding.colorTextFormat
        richEdit.setPadding(16, 16, 16, 16)
        richEdit.setEditorFontSize(22)
        richEdit.setPlaceholder("Insert text here...")
        richEdit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                colorButton.setOnClickListener {
                    ColorPickerDialog
                        .Builder(requireContext())
                        .setTitle("Pick text color")
                        .setColorShape(ColorShape.SQAURE)
                        .setColorListener { color, colorHex ->
                            richEdit.setTextColor(color)
                        }
                        .show()
                }
                binding.textBackFormat.setOnClickListener {
                    MaterialColorPickerDialog
                        .Builder(requireContext())
                        .setTitle("Pick Theme")
                        .setColorShape(ColorShape.SQAURE)
                        .setColorSwatch(ColorSwatch._300)
                        .setColorListener { color, colorHex ->
                            richEdit.setTextBackgroundColor(Color.parseColor(colorHex))
                        }
                        .show()
                }
                binding.italicFormat.setOnClickListener {
                    if (isItalic) {
                        binding.italicFormat.setBackgroundResource(R.color.white)
                        isItalic = false
                    } else {
                        binding.italicFormat.setBackgroundResource(R.drawable.bg_button)
                        isItalic = true
                    }
                    richEdit.setItalic()
                }
                binding.boldFormat.setOnClickListener {
                    richEdit.setBold()
                    if (isBold) {
                        binding.boldFormat.setBackgroundResource(R.color.white)
                        isBold = false
                    } else {
                        binding.boldFormat.setBackgroundResource(R.drawable.bg_button)
                        isBold = true
                    }
                }
                binding.underlinedFormat.setOnClickListener {
                    richEdit.setUnderline()
                    isUnderlined = if (isUnderlined) {
                        binding.underlinedFormat.setBackgroundResource(R.color.white)
                        false
                    } else {
                        binding.underlinedFormat.setBackgroundResource(R.drawable.bg_button)
                        true
                    }
                }

                binding.textSizeFormat.setOnClickListener {


                    val popupMenu = PopupMenu(requireContext(), binding.textSizeFormat)
                    popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { item ->
                        if (item != null) {
                            when (item.itemId) {
                                R.id.fourteen -> {
                                    richEdit.setFontSize(14)
                                }
                                R.id.eightteen -> {
                                    richEdit.setFontSize(18)
                                }
                                R.id.twentyTwo -> {
                                    richEdit.setFontSize(22)
                                }
                                R.id.thirtyThree -> {
                                    richEdit.setFontSize(32)
                                }
                            }
                        }
                        true
                    }
                    popupMenu.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}