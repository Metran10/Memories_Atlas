package com.example.memories_atlas.googleMapsActivities

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.mainActivity.SetsAdapter
import com.example.memories_atlas.models.UserSet

class MapGalleryAdapter(val context: Context, val photos: MutableList<String>, val onClickListener: SetsAdapter.OnClickListener): RecyclerView.Adapter<SetsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.map_gallery_image, parent, false)
        return SetsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetsAdapter.ViewHolder, position: Int) {
        val photo_uri = photos[position]
        val img_view = holder.itemView.findViewById<ImageView>(R.id.map_gallery_photo)

        img_view.setImageURI(Uri.parse(photo_uri))


    }

    override fun getItemCount(): Int {
        return photos.size
    }

}