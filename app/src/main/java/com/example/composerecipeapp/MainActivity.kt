package com.example.composerecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.composerecipeapp.fragment.RecipeListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}