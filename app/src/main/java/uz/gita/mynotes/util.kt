package uz.gita.mynotes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun htmltext(html: String?): String {
    return   android.text.Html.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

fun Fragment.hideKeyBoard(){
    view?.let { activity?.hideKeyboard(it)}
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus?:View(this))
}

fun Context.hideKeyboard(view : View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
}
@SuppressLint("SimpleDateFormat")
fun convertToData(time: String): Long {
    try {
        val dateFormat = SimpleDateFormat("dd/mm/yyyy HH:mm")
        val date = dateFormat.parse(time)
        return date.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0L
}

fun convertToDate(time: Long): String {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time

    val year = "${calendar.get(Calendar.YEAR)}"
    val month =
        if (calendar.get(Calendar.MONTH) < 9) "0${calendar.get(Calendar.MONTH) + 1}" else "${calendar.get(Calendar.MONTH) + 1}"
    val day = if (calendar.get(Calendar.DAY_OF_MONTH) < 10) "0${calendar.get(Calendar.DAY_OF_MONTH)}" else "${
        calendar.get(Calendar.DAY_OF_MONTH)
    }"
    val hour =
        if (calendar.get(Calendar.HOUR) < 10) "0${calendar.get(Calendar.HOUR)}" else "${calendar.get(Calendar.HOUR)}"
    val minute =
        if (calendar.get(Calendar.MINUTE) < 10) "0${calendar.get(Calendar.MINUTE)}" else "${calendar.get(Calendar.MINUTE)}"

    return "$day/$month/$year $hour:$minute"
}