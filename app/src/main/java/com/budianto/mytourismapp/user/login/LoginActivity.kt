package com.budianto.mytourismapp.user.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.budianto.mytourismapp.MainActivity
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.core.domain.model.state.AuthenticationState
import com.budianto.mytourismapp.databinding.ActivityLoginBinding
import com.budianto.mytourismapp.shared.SharedViewModel
import com.budianto.mytourismapp.user.account.ForgetPasswordActivity
import com.budianto.mytourismapp.user.register.RegisterActivity
import com.budianto.mytourismapp.view.ViewElement
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity(), ViewElement {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var bindingMain: MainActivity
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginViewModel.authentication(binding.edUsername.text.toString(), binding.edPassword.text.toString(), binding.rememberMe.isActivated)
        }

        binding.btnResetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }


        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        showProgress()
        loginViewModel.authenticationState.observe(this, { state ->
            when(state){
                AuthenticationState.AUTHENTICATED -> authenticatedUser(R.string.successful_authentication)
                AuthenticationState.INVALID_AUTHENTICATION -> showMessage(R.string.invalid_authentication)
                AuthenticationState.UNAUTHENTICATED -> bindingMain.moveTaskToBack(true)
                else -> AuthenticationState.UNAUTHENTICATED
            }
        })
    }


    override fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        binding.progressBar.visibility
    }

    override fun hideProgress() {
        binding.progressBar.isInvisible
    }

    private fun authenticatedUser(message: Int){
        showMessage(message)
        sharedViewModel.setUserName(binding.edUsername.text.toString())
        binding.root.hideKeyboard()
        bindingMain.onBackPressed()
    }

    private fun View.hideKeyboard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
