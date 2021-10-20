package uz.gita.mynotes.ui.liveDatas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.mynotes.data.domain.AppDatabase
import uz.gita.mynotes.data.entities.NoteData

class ScreenNoteLiveData : ViewModel() {
    private val _screenNoteLiveData = MutableLiveData<List<NoteData>>()
    val screenNoteLiveData : LiveData<List<NoteData>> get() = _screenNoteLiveData

    private val repository = AppDatabase.getDatabase()

    private val _searchViewLiveData = MutableLiveData<String>()
    val searchViewLiveData : LiveData<String> get() = _searchViewLiveData
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.noteDao().getAllNotes().collect {
                _screenNoteLiveData.postValue(it)
            }
        }
    }

    fun loadData(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.noteDao().getAllNotesByString(query).collect {
                _screenNoteLiveData.postValue(it)
            }
        }
    }

}