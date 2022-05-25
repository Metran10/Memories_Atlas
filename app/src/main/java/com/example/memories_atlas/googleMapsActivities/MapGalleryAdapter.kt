package com.example.memories_atlas.googleMapsActivities

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.mainActivity.SetsAdapter
import com.example.memories_atlas.models.UserSet

class MapGalleryAdapter(val context: Context, val photos: MutableList<String>) : RecyclerView.Adapter<MapGalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.map_gallery_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]

        // ustawianie zdjecia
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView333)
        imageView.setImageURI(Uri.parse(photo))
    }

    override fun getItemCount() = photos.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}