package com.melvin.ongandroid.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.businesslogic.data.PrefHelper


class SplashActivity  : AppCompatActivity() {
    lateinit var prefHelper: PrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefHelper = PrefHelper(this)
        StartSplash()
        onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        if (prefHelper.getBoolean(Constant.PREF_IS_LOGIN)) {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("token",true.toString())
            startActivity(intent)
        } else {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("token",false.toString())
            startActivity(intent)
        }
    }

    fun StartSplash() {
        onStart()
        Thread.sleep(5000)
    }

}




