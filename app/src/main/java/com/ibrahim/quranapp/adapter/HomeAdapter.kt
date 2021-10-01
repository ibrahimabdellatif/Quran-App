package com.ibrahim.quranapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.quranapp.R
import com.ibrahim.quranapp.data.QuranData

class HomeAdapter(
    private val quranData: List<QuranData>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    var data = quranData
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view =
            layoutInflater.inflate(R.layout.home_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }


    override fun getItemCount() = data.size

    inner class HomeViewHolder(var itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val readerName: TextView = itemView.findViewById(R.id.tv_reader_name)
        val rewayaType: TextView = itemView.findViewById(R.id.tv_rewaya_type)
        //val firstLetter: TextView = itemView.findViewById(R.id.tv_first_letter)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            listener.onItemClick(position)
        }


        fun bind(item: QuranData) {
            readerName.text = item.name
            rewayaType.text = item.rewaya
            //firstLetter.text = item.letter
//            firstLetter.text = item.Server
        }


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}