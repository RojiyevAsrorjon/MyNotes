package uz.gita.mynotes.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import uz.gita.mynotes.R
import uz.gita.mynotes.data.entities.NoteData
import uz.gita.mynotes.databinding.LayoutDeletedBinding
import uz.gita.mynotes.ui.adapters.NoteAdapter
import uz.gita.mynotes.ui.adapters.RecyclerAdapter
import uz.gita.mynotes.ui.liveDatas.ScreenDeleteLiveData

class ScreenDeleted : Fragment(R.layout.layout_deleted) {
    private val viewModel by viewModels<ScreenDeleteLiveData>()
    private val binding by viewBinding(LayoutDeletedBinding::bind)
    private val noteAdapter = NoteAdapter()
    private val list = ArrayList<NoteData>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        binding.recyclerView.adapter = noteAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = layoutManager
        noteAdapter.setLongClickListener {note ->
            val dialog = EventDialog()
            dialog.setDeleteListener{
                var isIgnored = true
                val index = list.indexOfFirst { it.id == note.id }
                list.removeAt(index)
                noteAdapter.submitList(list)
                Snackbar.make(binding.constraint, "Note is deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        list.add(index, note)
                        noteAdapter.submitList(list)
                        isIgnored = false
                    }.show()
                if (isIgnored){
                    viewModel.deleteItem(note)
                }
            }

            dialog.setRestoreListener {
                note.isDeleted = 0
                viewModel.updateItem(note)
            }
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "event_dialog") }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.deleteLiveData.observe(viewLifecycleOwner, resultObserver)
    }

    private fun setIsEmpty(data : List<NoteData>) {
        if (data.isEmpty()) {
            binding.isEmptyImage.visibility = View.VISIBLE
        }
        else binding.isEmptyImage.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private val resultObserver = Observer<List<NoteData>>{
        noteAdapter.submitList(it)
        list.clear()
        list.addAll(it)
        setIsEmpty(it)
    }
}