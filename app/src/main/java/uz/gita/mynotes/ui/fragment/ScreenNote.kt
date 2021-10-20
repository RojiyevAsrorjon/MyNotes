package uz.gita.mynotes.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mynotes.R
import uz.gita.mynotes.data.entities.NoteData
import uz.gita.mynotes.databinding.ScreenMainBinding
import uz.gita.mynotes.hideKeyBoard
import uz.gita.mynotes.ui.adapters.NoteAdapter
import uz.gita.mynotes.ui.liveDatas.ScreenNoteLiveData

class ScreenNote : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel by viewModels<ScreenNoteLiveData>()
    private val list = ArrayList<NoteData>()
    private var querySt = ""
    private lateinit var data: ArrayList<NoteData>
    private lateinit var handler: Handler
    private val noteAdapter = NoteAdapter()

    @SuppressLint("ShowToast", "NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        handler = Handler(Looper.getMainLooper())
        binding.idRecyclerview.adapter = noteAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        binding.idRecyclerview.layoutManager = layoutManager

//        liveData.searchViewLiveData.observe(viewLifecycleOwner, searchObserver)


        noteAdapter.setListener {
            val bundle = Bundle()
            bundle.putSerializable("NOTE_DATA", it)
            findNavController().navigate(R.id.action_screenNote_to_screenDetail, bundle)
        }

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_screenNote_to_screenAdd)
        }

        binding.deleteButton.setOnClickListener {
            findNavController().navigate(R.id.action_screenNote_to_screenDeleted)
        }

        binding.seachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    querySt = it.trim()
                    viewModel.loadData(querySt)
                }
                return true
            }


            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    newText?.let {
                        querySt = it.trim()
                        viewModel.loadData(querySt)
                        }
                }, 500)
                return true
            }
        })
        viewModel.screenNoteLiveData.observe(viewLifecycleOwner, listObserver)
    }

    private fun setIsEmpty(data: List<NoteData>) {
        if (data.isEmpty()) {
            binding.isEmptyImage.visibility = View.VISIBLE
        } else binding.isEmptyImage.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private val listObserver = Observer<List<NoteData>> {
        noteAdapter.submitList(it)
        list.clear()
        list.addAll(it)
        setIsEmpty(it)
    }


    override fun onPause() {
        super.onPause()
        binding.seachView.isFocusable = false
        hideKeyBoard()
    }
}