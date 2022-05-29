package com.example.memories_atlas.models

import androidx.annotation.Keep


@Keep
@kotlinx.serialization.Serializable
data class UserSet(var title: String, val places: MutableList<Place>)