package com.dmytrobilash.vrgsofttechtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmytrobilash.vrgsofttechtask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}