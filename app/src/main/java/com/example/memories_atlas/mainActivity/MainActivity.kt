package com.example.memories_atlas.mainActivity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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

private const val REQUEST_CODE = 1
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

        // sets adapter to setsView, put fake data (implementation below), pass onClick function
        sets = deserializeUserMaps(this).toMutableList()

        setAdapter = SetsAdapter(this, sets, object: SetsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {

                // open maps with  list of marks of selected set
                var intent = Intent(this@MainActivity, MapActivity::class.java)
                intent.putExtra(R.string.selected_map_set.toString(), sets[position])
                startActivityForResult(intent, REQUEST_CODE)
            }
        })
        setsRecyclerView.adapter = setAdapter

        // add new sets
        var addSetButton = findViewById<FloatingActionButton>(R.id.addSetButton)
        addSetButton.setOnClickListener {

            sets.add(UserSet(enterNewMapName(), emptyList()))
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
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val title = data?.getStringExtra("title")
            val newSet = data?.getSerializableExtra(title) as UserSet
            print(newSet)
            sets.remove(sets.find { set -> set.title == title })
            sets.add(newSet)
            setAdapter.notifyDataSetChanged()
            serializeUserMaps(this, sets)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getDataFile(context: Context) : File {

        return File(context.filesDir, FILENAME)
    }

    private fun serializeUserMaps(context: Context, userSets: List<UserSet>) {

        ObjectOutputStream(FileOutputStream(getDataFile(context))).use { it.writeObject(userSets) }
    }

    private fun deserializeUserMaps(context: Context) : List<UserSet> {

        val dataFile = getDataFile(context)
        if (!dataFile.exists()) {

            return generateSampleData()
        }

        ObjectInputStream(FileInputStream(dataFile)).use { return it.readObject() as List<UserSet> }
    }

    private fun print(set: UserSet ) {

        var str = set.title

        for (element in set.places) {

            str += " " + element.title + " " + element.longtitude + " " + element.latitude
        }
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()

    }

    // generate fake data
    private fun generateSampleData(): List<UserSet> {
        return listOf(
            UserSet(
                "Memories from University",
                listOf(
                    Place("Branner Hall", "Best dorm at Stanford", 37.426, -122.163, emptyList()),
                    Place("Gates CS building", "Many long nights in this basement", 37.430, -122.173, emptyList()),
                    Place("Pinkberry", "First date with my wife", 37.444, -122.170, emptyList())
                )
            ),
            UserSet("January vacation planning!",
                listOf(
                    Place("Tokyo", "Overnight layover", 35.67, 139.65, emptyList()),
                    Place("Ranchi", "Family visit + wedding!", 23.34, 85.31, emptyList()),
                    Place("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82, emptyList())
                )),
            UserSet("Singapore travel itinerary",
                listOf(
                    Place("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864, emptyList()),
                    Place("Jurong Bird Park", "Family-friendly park with many varieties of birds", 1.319, 103.706, emptyList()),
                    Place("Sentosa", "Island resort with panoramic views", 1.249, 103.830, emptyList()),
                    Place("Botanic Gardens", "One of the world's greatest tropical gardens", 1.3138, 103.8159, emptyList())
                )
            ),
            UserSet("My favorite places in the Midwest",
                listOf(
                    Place("Chicago", "Urban center of the midwest, the \"Windy City\"", 41.878, -87.630, emptyList()),
                    Place("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134, emptyList()),
                    Place("Mackinaw City", "The entrance into the Upper Peninsula", 45.777, -84.727, emptyList()),
                    Place("Michigan State University", "Home to the Spartans", 42.701, -84.482, emptyList()),
                    Place("University of Michigan", "Home to the Wolverines", 42.278, -83.738, emptyList())
                )
            ),
            UserSet("Restaurants to try",
                listOf(
                    Place("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941, emptyList()),
                    Place("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625, emptyList()),
                    Place("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422, emptyList()),
                    Place("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739, emptyList()),
                    Place("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635, emptyList())
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
