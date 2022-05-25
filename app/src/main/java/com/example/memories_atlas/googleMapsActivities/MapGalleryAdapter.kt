package com.example.memories_atlas.googleMapsActivities

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.mainActivity.SetsAdapter
import com.example.memories_atlas.models.UserSet

class MapGalleryAdapter(): RecyclerView.Adapter<GalleryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val galleryImage = layoutInflater.inflate(R.layout.map_gallery_image, parent, false)

        return GalleryViewHolder(galleryImage)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image_view: ImageView = holder.itemView.findViewById(R.id.map_gallery_photo)
        //image_view.setImageURI(Uri.parse())

    }

    override fun getItemCount(): Int {
        return 3
    }

}


class GalleryViewHolder(val view: View): RecyclerView.ViewHolder(view){

}