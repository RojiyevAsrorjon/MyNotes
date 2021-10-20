package uz.gita.mynotes.ui.liveDatas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.mynotes.data.domain.AppDatabase
import uz.gita.mynotes.data.entities.NoteData

class ScreenAddLiveData : ViewModel() {
//    private val _addNoteLiveData = MutableLiveData<List<NoteData>>()
//    val addNoteLiveData : LiveData<List<NoteData>> get() = _addNoteLiveData

    private val repository = AppDatabase.getDatabase()

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.noteDao().getAllNotes().collect {
//                _addNoteLiveData.postValue(it)
//            }
//        }
//    }

    fun insert(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.noteDao().insert(noteData)
        }
    }

}