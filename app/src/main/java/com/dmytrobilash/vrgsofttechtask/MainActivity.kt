package com.dmytrobilash.vrgsofttechtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmytrobilash.vrgsofttechtask.databinding.ActivityMainBinding
import com.dmytrobilash.vrgsofttechtask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}