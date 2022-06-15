package com.example.memories_atlas.models

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class MemoriesDatabaseTest : TestCase(){

    private lateinit var  db: MemoriesDatabase
    private lateinit var  dao: SetsDAO

    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MemoriesDatabase::class.java).build()
        dao = db.setDAO()

    }

    @After
    fun closeDb(){
        db.close()
    }


    @Test
    fun writeAndRead(){
        val userSet = UserSet("title", mutableListOf(
            Place("a place","very nice",
            21.00, 37.00, mutableListOf())
        ))

        val data = SerializedData(Json.encodeToString(toUserSetDB(userSet)))

        dao.insertSetData(data)
        //val retrived = dao.getOneSets(Json.encodeToString(toUserSetDB(userSet)))
        val retrived = dao.getAllSets()

        assertThat(retrived.contains(data)).isTrue()
    }


}