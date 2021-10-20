package uz.gita.mynotes.ui.liveDatas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mynotes.data.domain.AppDatabase
import uz.gita.mynotes.data.entities.NoteData

class ScreenEditLiveData : ViewModel() {
    private val repository = AppDatabase.getDatabase()
    private val data = ArrayList<NoteData>()
    fun update(noteData: NoteData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.noteDao().update(noteData)
        }
    }
}