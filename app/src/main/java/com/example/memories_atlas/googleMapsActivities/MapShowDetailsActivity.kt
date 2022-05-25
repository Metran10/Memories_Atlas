package com.example.memories_atlas.googleMapsActivities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memories_atlas.R
import com.example.memories_atlas.models.Place
import com.example.memories_atlas.models.UserSet
import java.util.ArrayList

class MapShowDetailsActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1002
    private val IMAGE_CAPATURE_CODE = 555
    private val IMAGE_FROM_GALERY_CODE = 999
    private var takenPhoto: Uri? = null
    private lateinit var photos: MutableList<String>
    private lateinit var customAdapter: MapGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_show_details)

        val passedName = intent.getStringExtra("title")
        val passedDescription = intent.getStringExtra("description")
        photos = intent.getStringArrayListExtra("photos") as MutableList<String>

        val name = findViewById<TextView>(R.id.title123)
        name.text = passedName
        val description = findViewById<TextView>(R.id.description123)
        description.text = passedDescription
        val addFromGalery = findViewById<Button>(R.id.add_from_galery)
        val addFromCamera = findViewById<Button>(R.id.add_from_camera)
        val save = findViewById<Button>(R.id.save)


        val recyclerView = findViewById<RecyclerView>(R.id.rec_view_gallery)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        customAdapter = MapGalleryAdapter(this, photos)
        recyclerView.adapter = customAdapter

        addFromGalery.setOnClickListener {
            getImageFromGallery()
            customAdapter.notifyDataSetChanged()
        }

        addFromCamera.setOnClickListener {
            takePhoto()
            customAdapter.notifyDataSetChanged()
        }

        save.setOnClickListener {

            val data = Intent()
            data.putExtra("title", passedName)
            data.putStringArrayListExtra("photos", ArrayList(photos))
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_FROM_GALERY_CODE)
    }

    private fun takePhoto() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_CODE)
        }
        else {
            openCamera()
        }
    }

    private fun openCamera() {

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")

        takenPhoto = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, takenPhoto)
        startActivityForResult(cameraIntent, IMAGE_CAPATURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPATURE_CODE) {

            photos.add(takenPhoto.toString())
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_FROM_GALERY_CODE) {

            var imageUri = data?.data
            photos.add(imageUri.toString())
        }

        customAdapter.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}