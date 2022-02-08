package com.melvin.ongandroid.businesslogic.vo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.melvin.ongandroid.businesslogic.data.Constant
import com.melvin.ongandroid.businesslogic.data.Constant.Companion.PREF_PASSWORD
import com.melvin.ongandroid.businesslogic.data.Constant.Companion.PREF_USERNAME
import com.melvin.ongandroid.businesslogic.data.Constant.Companion.log
import com.melvin.ongandroid.businesslogic.data.Constant.Companion.pass
import com.melvin.ongandroid.businesslogic.data.Constant.Companion.user
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.view.MainActivity



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
            Log.i("mensajee", "SI existe token")
            //TO HOMEFRAGMENT
        } else {
            Log.i("mensajee", "no existe token")
            save()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun StartSplash() {
        onStart()
        Thread.sleep(5000)

    }

    override fun onBackPressed() {
        finish()
    }

    fun save(){
       if (log=="true") {
           prefHelper.put(Constant.PREF_USERNAME, user)
           prefHelper.put(Constant.PREF_PASSWORD, pass)
           prefHelper.put(Constant.PREF_IS_LOGIN, true)
       }
    }



}




