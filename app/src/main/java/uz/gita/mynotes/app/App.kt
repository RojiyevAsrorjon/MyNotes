package uz.gita.mynotes.app

import android.app.Application
import uz.gita.mynotes.data.domain.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.init(this)
    }
    companion object{
        lateinit var instance : App
    }
}