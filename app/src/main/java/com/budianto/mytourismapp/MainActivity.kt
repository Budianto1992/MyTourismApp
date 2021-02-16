package com.budianto.mytourismapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.budianto.mytourismapp.databinding.ActivityMainBinding
import com.budianto.mytourismapp.favorite.FavoriteFragment
import com.budianto.mytourismapp.home.HomeFragment
import com.budianto.mytourismapp.user.login.LoginActivity
import com.budianto.mytourismapp.util.ActivityHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object{
        const val SELECTED_MENU = "selected_menu"
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSeleectedListener)

        if (savedInstanceState != null){
            savedInstanceState.getInt(SELECTED_MENU)
        } else {
            binding.navView.selectedItemId = R.id.navigation_home
        }
    }

    private val onNavigationItemSeleectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                navigationChange(HomeFragment())
            }
            R.id.navigation_maps -> {
                moveToMapsFragment()
            }
            R.id.navigation_favorite -> {
                navigationChange(FavoriteFragment())
            }
        }

        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.login) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun moveToMapsFragment() {
        val fragment = instantiateFragment()
        if (fragment != null) {
            navigationChange(fragment)
        }
    }

    private fun instantiateFragment(): Fragment? {
        return try {
            Class.forName(ActivityHelper.Maps.classMapsFragment).newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun navigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, binding.navView.selectedItemId)
    }
}