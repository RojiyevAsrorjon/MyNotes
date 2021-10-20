package uz.gita.mynotes.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_entities")
data class NoteData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long,
    @ColumnInfo(name = "title")
    var title: String,
    var description: String,
    var addedTime: Long,
    @ColumnInfo(name = "isDeleted")
    var isDeleted: Int
) : Serializable{
}