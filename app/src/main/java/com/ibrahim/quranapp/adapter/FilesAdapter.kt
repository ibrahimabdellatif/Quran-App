package com.ibrahim.quranapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.R
import com.ibrahim.quranapp.data.SurahData

class FilesAdapter(
    surahData: List<SurahData>,
    private val listener: OnItemClickListener,
    surasArgs: String?
) :
    RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    var surasNumbersList = surasArgs?.split(",")?.map { it.toInt() }
    var data = surahData
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.file_item, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    inner class FileViewHolder(item: View) : RecyclerView.ViewHolder(item), View.OnClickListener {
        val surahNumber: TextView = item.findViewById(R.id.tv_surah_number)
        val surahName: TextView = item.findViewById(R.id.tv_surah_name)
        val surahEnglishName: TextView = item.findViewById(R.id.tv_surah_english_name)
        val surahType: TextView = item.findViewById(R.id.tv_revelationType)
        val consLayoutFileItem: ConstraintLayout = item.findViewById(R.id.con_layout_file_item)

        fun bind(item: SurahData) {
            consLayoutFileItem.visibility = View.GONE
            try {

                for (i in surasNumbersList!!) {
                    if (i == item.number) {
                        surahNumber.text = item.number.toString()
                        surahName.text = item.name
                        surahEnglishName.text = item.englishName
                        surahType.text = item.revelationType
                        consLayoutFileItem.visibility = View.VISIBLE

                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                e.message?.let { Log.d("FileAdapter class", it) }
            }

        }


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            listener.onItemClick(position)
        }


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }


}