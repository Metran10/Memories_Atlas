package com.example.memories_atlas.models

import java.io.Serializable

// mark on the map
data class Place(val title: String, val description: String, val latitude: Double, val longtitude: Double) : Serializable