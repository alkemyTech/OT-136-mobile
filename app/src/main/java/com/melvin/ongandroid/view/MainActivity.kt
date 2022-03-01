package com.melvin.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = Firebase.auth.currentUser
        setPhoto(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        firebaseAuth.addAuthStateListener(this.firebaseAuthListener)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newsFragment,
                R.id.homeFragment,
                R.id.testimonialsFragment,
                R.id.activitiesFragment,
                R.id.contactFragment,
                R.id.weFragment
            ), binding.drawerLayout
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.navView.getMenu().getItem(0).setChecked(true)
        var itemMenu=0

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if(itemMenu==0){ binding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else{
                    navController.navigateUp()
                    navController.navigate(R.id.homeFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                        itemMenu=0}
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
                    if(itemMenu==3){ binding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else{
                    navController.navigateUp()
                    navController.navigate(R.id.testimonialsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                        itemMenu=3}
                    true
                }
                R.id.nav_act -> {
                    if(itemMenu==1){ binding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else{
                    navController.navigateUp()
                    navController.navigate(R.id.activitiesFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                        itemMenu=1}
                    true
                }
                R.id.nav_contacts -> {
                    if(itemMenu==5){ binding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else{
                    navController.navigateUp()
                    navController.navigate(R.id.contactFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                        itemMenu=5
                    }
                    true
                }
                R.id.nav_nosotros -> {
                    if(itemMenu==4){ binding.drawerLayout.closeDrawer(GravityCompat.START)
                    } else{
                        navController.navigateUp()
                        navController.navigate(R.id.weFragment)
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                        itemMenu=4
                    }
                    true
                }
                R.id.nav_nov -> {
                    if(binding.navView.menu.getItem(2).isChecked()==true){
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }else{
                    navController.navigateUp()
                    navController.navigate(R.id.newsFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                        itemMenu=2
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.homeFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

     fun refreshWeFragment() {
         navController.navigateUp()
         navController.navigate(R.id.weFragment)


     }

    private fun setPhoto(user: FirebaseUser?){
        user?.let{
        val photoUrl = user?.photoUrl
        Glide.with(this).load(photoUrl).centerCrop().into(binding.ivUser)
        binding.ivUser.visibility= View.VISIBLE
        }
    }

    fun refreshFr(){
        navController.navigate(R.id.homeFragment)
    }
}

