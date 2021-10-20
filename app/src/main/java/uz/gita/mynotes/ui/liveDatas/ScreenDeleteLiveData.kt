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

class ScreenDeleteLiveData : ViewModel() {

    private val _deletedLiveData = MutableLiveData<List<NoteData>>()
    val deleteLiveData : LiveData<List<NoteData>> get() = _deletedLiveData

    private val repository = AppDatabase.getDatabase().noteDao()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDeletedNotes().collect {
                _deletedLiveData.postValue(it)
            }
        }
    }

    fun deleteItem(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(noteData)
        }
    }
    fun updateItem(data: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(data)
        }
    }
}