package uz.gita.mynotes.ui.adapters

import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.mynotes.R
import uz.gita.mynotes.convertToDate
import uz.gita.mynotes.data.entities.NoteData
import uz.gita.mynotes.htmltext

class NoteAdapter : ListAdapter<NoteData, NoteAdapter.ViewHolder>(DiffItem) {
    private var listener: ((NoteData) -> Unit)? = null
    private var longClickListener: ((NoteData) -> Unit)? = null
    var query = ""

    object DiffItem : DiffUtil.ItemCallback<NoteData>() {
        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
            return oldItem == newItem
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener { listener?.invoke(getItem(absoluteAdapterPosition)) }
            itemView.setOnLongClickListener {
                longClickListener?.invoke(getItem(absoluteAdapterPosition))
                true
            }
        }

        val textTitle = itemView.findViewById<TextView>(R.id.noteTitleItem)
        val textDef = itemView.findViewById<TextView>(R.id.noteDescItem)
        val textTime = itemView.findViewById<TextView>(R.id.noteTimeItem)

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind() {
            val data = getItem(absoluteAdapterPosition)
            val spanSt = SpannableString(data.title)
            val foreGroundColor = ForegroundColorSpan(Color.RED)
            val startIndex = data.title.indexOf(query, 0, true)
            val endIndex = startIndex + query.length
            if (startIndex == -1) return
            spanSt.setSpan(
                foreGroundColor,
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textTitle.text = spanSt
            textDef.text = htmltext(data.description)
            textTime.text = convertToDate(data.addedTime)
        }
    }

    fun setListener(block: (NoteData) -> Unit) {
        listener = block
    }

    fun setLongClickListener(block: (NoteData) -> Unit) {
        longClickListener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}
