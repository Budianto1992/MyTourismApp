package com.budianto.mytourismapp.user.account.password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import com.budianto.mytourismapp.MainActivity
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.databinding.ActivityNewPasswordBinding
import com.budianto.mytourismapp.shared.ValidatorService
import com.budianto.mytourismapp.view.ViewElement
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class NewPasswordActivity : AppCompatActivity(), ViewElement {

    companion object{
        const val EXTRA_KEY = "key"
    }

    private lateinit var binding: ActivityNewPasswordBinding
    private val newPasswordViewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tokenKey = savedInstanceState?.getString("key")

        binding.btnValidatePassword.setOnClickListener {
            val (isValid, error) = validatorService.isValidPassword(binding.edNewPassword.text.toString(),
                                                                    binding.edCurrentPassword.text.toString())
            if (isValid){
                newPasswordViewModel.finishResetPassword(tokenKey.toString(), binding.edNewPassword.text.toString())
            } else{
                showMessage(error)
            }
        }

        observerViewModel()
    }

    private fun observerViewModel(){
        newPasswordViewModel.finishResetPasswordState.observe(this, { passwordState->
            when(passwordState){
                AccountState.UPDATE_COMPLETED -> handleChangePassword()
                AccountState.INVALID_UPDATE -> showMessage(R.string.changed_password_init_error)
                AccountState.UPDATING -> showMessage(R.string.updating_password)
                else -> AccountState.INVALID_UPDATE
            }
        })
    }

    private fun handleChangePassword(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        binding.progressBar.visibility
    }

    override fun hideProgress() {
        binding.progressBar.isInvisible
    }
}