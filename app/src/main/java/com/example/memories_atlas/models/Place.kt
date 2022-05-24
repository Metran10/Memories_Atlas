package com.example.memories_atlas.models

import android.net.Uri
import java.io.Serializable

data class Place(val title: String, val description: String, val latitude: Double, val longtitude: Double, val photos: MutableList<Uri>) : Serializable