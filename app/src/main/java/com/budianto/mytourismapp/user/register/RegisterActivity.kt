package com.budianto.mytourismapp.user.register

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
import com.budianto.mytourismapp.core.domain.model.state.RegistrationState
import com.budianto.mytourismapp.databinding.ActivityRegisterBinding
import com.budianto.mytourismapp.shared.ValidatorService
import com.budianto.mytourismapp.view.ViewElement
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

private const val NO_ERROR_MESSAGE = 0

class RegisterActivity : AppCompatActivity(), ViewElement {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val (isValid, error) = validate(
                binding.edUsername.text.toString(),
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString(),
                binding.edConfirmPassword.text.toString()
            )

            if (isValid){
                viewModel.createAccount(
                    binding.edUsername.text.toString(),
                    binding.edEmail.text.toString(),
                    binding.edPassword.text.toString()
                )
            } else{
                showMessage(error)
            }

            observerViewModel()
        }

        binding.root.hideKeyboard()

    }


    private fun observerViewModel(){
        showProgress()
        viewModel.registrationState.observe(this, { state->
            if (state == RegistrationState.REGISTRATION_COMPLETED){
                showMessage(R.string.successful_registration)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun validate(username: String, email: String, password: String, passwordConfrm: String): Pair<Boolean, Int>{
        val (isValidUsername, usernameErrorMessage) = validatorService.isValidUsername(username)
        if (!isValidUsername) return Pair(isValidUsername, usernameErrorMessage)

        val (isValidEmail, emailErrorMessage) = validatorService.isValidEmail(email)
        if (!isValidEmail) return Pair(isValidEmail, emailErrorMessage)

        val (isValidPassword, passwordErrorMessage) = validatorService.isValidPassword(password, passwordConfrm)
        if (!isValidPassword) return Pair(isValidPassword, passwordErrorMessage)

        return Pair(true, NO_ERROR_MESSAGE)
    }


    private fun View.hideKeyboard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        binding.progressBarReg.visibility
    }

    override fun hideProgress() {
        binding.progressBarReg.isInvisible
    }
}