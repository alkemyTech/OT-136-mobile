package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}