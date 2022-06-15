package com.example.memories_atlas.mainActivity

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.example.memories_atlas.mainActivity.serializeSET
import com.example.memories_atlas.mainActivity.deserializeSET
import com.example.memories_atlas.models.*
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@RunWith(JUnit4::class)
class MainActivityTest {

    @Test
    fun setToSetDBConversionTest(){
        val userSet = UserSet("Example", mutableListOf(Place("A Place","",24.9, 45.5,
            mutableListOf("Url"))))

        val userDBSet = UserSetDB("Example", mutableListOf(
            PlaceDB("A Place","",24.9, 45.5,
                mutableListOf("Url"))
        ))

        assertThat(userDBSet).isEqualTo(toUserSetDB(userSet))

    }

    @Test
    fun placeToDBConversionTest(){
        val place = Place("a place", "very nice place", 68.3, 21.43, mutableListOf("1url","2url"))

        val placeDB = PlaceDB("a place", "very nice place", 68.3, 21.43, mutableListOf("1url","2url"))

        assertThat(placeDB).isEqualTo(toPlaceDB(place))

    }

    @Test
    fun placeDBToPlaceConversionTest(){
        val place = Place("a place", "very nice place", 68.3, 21.43, mutableListOf("1url","2url"))

        val placeDB = PlaceDB("a place", "very nice place", 68.3, 21.43, mutableListOf("1url","2url"))

        assertThat(place).isEqualTo(toPlace(placeDB))
    }


    @Test
    fun serializeUserSetTest(){
        val userSet = UserSet("Example", mutableListOf(Place("A Place","",24.9, 45.5,
            mutableListOf("Url"))))


        val serRes = serializeSET(userSet)

        val expectedRes = SerializedData(Json.encodeToString(toUserSetDB(userSet)))

        assertThat(serRes).isEqualTo(expectedRes)
    }

    @Test
    fun deserializeSETTest(){
        val userSet = UserSet("Example", mutableListOf(Place("A Place","",24.9, 45.5,
            mutableListOf("Url"))))

        val serData = SerializedData(Json.encodeToString(toUserSetDB(userSet)))

        val result = deserializeSET(serData)

        assertThat(result).isEqualTo(userSet)
    }

}