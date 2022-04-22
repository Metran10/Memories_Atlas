package com.example.memories_atlas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        supportActionBar?.hide()
    }
}