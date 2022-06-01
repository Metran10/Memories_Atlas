package com.example.memories_atlas.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        SerializedData::class
    ],
    version = 1
)
abstract class MemoriesDatabase : RoomDatabase() {


    abstract fun setDAO(): SetsDAO


    companion object {
        @Volatile
        private var INSTANCE: MemoriesDatabase? = null

        fun getDatabase(context: Context): MemoriesDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    MemoriesDatabase::class.java,
                    "memories_db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }

        }

    }



    /*
    abstract val setsDAO: SetsDAO

    companion object{
        @Volatile
        private var INSTANCE: MemoriesDatabase? = null

        fun getInstance(context: Context): MemoriesDatabase {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MemoriesDatabase::class.java,
                    "memories_db"
                ).build().also { INSTANCE = it }
            }
        }

    }
    */


}