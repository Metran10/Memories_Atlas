package com.example.memories_atlas.models

import java.io.Serializable

// set of marks
data class UserSet(val title: String, val places: List<Place>) : Serializable