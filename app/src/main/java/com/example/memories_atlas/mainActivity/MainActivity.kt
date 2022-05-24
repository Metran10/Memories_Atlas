package com.example.memories_atlas.mainActivity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.googleMapsActivities.MapActivity
import com.example.memories_atlas.models.Place
import com.example.memories_atlas.models.UserSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

private const val RETURN_MAP_ACTIVITY_CODE = 1
private const val RETURN_NEW_SET_CODE = 2
private const val RETURN_EDIT_SET_CODE = 3
private const val FILENAME = "UserData.data"

class MainActivity : AppCompatActivity() {

    private lateinit var sets: MutableList<UserSet>
    private lateinit var setAdapter: SetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // set layout manager to setsView
        var setsRecyclerView = findViewById<RecyclerView>(R.id.setsRecyclerView)
        setsRecyclerView.layoutManager = LinearLayoutManager(this)

        // set adapter to setsView, put fake data (implementation below), pass onClick function
        sets = deserializeUserMaps(this).toMutableList()

        // set adapter
        setAdapter = SetsAdapter(this, sets,
            object: SetsAdapter.OnClickListener {
                override fun onItemClick(position: Int) {

                    // open maps with  list of marks of selected set
                    var intent = Intent(this@MainActivity, MapActivity::class.java)
                    intent.putExtra(R.string.selected_map_set.toString(), sets[position])
                    startActivityForResult(intent, RETURN_MAP_ACTIVITY_CODE)
                }
                override fun onLongClick(position: Int) {
                    sets.removeAt(position)
                    setAdapter.notifyDataSetChanged()
                }
                override fun onButtonClick(position: Int) {
                    var intent = Intent(this@MainActivity, EditSetActivity::class.java)
                    intent.putExtra(R.string.selected_map_set.toString(), sets[position])
                    intent.putExtra("index", position)
                    startActivityForResult(intent, RETURN_EDIT_SET_CODE)
                }

            }
        )
        setsRecyclerView.adapter = setAdapter

        // add new sets
        var addSetButton = findViewById<FloatingActionButton>(R.id.addSetButton)
        addSetButton.setOnClickListener {

            var intent = Intent(this@MainActivity, EditSetActivity::class.java)
            intent.putExtra("index", -1)
            startActivityForResult(intent, RETURN_NEW_SET_CODE)
        }
    }

//    companion object{
//        var IMAGE_CODE = 100
//    }
//
//    private fun getImage(request_code: Int){
//        this@Companion.IMAGE_CODE = request_code
//        getImageFromGallery()
//    }
//
//    // opening gallery for image to choose, returns data in onActivityResult
//    private fun getImageFromGallery(){
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, this@Companion.IMAGE_CODE)
//    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RETURN_MAP_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {

            val title = data?.getStringExtra("title")
            val newSet = data?.getSerializableExtra(title) as UserSet
            print(newSet)
            sets.remove(sets.find { set -> set.title == title })
            sets.add(newSet)
            //serializeUserMaps(this, sets)
        }

        if (requestCode == RETURN_NEW_SET_CODE && resultCode == Activity.RESULT_OK) {

            var numLat = 0.0
            var numLong = 0.0

            var name = data?.getStringExtra("name")
            if (name == null || name == "") name = "Default"
            var place = data?.getStringExtra("place")
            if (place == null || place == "") place = "Default"
            var description = data?.getStringExtra("description")
            if (description == null || description == "") description = "Default"
            var lat = data?.getStringExtra("lat")
            if (lat == null || lat == "") numLat = 0.0
            else numLat = lat.toDouble()
            var long = data?.getStringExtra("long")
            if (long == null || long == "") numLong = 0.0
            else numLong = long.toDouble()

            val newSet = UserSet(
                name,
                mutableListOf(
                    Place(place, description, numLat, numLong, mutableListOf())
                )
            )

            sets.add(newSet)
            //serializeUserMaps(this, sets)
        }

        if (requestCode == RETURN_EDIT_SET_CODE && resultCode == Activity.RESULT_OK) {

            var name = data?.getStringExtra("name")
            if (name == null || name == "") name = "Default"
            val index = data?.getStringExtra("index")

            if (index != null) {
                sets[index.toInt()].title = name
            }
        }

        setAdapter.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getDataFile(context: Context) : File {

        return File(context.filesDir, FILENAME)
    }

    private fun serializeUserMaps(context: Context, userSets: MutableList<UserSet>) {

        ObjectOutputStream(FileOutputStream(getDataFile(context))).use { it.writeObject(userSets) }
    }

    private fun deserializeUserMaps(context: Context) : MutableList<UserSet> {

        val dataFile = getDataFile(context)
        if (!dataFile.exists()) {

            return generateSampleData()
        }

        ObjectInputStream(FileInputStream(dataFile)).use { return it.readObject() as MutableList<UserSet> }
    }

    private fun print(set: UserSet ) {

        var str = set.title

        for (element in set.places) {

            str += " " + element.title + " " + element.longtitude + " " + element.latitude
        }
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()

    }

    // generate fake data
    private fun generateSampleData(): MutableList<UserSet> {
        return mutableListOf(
            UserSet(
                "Memories from University",
                mutableListOf(
                    Place("Branner Hall", "Best dorm at Stanford", 37.426, -122.163, mutableListOf(
                        Uri.fromFile(File("src/main/res/drawable-v24/city01.jpg")), Uri.fromFile(File("src/main/res/drawable-v24/city02.jpg")))),
                    Place("Gates CS building", "Many long nights in this basement", 37.430, -122.173, mutableListOf(
                        Uri.fromFile(File("src/main/res/drawable-v24/city03.jpg")))),
                    Place("Pinkberry", "First date with my wife", 37.444, -122.170, mutableListOf())
                )
            ),
            UserSet("January vacation planning!",
                mutableListOf(
                    Place("Tokyo", "Overnight layover", 35.67, 139.65, mutableListOf()),
                    Place("Ranchi", "Family visit + wedding!", 23.34, 85.31, mutableListOf()),
                    Place("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82, mutableListOf())
                )),
            UserSet("Singapore travel itinerary",
                mutableListOf(
                    Place("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864, mutableListOf()),
                    Place("Jurong Bird Park", "Family-friendly park with many varieties of birds", 1.319, 103.706, mutableListOf()),
                    Place("Sentosa", "Island resort with panoramic views", 1.249, 103.830, mutableListOf()),
                    Place("Botanic Gardens", "One of the world's greatest tropical gardens", 1.3138, 103.8159, mutableListOf())
                )
            ),
            UserSet("My favorite places in the Midwest",
                mutableListOf(
                    Place("Chicago", "Urban center of the midwest, the \"Windy City\"", 41.878, -87.630, mutableListOf()),
                    Place("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134, mutableListOf()),
                    Place("Mackinaw City", "The entrance into the Upper Peninsula", 45.777, -84.727, mutableListOf()),
                    Place("Michigan State University", "Home to the Spartans", 42.701, -84.482, mutableListOf()),
                    Place("University of Michigan", "Home to the Wolverines", 42.278, -83.738, mutableListOf())
                )
            ),
            UserSet("Restaurants to try",
                mutableListOf(
                    Place("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941, mutableListOf()),
                    Place("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625, mutableListOf()),
                    Place("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422, mutableListOf()),
                    Place("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739, mutableListOf()),
                    Place("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635, mutableListOf())
                )
            )
        )
    }

    private fun enterNewMapName(): String {

        var titleToReturn = ""

        // box to fill data
        var newMapForm = LayoutInflater.from(this).inflate(R.layout.create_new_map, null)


        // alert window
        var dialog = AlertDialog
            .Builder(this)
            .setTitle("Describe memory!")
            .setView(newMapForm)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Add", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = newMapForm.findViewById<EditText>(R.id.newTitle).text.toString()
            if (title.trim().isEmpty()) {
                Toast.makeText(this, "Enter new map name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            titleToReturn = title
            dialog.dismiss()
        }

        return titleToReturn
    }
}
