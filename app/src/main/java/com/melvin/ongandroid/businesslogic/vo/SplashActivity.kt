package com.melvin.ongandroid.businesslogic.vo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.melvin.ongandroid.businesslogic.data.Constant
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.databinding.ActivityMainBinding
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.view.HomeFragment
import com.melvin.ongandroid.view.LoginFragment
import com.melvin.ongandroid.view.MainActivity
import androidx.fragment.app.FragmentTransaction


class SplashActivity : AppCompatActivity(){
    lateinit var prefHelper: PrefHelper
    val PREF_IS_LOGIN = "PREF_IS_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefHelper = PrefHelper(this)
        StartSplash()
        onBackPressed()


    }
    fun StartSplash(){
        CheckToken()
        Thread.sleep(5000)
        //startActivity(Intent(this, MainActivity::class.java))

    }
    override fun onBackPressed() {
        finish()
    }
    fun CheckToken() {
        if (prefHelper.getBoolean(Constant.PREF_IS_LOGIN )) {
            Log.i("mensajee","existe token"+Constant.PREF_IS_LOGIN + Constant.PREFS_EMAIL + Constant.PREF_PASSWORD)
        }else {
            Log.i("mensajee","no existe token"+Constant.PREF_IS_LOGIN.toString())
        }
    }
    fun  saveSession(email: String, password: String){
        prefHelper.put( Constant.PREFS_EMAIL, email )
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put(Constant.PREF_IS_LOGIN, true)
    }





}