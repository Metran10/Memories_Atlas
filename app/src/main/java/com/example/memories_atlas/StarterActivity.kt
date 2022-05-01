package com.example.memories_atlas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.memories_atlas.mainActivity.MainActivity

class StarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        supportActionBar?.hide()

        // browse button open MainActivity
        val browseButton = findViewById<Button>(R.id.browse_button)
        browseButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}