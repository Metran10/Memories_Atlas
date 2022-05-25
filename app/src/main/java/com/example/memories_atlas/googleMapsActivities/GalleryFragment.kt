package com.example.memories_atlas.googleMapsActivities

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.models.Place
import androidx.recyclerview.widget.LinearLayoutManager


class GalleryFragment(place: Place): Fragment() {
    private lateinit var newRec: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_gallery_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        newRec = view.findViewById(R.id.gallery_rec)

    }

}