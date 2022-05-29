package com.example.memories_atlas.models

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SetsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetData(serializedData: SerializedData)

    @Delete
    fun deleteSetData(serializedData: SerializedData)

    @Update
    fun updateSetData(serializedData: SerializedData)


    @Transaction
    @Query("SELECT * FROM sets")
    fun getAllSets(): LiveData<List<SerializedData>>

    @Transaction
    @Query("SELECT * FROM sets WHERE serializedString = :serializedString")
    fun getOneSets(serializedString: String): LiveData<List<SerializedData>>


}