package com.melvin.ongandroid.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.businesslogic.domain.OnRegister
import com.melvin.ongandroid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnRegister {


    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bundle = intent.extras
        val token = bundle?.getString("token")
        var sharedPrefences=token
        prefHelper = PrefHelper(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        when(sharedPrefences){
            "true" -> {
                binding.toolbar.visibility= View.VISIBLE
                binding.navView.visibility=View.VISIBLE
                navController.navigateUp()
                navController.navigate(R.id.homeFragment)

                Log.i("mensajee","pasa por true"+ sharedPrefences)
            }else ->{

                binding.toolbar.visibility= View.GONE
                binding.navView.visibility=View.GONE
                navController.navigateUp()
                navController.navigate(R.id.flowLogSign)


            Log.i("mensajee", "pasa por else"+sharedPrefences)
            }
        }

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
                    navController.navigate(R.id.signUpFragment)
                    true
                }
                R.id.nav_testimonios -> {
                    Toast.makeText(this, getString(R.string.nav_drawer_item_testimonios), Toast.LENGTH_SHORT).show()
                    //navController.navigateUp()
                    //navController.navigate(R.id.testimonialsFragment)
                    true
                }
                R.id.nav_act -> {
                    Toast.makeText(this, getString(R.string.title_actividades), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_contacts -> {
                    Toast.makeText(this, getString(R.string.title_contacto), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_nosotros -> {
                    Toast.makeText(this, getString(R.string.title_nosotros), Toast.LENGTH_SHORT).show()
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

    public fun saveSession(username: String, password: String){
        prefHelper.put( Constant.PREF_USERNAME, username )
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    public override fun onBackPressed() {
        finish()
    }

    override fun onClickRegister() {
        binding.toolbar.visibility= View.VISIBLE
        binding.navView.visibility=View.VISIBLE
    }


}

