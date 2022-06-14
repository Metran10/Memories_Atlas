package com.example.memories_atlas.googleMapsActivities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.media.ExifInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.memories_atlas.R
import com.example.memories_atlas.databinding.ActivityMapBinding
import com.example.memories_atlas.models.UserSet
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.memories_atlas.models.Place
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var userSet: UserSet
    private var photos: MutableMap<String, MutableList<String>> = mutableMapOf()
    private var markers: MutableList<Marker> = mutableListOf()
    private var RETURN_CODE = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSet = intent.getSerializableExtra(R.string.selected_map_set.toString()) as UserSet

        supportActionBar?.title = userSet.title

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // instruction for user
        mapFragment.view?.let {
            Snackbar.make(it, "Hold place to add pin, tap pin twice to delete", Snackbar.LENGTH_LONG)
                .setAction("OK",  {})
                .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.save_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.miSave) {

            if (markers.isEmpty()) {
                Toast.makeText(this, "Enter at least 1 place", Toast.LENGTH_SHORT).show()
                return true
            }

            val places = mutableListOf<Place>()
            for (marker in markers) {
                places.add(Place(marker.title, marker.snippet, marker.position.latitude, marker.position.longitude,
                    photos[marker.title]!!))
            }

            val userMap = UserSet(userSet.title, places)
            val data = Intent()
            data.putExtra(userSet.title, userMap)
            data.putExtra("title", userSet.title)
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun printMarkers() {

        var str = ""
        for (marker in markers) {
            str += marker.title + " " + marker.snippet + " " + marker.position.longitude + " " + marker.position.latitude + "\n"
        }
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

    private fun printPlaces(places: MutableList<Place>) {

        var str = ""
        for (place in places) {
            str += place.title + " " + place.description + " " + place.longtitude + " " + place.latitude + "\n"
        }
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var boundsBuilder = LatLngBounds.builder()

        // add passed places
        for (mark in userSet.places) {
            val latLng = LatLng(mark.latitude, mark.longtitude)
            boundsBuilder.include(latLng)
            var marker = mMap.addMarker(MarkerOptions().position(latLng).title(mark.title).snippet(mark.description))
            markers.add(marker)
            photos[mark.title] = mark.photos
        }

        // move the camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))

        // delete marker
        mMap.setOnInfoWindowClickListener {
            selectedMarker ->
            var intent = Intent(this, MapShowDetailsActivity::class.java)
            var photosOfPlace = photos[selectedMarker.title]

            intent.putExtra("title", selectedMarker.title)
            intent.putExtra("description", selectedMarker.snippet)
            intent.putStringArrayListExtra("photos", ArrayList(photosOfPlace))
            startActivityForResult(intent, RETURN_CODE)
        }

        mMap.setOnInfoWindowLongClickListener {
            toDelete ->
            markers.remove(toDelete)
            toDelete.remove()
        }

        // add marker
        mMap.setOnMapLongClickListener {
            latLng ->
            setParams(latLng)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            //here its gathering ####################################################################################
            val title = data?.getStringExtra("title").toString()
            val pics = data?.getStringArrayListExtra("photos") as MutableList<String>

            photos[title] = pics
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setParams(latLng: LatLng) {

        // box to fill data
        var form = LayoutInflater.from(this).inflate(R.layout.create_pin, null)


        // alert window
        var dialog = AlertDialog
            .Builder(this)
            .setTitle("Describe memory!")
            .setView(form)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Add", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = form.findViewById<EditText>(R.id.titleInput).text.toString()
            val description = form.findViewById<EditText>(R.id.descriptionInput).text.toString()
            if (title.trim().isEmpty() || description.trim().isEmpty()) {
                Toast.makeText(this, "Describe your memory!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            markers.add(mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description)))
            photos[markers[markers.size-1].title] = mutableListOf()
            dialog.dismiss()
        }
    }
}