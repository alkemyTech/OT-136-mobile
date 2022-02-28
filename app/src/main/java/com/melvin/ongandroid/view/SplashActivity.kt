package com.melvin.ongandroid.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.businesslogic.data.PrefHelper


class SplashActivity  : AppCompatActivity() {
    lateinit var prefHelper: PrefHelper
    private val authGoogle:FirebaseAuth by lazy {FirebaseAuth.getInstance()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefHelper = PrefHelper(this)
        StartSplash()
        onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        val isLoggedInFB = AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired
        if (prefHelper.getBoolean(Constant.PREF_IS_LOGIN ) || authGoogle.currentUser!=null || isLoggedInFB ) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun StartSplash() {
        onStart()
        Thread.sleep(5000)
    }

}




