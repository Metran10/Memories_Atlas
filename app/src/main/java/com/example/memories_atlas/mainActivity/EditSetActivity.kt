package com.example.memories_atlas.mainActivity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.memories_atlas.R
import com.example.memories_atlas.models.UserSet
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class EditSetActivity : AppCompatActivity() {

    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private var permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_set)

        val name = findViewById<EditText>(R.id.setName)
        val place = findViewById<EditText>(R.id.placeName)
        val description = findViewById<EditText>(R.id.description)
        val lat = findViewById<EditText>(R.id.lat_edit)
        val long = findViewById<EditText>(R.id.long_edit)
        val button = findViewById<Button>(R.id.set_confirm_changes)

        val userSet = intent.getSerializableExtra(R.string.selected_map_set.toString()) as UserSet?
        val index = intent.getSerializableExtra("index") as Int

        if (userSet != null) {
            name.setText(userSet.title)
            if (userSet.places.size > 0) {
                place.setText(userSet.places[0].title)
                place.isEnabled = false
                description.setText(userSet.places[0].description)
                description.isEnabled = false
                long.setText(userSet.places[0].longtitude.toString())
                long.isEnabled = false
                lat.setText(userSet.places[0].latitude.toString())
                lat.isEnabled = false
            }
        }
        else {
            findLocation()
        }

        button.setOnClickListener {

            val data = Intent()
            data.putExtra("name", name.text.toString())
            data.putExtra("place", place.text.toString())
            data.putExtra("description", description.text.toString())
            data.putExtra("lat", lat.text.toString())
            data.putExtra("long", long.text.toString())
            data.putExtra("index", index.toString())

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun findLocation(){

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
    }

    private fun fetchLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        val task = fusedLocationProvider.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(this, "Your current location is: \n" +
                        "Long: " + it.longitude + " \n" +
                        "Lat: " + it.latitude, Toast.LENGTH_LONG).show()
            }
        }
    }
}