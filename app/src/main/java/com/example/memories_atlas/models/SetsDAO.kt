package com.example.memories_atlas.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SetsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(userSet: UserSet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoData: PhotoData)

    @Update
    suspend fun updateSet(userSet: UserSet)

    @Update
    suspend fun updatePlace(place: Place)

    @Update
    suspend fun updatePhoto(photoData: PhotoData)

    @Delete
    suspend fun deleteSet(userSet: UserSet)

    @Delete
    suspend fun deletePlace(place: Place)

    @Delete
    suspend fun deletePhotoData(photoData: PhotoData)


    @Transaction
    @Query("SELECT * FROM sets")
    suspend fun getAllSets(): LiveData<List<UserSet>>

    @Transaction
    @Query("SELECT * FROM sets WHERE sets.title = :setTitle")
    suspend fun getOneSets(setTitle: String): LiveData<UserSet>

    @Transaction
    @Query("SELECT * FROM places WHERE setID = :setID")
    suspend fun getSetPlaces(setID: Int): LiveData<List<Place>>

    @Transaction
    @Query("SELECT * FROM photos WHERE placeID = :placeID")
    suspend fun getPlacePhotos(placeID: Int): LiveData<List<PhotoData>>


}