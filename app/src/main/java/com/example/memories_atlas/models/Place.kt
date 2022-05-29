package com.example.memories_atlas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "places")
data class Place(
    val title: String,
    val setID: Int,
    val description: String,
    val latitude: Double,
    val longtitude: Double
){
    @PrimaryKey(autoGenerate = true)
    var placeID: Int = 0;
}