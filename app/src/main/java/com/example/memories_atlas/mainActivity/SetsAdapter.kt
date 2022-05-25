package com.example.memories_atlas.mainActivity

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.models.UserSet

class SetsAdapter(val context: Context, val userSets: MutableList<UserSet>, val onClickListener: OnClickListener) : RecyclerView.Adapter<SetsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onLongClick(position: Int)
        fun onButtonClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_of_sets_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userSet = userSets[position]
        // trigger function passed in constructor
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        holder.itemView.setOnLongClickListener {
            onClickListener.onLongClick(position)
            return@setOnLongClickListener true
        }
        holder.itemView.findViewById<Button>(R.id.buttonik).setOnClickListener {
            onClickListener.onButtonClick(position)
        }

        // ustawianie textu
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.textt)
        textViewTitle.text = userSet.title

        // ustawianie obrazka
        if (userSet.places.size > 0) {
            val photoView = holder.itemView.findViewById<ImageView>(R.id.photoo)

            for (place in userSet.places) {

                if (!place.photos.isEmpty()){

                    photoView.setImageURI(Uri.parse(place.photos[0]))
                }
            }
        }
    }

    override fun getItemCount() = userSets.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}