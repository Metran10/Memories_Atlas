package com.example.memories_atlas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "sets")
data class UserSet(
    var title: String,
    val places: MutableList<Place>)
{
    @PrimaryKey(autoGenerate = true)
    var setID: Int = 0

}