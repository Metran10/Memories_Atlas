package com.example.memories_atlas.mainActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.memories_atlas.R

class EditSetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_set)

        val name = findViewById<EditText>(R.id.setName)
        val place = findViewById<EditText>(R.id.placeName)
        val description = findViewById<EditText>(R.id.description)
        val lat = findViewById<EditText>(R.id.lat_edit)
        val long = findViewById<EditText>(R.id.long_edit)


        val button = findViewById<Button>(R.id.set_confirm_changes)

        button.setOnClickListener {

            val data = Intent()
            data.putExtra("name", name.text.toString())
            data.putExtra("place", place.text.toString())
            data.putExtra("description", description.text.toString())
            data.putExtra("lat", lat.text.toString())
            data.putExtra("long", long.text.toString())

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }



    private fun get() {


    }



}