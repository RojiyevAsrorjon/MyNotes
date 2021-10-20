package uz.gita.mynotes.ui.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import jp.wasabeef.richeditor.RichEditor
import uz.gita.mynotes.R
import uz.gita.mynotes.convertToData
import uz.gita.mynotes.data.entities.NoteData
import uz.gita.mynotes.databinding.ScreenAddBinding
import uz.gita.mynotes.hideKeyBoard
import uz.gita.mynotes.ui.liveDatas.ScreenAddLiveData
import uz.gita.mynotes.ui.liveDatas.ScreenEditLiveData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ScreenEdit : Fragment(R.layout.screen_add) {
    private val liveData by viewModels<ScreenEditLiveData>()
    private lateinit var titleEdittext: EditText
    private lateinit var richEdit: RichEditor
    private var data: NoteData? = null
    private var newData = NoteData(0, "", "", 0L, 0)
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
        arguments?.let {
            data = it.getSerializable("NOTE_EDIT_DATA") as NoteData
            titleEdittext.hint = data?.title
            richEdit.html = data?.description
        }




        setEditorType()


        binding.saveButton.setOnClickListener {
            saveOrUpdate()
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

    private fun saveOrUpdate() {
        if (richEdit.html != null && titleEdittext.text.toString().trim().isNotEmpty()) {
            if (richEdit.html.toString().trim().isNotEmpty()) {
                val textDesc = if (richEdit.html == null) "Empty note" else richEdit.html
                val textTitle = if (titleEdittext.text.toString().isEmpty()) "Untitled" else titleEdittext.text.toString()
                val currentTime = System.currentTimeMillis()
                data?.let { NoteData(it.id, textTitle, textDesc, currentTime, 0).let { liveData.update(it) } }
            }
        }
        }


    override fun onPause() {
        super.onPause()
        if (!isSaved) {
            saveOrUpdate()
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
                    if (isUnderlined) {
                        binding.underlinedFormat.setBackgroundResource(R.color.white)
                        isUnderlined = false
                    } else {
                        binding.underlinedFormat.setBackgroundResource(R.drawable.bg_button)
                        isUnderlined = true
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