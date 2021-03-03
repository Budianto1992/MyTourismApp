package com.budianto.mytourismapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.budianto.mytourismapp.core.domain.model.state.RegistrationState
import com.budianto.mytourismapp.databinding.ActivityMainBinding
import com.budianto.mytourismapp.favorite.FavoriteFragment
import com.budianto.mytourismapp.home.HomeFragment
import com.budianto.mytourismapp.user.account.password.ChangePasswordFragment
import com.budianto.mytourismapp.user.account.password.NewPasswordActivity
import com.budianto.mytourismapp.user.account.setting.SettingFragment
import com.budianto.mytourismapp.user.login.LoginActivity
import com.budianto.mytourismapp.user.login.LoginViewModel
import com.budianto.mytourismapp.user.login.LogoutState
import com.budianto.mytourismapp.user.register.RegisterViewModel
import com.budianto.mytourismapp.util.ActivityHelper
import com.budianto.mytourismapp.view.ViewElement
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import timber.log.Timber


private const val ACTIVATE_ACCOUNT_PATH = "activate"
private const val RESET_ACCOUNT_PATH = "reset"
private const val PARAMETER_NAME = "key"

class MainActivity : AppCompatActivity(), ViewElement {

    companion object{
        const val SELECTED_MENU = "selected_menu"
    }

    private lateinit var binding: ActivityMainBinding

    private val loginViewModel: LoginViewModel by viewModel()
    private val registerViewModel: RegisterViewModel by viewModel()


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

        handleIntentData()
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
            R.id.menu_account ->{
                showUserAccountPopup(binding.navView)
            }
        }

        false
    }

    private fun showUserAccountPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_account)
        popup.gravity = Gravity.RIGHT

        popup.show()

        handleNavigationAccountPopup(popup)
    }

    private fun handleNavigationAccountPopup(popupMenu: PopupMenu){
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.settings ->{
                    navigationChange(SettingFragment())
                    true
                }
                R.id.password ->{
                    navigationChange(ChangePasswordFragment())
                    true
                }

                R.id.signOut ->{
                    signOutUser()
                    navigationChange(HomeFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun signOutUser(){
        loginViewModel.signOut()

        loginViewModel.logoutState.observe(this, { state->
            if (LogoutState.LOGOUT_COMPLATE == state){
                showMessage(R.string.logout_successful)
            } else {
                showMessage(R.string.error_logout)
            }
        })
    }


    private fun handleIntentData(){
        val data: Uri? = this.intent.data

        if (data != null && data.isHierarchical){
            val uri = this.intent.dataString
            val activateKey = this.intent.dataString?.toHttpUrlOrNull()?.queryParameter(PARAMETER_NAME)
            val secondPath = this.intent.dataString?.toHttpUrlOrNull()?.pathSegments?.get(1)

            Timber.i("Deep link clicked: $uri")
            Timber.i("Activate Key: $activateKey")
            Timber.i("Second Path: $secondPath")

            if (secondPath == ACTIVATE_ACCOUNT_PATH) activateUserAccount(activateKey)
            else if (secondPath == RESET_ACCOUNT_PATH) resetUserPassword(activateKey)
        }
    }

    private fun activateUserAccount(activateKey: String?) {
        activateKey?.let {
            registerViewModel.activateAccount(it)
        }

        registerViewModel.registrationState.observe(this, { registrationResult ->
            if (registrationResult == RegistrationState.ACTIVATION_COMPLETED) {
                Toast.makeText(this, R.string.user_account_activate, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else if (registrationResult == RegistrationState.INVALID_ACTIVATION) {
                Toast.makeText(this, R.string.user_account_error, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetUserPassword(key: String?) {
        val bundle = bundleOf("key" to key)
        val intent = Intent(this, NewPasswordActivity::class.java)
        intent.putExtra(NewPasswordActivity.EXTRA_KEY, bundle)
        startActivity(intent)
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

    override fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }
}