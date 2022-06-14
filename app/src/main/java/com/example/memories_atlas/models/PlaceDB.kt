package com.example.memories_atlas.models

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class PlaceDB(val title: String, val description: String, val latitude: Double, val longtitude: Double, val photos: MutableList<String>)

fun toPlace(pdb: PlaceDB): Place{
    return Place(pdb.title, pdb.description, pdb.latitude, pdb.longtitude, pdb.photos)
}

fun toPlaceDB(place: Place): PlaceDB{
    return PlaceDB(place.title, place.description, place.latitude, place.longtitude, place.photos)
}