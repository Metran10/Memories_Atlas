package com.example.memories_atlas.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@kotlinx.serialization.Serializable
data class UserSetDB(var title: String, val places: MutableList<PlaceDB>) {
}

fun toUserSet(userSetDB: UserSetDB): UserSet{
    var newPlaces = mutableListOf<Place>()
    userSetDB.places.forEach {newPlaces.add(toPlace(it))}

    return UserSet(userSetDB.title, newPlaces)
}

fun toUserSetDB(userSet: UserSet): UserSetDB{
    var newPlacesDB = mutableListOf<PlaceDB>()
    userSet.places.forEach { newPlacesDB.add(toPlaceDB(it)) }

    return UserSetDB(userSet.title, newPlacesDB)
}