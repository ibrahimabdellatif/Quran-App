package com.ibrahim.quranapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.QuranData
import com.ibrahim.quranapp.R

class HomeAdapter (private val quranData: List<QuranData>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var data = quranData
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }


    override fun getItemCount() = data.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val readerName :TextView = itemView.findViewById(R.id.tv_reader_name)
        val rewayaType :TextView = itemView.findViewById(R.id.tv_rewaya_type)

        fun bind(item :QuranData){
            readerName.text = item.readerName
            rewayaType.text = item.rewaya
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                var layoutInflater = LayoutInflater.from(parent.context)
                var view =
                    layoutInflater.inflate(R.layout.home_item, parent, false)
                return ViewHolder(view)
            }
        }
    }
}