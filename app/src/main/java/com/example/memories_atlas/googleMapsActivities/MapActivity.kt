package com.example.memories_atlas.googleMapsActivities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.memories_atlas.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.memories_atlas.databinding.ActivityMapBinding
import com.example.memories_atlas.models.UserSet
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var userSet: UserSet
    private var markers: MutableList<Marker> = mutableListOf()

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

        TODO("return modified list and save in adapter")
        TODO("add database Room ")
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

        // add passed markers
        for (mark in userSet.places) {
            val latLng = LatLng(mark.latitude, mark.longtitude)
            boundsBuilder.include(latLng)
            markers.add(mMap.addMarker(MarkerOptions().position(latLng).title(mark.title).snippet(mark.description)))
        }

        // move the camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))

        //------------------------------------------------------------------------------------------
        // delete marker
        mMap.setOnInfoWindowClickListener {
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
            dialog.dismiss()
        }
    }
}