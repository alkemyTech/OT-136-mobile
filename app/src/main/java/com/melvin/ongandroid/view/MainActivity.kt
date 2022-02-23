package com.melvin.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.facebook.login.LoginManager
import com.firebase.ui.auth.AuthUI
import com.melvin.ongandroid.R
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
                    navController.navigateUp()
                    navController.navigate(R.id.homeFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_signout -> {
                    navController.navigateUp()
                    PrefHelper(this).clear()
                    LoginManager.getInstance().logOut()
                    AuthUI.getInstance().signOut(this)
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }
                R.id.nav_testimonios -> {
                    navController.navigateUp()
                    navController.navigate(R.id.testimonialsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_act -> {
                    navController.navigateUp()
                    navController.navigate(R.id.activitiesFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_contacts -> {
                    navController.navigateUp()
                    navController.navigate(R.id.contactFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_nosotros -> {
                    navController.navigateUp()
                    navController.navigate(R.id.weFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_nov -> {
                    navController.navigateUp()
                    navController.navigate(R.id.newsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
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

