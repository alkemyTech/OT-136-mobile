package com.melvin.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.businesslogic.domain.OnRegister
import com.melvin.ongandroid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnRegister {


    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, getString(R.string.title_intro), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.nav_signout -> {
                    Toast.makeText(this, getString(R.string.title_sign_out), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    PrefHelper(this).clear()
                    LoginManager.getInstance().logOut()
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }
                R.id.nav_testimonios -> {
                    Toast.makeText(this, getString(R.string.nav_drawer_item_testimonios), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.testimonialsFragment)
                    true
                }
                R.id.nav_act -> {
                    Toast.makeText(this, getString(R.string.title_actividades), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.activitiesFragment)
                    true
                }
                R.id.nav_contacts -> {
                    Toast.makeText(this, getString(R.string.title_contacto), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.contactFragment)
                    true
                }
                R.id.nav_nosotros -> {
                    Toast.makeText(this, getString(R.string.title_nosotros), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.weFragment)
                    true
                }
                R.id.nav_nov -> {
                    Toast.makeText(this, getString(R.string.title_novedades), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.newsFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    override fun onBackPressed() {
        finish()
    }
     fun refreshWeFragment() {
         navController.navigateUp()
         navController.navigate(R.id.weFragment)

     }

    override fun onClickRegister() {

    }
    fun refreshFr(){
        navController.navigate(R.id.homeFragment)
    }
}

