package com.example.memories_atlas.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
class PhotoData(
    val photoUri: String,
    val placeID: Int
) {
    @PrimaryKey(autoGenerate = true)
    var photoID: Int = 0
}