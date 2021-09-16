package com.ibrahim.quranapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.R
import com.ibrahim.quranapp.data.Data
import com.ibrahim.quranapp.data.SurahData

class FilesAdapter(private val surahData: List<SurahData>) :
    RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    var data = surahData
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class FileViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val surahNumber: TextView = item.findViewById(R.id.tv_surah_number)
        val surahName: TextView = item.findViewById(R.id.tv_surah_name)
        val surahEnglishName: TextView = item.findViewById(R.id.tv_surah_english_name)
        val surahType: TextView = item.findViewById(R.id.tv_revelationType)
        fun bind(item: SurahData) {
            surahNumber.text = item.number.toString()
            surahName.text = item.name
            surahEnglishName.text = item.englishName
            surahType.text = item.revelationType
        }

        companion object {
            fun from(parent: ViewGroup): FileViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                var view = layoutInflater.inflate(R.layout.file_item, parent, false)
                return FileViewHolder(view)
            }
        }
    }
}