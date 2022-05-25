package com.example.memories_atlas.mainActivity

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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
    private var LOCALIZATION_PERMISSION_CODE = 101
    private var CAMERA_PERMISSION_CODE = 1000
    private var IMAGE_CAPATURE_CODE = 1001
    var image_uri: Uri? = null
    private lateinit var add_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_set)

        val name = findViewById<EditText>(R.id.setName)
        val place = findViewById<EditText>(R.id.placeName)
        val description = findViewById<EditText>(R.id.description)
        val lat = findViewById<EditText>(R.id.lat_edit)
        val long = findViewById<EditText>(R.id.long_edit)
        val button = findViewById<Button>(R.id.set_confirm_changes)
        add_button = findViewById(R.id.add_button)

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

        add_button.setOnClickListener {
            takePhoto()
        }

        button.setOnClickListener {

            val data = Intent()
            data.putExtra("name", name.text.toString())
            data.putExtra("place", place.text.toString())
            data.putExtra("description", description.text.toString())
            data.putExtra("lat", lat.text.toString())
            data.putExtra("long", long.text.toString())
            data.putExtra("index", index.toString())
            data.putExtra("uri", image_uri.toString())

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun takePhoto() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permissions, LOCALIZATION_PERMISSION_CODE)


            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), CAMERA_PERMISSION_CODE)
        }
        else {
            openCamera()
        }
    }

    private fun openCamera() {

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")

        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPATURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            add_button.background = getDrawable(R.drawable.tick_sign)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun findLocation(){

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
    }

    private fun fetchLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCALIZATION_PERMISSION_CODE)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
                else {
                    Toast.makeText(this, "No permissions to add photo", Toast.LENGTH_SHORT).show()
                }
            }
            LOCALIZATION_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                }
                else {
                    Toast.makeText(this, "No permissions to take localization", Toast.LENGTH_SHORT).show()
                }
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}