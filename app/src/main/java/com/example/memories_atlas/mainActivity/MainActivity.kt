package com.example.memories_atlas.mainActivity

import android.app.Activity
import android.content.Intent
import android.media.ExifInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.googleMapsActivities.MapActivity
import com.example.memories_atlas.models.Place
import com.example.memories_atlas.models.UserSet
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set layout manager to setsView
        var setsRecyclerView = findViewById<RecyclerView>(R.id.setsRecyclerView)
        setsRecyclerView.layoutManager = LinearLayoutManager(this)

        // sets adapter to setsView, put fake data (implementation below), pass onClick function
        var sets = generateSampleData()
        setsRecyclerView.adapter = SetsAdapter(this, sets, object: SetsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {

                // open maps with  list of marks of selected set
                var intent = Intent(this@MainActivity, MapActivity::class.java)
                intent.putExtra(R.string.selected_map_set.toString(), sets[position])
                startActivityForResult(intent, REQUEST_CODE)
            }
        })

        // add new sets
        var addSetButton = findViewById<FloatingActionButton>(R.id.addSetButton)
        addSetButton.setOnClickListener {
            TODO("Add empty set by notifing adapter")
        }
    }

    companion object{
        var IMAGE_REQUEST_CODE = 100
    }

    private fun getImage(request_code: Int){
        IMAGE_REQUEST_CODE = request_code
        getImageFromGallery()
    }

    // opening gallery for image to choose, returns data in onActivityResult
    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //getting image
        //uri = data?.data
        if (requestCode ==  IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }

    }

    //Get coordinates in form mutablelistof(latVal, latDir, longVal, longDir)
    private fun getCoordOfImage(image_path: String): List<String>{
        val lat = getExifTagData(image_path, ExifInterface.TAG_GPS_LATITUDE)
        val isNorth = getExifTagData(image_path, ExifInterface.TAG_GPS_LATITUDE_REF)

        val long = getExifTagData(image_path, ExifInterface.TAG_GPS_LONGITUDE)
        val isWest = getExifTagData(image_path, ExifInterface.TAG_GPS_LONGITUDE_REF)

        var coord = mutableListOf<String>()
        if (lat != null) {
            coord.add(lat)
        }
        else{
            coord.add("")
        }
        if (isNorth != null) {
            coord.add(isNorth)
        }
        else{
            coord.add("")
        }
        if (long != null) {
            coord.add(long)
        }
        else{
            coord.add("")
        }
        if (isWest != null) {
            coord.add(isWest)
        }
        else{
            coord.add("")
        }


        return coord

    }


    //get value for in image meta-data
    private fun getExifTagData(image_path: String,tag: String): String? {
        val exif = ExifInterface(image_path)

        val neededVal = exif.getAttribute(tag)
        if (neededVal == null){
            return ""
        }
        else {
            return neededVal
        }
    }


    // generate fake data
    private fun generateSampleData(): MutableList<UserSet> {
        return mutableListOf(
            UserSet(
                "Memories from University",
                listOf(
                    Place("Branner Hall", "Best dorm at Stanford", "URI"),
                    Place("Gates CS building", "Many long nights in this basement", "URI"),
                    Place("Pinkberry", "First date with my wife", "URI")
                )
            ),
            UserSet("January vacation planning!",
                listOf(
                    Place("Tokyo", "Overnight layover", "URI"),
                    Place("Ranchi", "Family visit + wedding!", "URI"),
                    Place("Singapore", "Inspired by \"Crazy Rich Asians\"", "URI")
                )),
            UserSet("Singapore travel itinerary",
                listOf(
                    Place("Gardens by the Bay", "Amazing urban nature park", "URI"),
                    Place("Jurong Bird Park", "Family-friendly park with many varieties of birds", "URI"),
                    Place("Sentosa", "Island resort with panoramic views", "URI"),
                    Place("Botanic Gardens", "One of the world's greatest tropical gardens", "URI")
                )
            ),
            UserSet("My favorite places in the Midwest",
                listOf(
                    Place("Chicago", "Urban center of the midwest, the \"Windy City\"", "URI"),
                    Place("Rochester, Michigan", "The best of Detroit suburbia", "URI"),
                    Place("Mackinaw City", "The entrance into the Upper Peninsula", "URI"),
                    Place("Michigan State University", "Home to the Spartans", "URI"),
                    Place("University of Michigan", "Home to the Wolverines", "URI")
                )
            ),
            UserSet("Restaurants to try",
                listOf(
                    Place("Champ's Diner", "Retro diner in Brooklyn", "URI"),
                    Place("Althea", "Chicago upscale dining with an amazing view", "URI"),
                    Place("Shizen", "Elegant sushi in San Francisco", "URI"),
                    Place("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", "URI"),
                    Place("Kati Thai", "Authentic Portland Thai food, served with love", "URI")
                )
            )
        )
    }


}
