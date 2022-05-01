package com.example.memories_atlas.mainActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.models.UserSet

// custom adapter for sets, looks ugly, but works for now
class SetsAdapter(val context: Context, val userSets: List<UserSet>, val onClickListener: OnClickListener) : RecyclerView.Adapter<SetsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userSet = userSets[position]
        // trigger function passed in constructor
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        val textViewTitle = holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text = userSet.title
    }

    override fun getItemCount() = userSets.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}