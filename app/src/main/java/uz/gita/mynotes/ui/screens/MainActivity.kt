package uz.gita.mynotes.ui.screens

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import jp.wasabeef.richeditor.RichEditor
import uz.gita.mynotes.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation = supportFragmentManager.findFragmentById(R.id.container1) as NavHostFragment
        val graph = navigation.navController.navInflater.inflate(R.navigation.app_nav)
        navigation.navController.graph = graph
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }


}