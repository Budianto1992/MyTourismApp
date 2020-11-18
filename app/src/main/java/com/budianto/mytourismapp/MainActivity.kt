package com.budianto.mytourismapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.budianto.mytourismapp.favorite.FavoriteFragment
import com.budianto.mytourismapp.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val SELECTED_MENU = "selected_menu"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSeleectedListener)

        if (savedInstanceState != null){
            savedInstanceState.getInt(SELECTED_MENU)
        } else {
            nav_view.selectedItemId = R.id.navigation_home
        }
    }

    private val onNavigationItemSeleectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.navigation_home -> {
                fragment = HomeFragment.newInstance()
            }
            R.id.navigation_maps -> {
                Toast.makeText(this, "Blank Data for maps", Toast.LENGTH_LONG).show()
            }
            R.id.navigation_favorite -> {
                fragment = FavoriteFragment.newInstance()
            }
        }

        if (fragment != null) {
            supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.frame_container, fragment).commit()
        }

        false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, nav_view.selectedItemId)
    }
}