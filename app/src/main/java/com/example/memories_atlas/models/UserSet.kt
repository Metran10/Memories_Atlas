package com.example.memories_atlas.models

import androidx.annotation.Keep
import java.io.Serializable



data class UserSet(var title: String, val places: MutableList<Place>) : Serializable