package com.example.memories_atlas.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.memories_atlas.models.PhotoData
import com.example.memories_atlas.models.Place

class PlaceWithPhotos(
    @Embedded val place: Place,
    @Relation(
        parentColumn = "placeID",
        entityColumn = "placeID"
    )
    val photos: List<PhotoData>
) {
}