package com.melvin.ongandroid.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.Constant
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    lateinit var prefHelper: PrefHelper
    val PREF_IS_LOGIN = "PREF_IS_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        StartSplash()
        super.onCreate(savedInstanceState)
        prefHelper = PrefHelper(this)
        CheckToken()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        //navController = findNavController(R.id.nav_host_fragment)
        //NavigationUI.setupActionBarWithNavController(this, navController)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_login -> {
                    Toast.makeText(this, getString(R.string.title_intro), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.loginFragment)

                    true
                }
                R.id.nav_signin -> {
                    Toast.makeText(this, getString(R.string.title_sign_up), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                    navController.navigate(R.id.signUpFragment)

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


    fun saveSession(email: String, password: String){
        prefHelper.put( Constant.PREFS_EMAIL, email )
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put(Constant.PREF_IS_LOGIN, true)
    }

    fun StartSplash(){
        Thread.sleep(5000)
        setTheme(R.style.Theme_ONGAndroid)
        Toast.makeText(this,"Timer has finished", Toast.LENGTH_SHORT).show()
    }

     fun CheckToken() {
        if (prefHelper.getBoolean( Constant.PREF_IS_LOGIN )) {
          /*  val Hfragment = HomeFragment()
            val fragment : Fragment?= supportFragmentManager.findFragmentByTag(HomeFragment::class.java.name)
            if (fragment !is HomeFragment){
               supportFragmentManager.beginTransaction()
                .add(R.id.nav_view,Hfragment,HomeFragment::class.java.name)
                   .commit()
           }*/
            Log.i("mensajee","existe token")
        }else {
            Log.i("mensajee","no existe token")
        }
        }

    }

