package uz.gita.mynotes.ui.liveDatas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.mynotes.data.domain.AppDatabase
import uz.gita.mynotes.data.entities.NoteData

class ScreenDetailLiveData : ViewModel() {
    private val repository = AppDatabase.getDatabase().noteDao()

    fun deleteData(data: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(data)
        }

    }


}