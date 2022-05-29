package com.example.memories_atlas.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sets")
data class SerializedData(
    @PrimaryKey(autoGenerate = false)
    val serializedString: String
) {
}