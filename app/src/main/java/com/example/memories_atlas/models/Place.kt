package com.example.memories_atlas.models

import androidx.annotation.Keep
import java.io.Serializable


data class Place(val title: String, val description: String, val latitude: Double, val longtitude: Double, val photos: MutableList<String>) : Serializable