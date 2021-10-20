package uz.gita.mynotes.data.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.mynotes.app.App
import uz.gita.mynotes.data.dao.NoteDao
import uz.gita.mynotes.data.entities.NoteData

@Database(entities = [NoteData::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun noteDao() : NoteDao
    companion object{
        private lateinit var instance : AppDatabase

        fun init(context: Context){
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(context,AppDatabase::class.java, "note_database")
                    .allowMainThreadQueries()
                    .build()

            }
        }
        fun getDatabase() = instance
    }
}