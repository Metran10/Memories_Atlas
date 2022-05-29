package com.example.memories_atlas.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.memories_atlas.models.Place
import com.example.memories_atlas.models.UserSet

class SetWithPlaces(
    @Embedded val userSet: UserSet,
    @Relation(
        parentColumn = "setID",
        entityColumn = "setID"
    )
    val places: List<Place>
){
}