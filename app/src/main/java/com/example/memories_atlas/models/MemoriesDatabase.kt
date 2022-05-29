package com.example.memories_atlas.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [
        UserSet::class,
        Place::class,
        PhotoData::class
    ],
    version = 1
)
abstract class MemoriesDatabase : RoomDatabase() {

    abstract val setDao: SetsDAO


    companion object {
        @Volatile
        private var INSTANCE: MemoriesDatabase? = null

        fun getInstance(context: Context): MemoriesDatabase {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MemoriesDatabase::class.java,
                    "memories_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

    }


}