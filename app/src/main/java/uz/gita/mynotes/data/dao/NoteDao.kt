package uz.gita.mynotes.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.mynotes.data.entities.NoteData

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_entities WHERE isDeleted = 0")
    fun getAllNotes(): Flow<List<NoteData>>

    @Query("SELECT * FROM note_entities WHERE isDeleted = 1")
    fun getDeletedNotes(): Flow<List<NoteData>>


    @Query("SELECT * FROM note_entities WHERE title LIKE '%' || :query ||'%' AND isDeleted=0")
    fun getAllNotesByString(query: String): Flow<List<NoteData>>

    @Insert
    suspend fun insert(noteData: NoteData): Long

    @Delete
    suspend fun delete(noteData: NoteData)

    @Update
    suspend fun update(noteData: NoteData)
}