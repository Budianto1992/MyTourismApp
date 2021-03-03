package com.budianto.mytourismapp.user.account.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.databinding.ActivityForgetPasswordBinding
import com.budianto.mytourismapp.shared.ValidatorService
import com.budianto.mytourismapp.view.ViewElement
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ForgetPasswordActivity : AppCompatActivity(), ViewElement {


    private lateinit var binding: ActivityForgetPasswordBinding
    private val passwordViewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
            val (isValid, error) = validatorService.isValidEmail(binding.edEmail.text.toString())
            if (isValid){
                passwordViewModel.startResetPassword(binding.edEmail.text.toString())
            } else{
                showMessage(error)
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        passwordViewModel.startResetPasswordState.observe(this, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> showMessage(R.string.changed_password_init)
                AccountState.INVALID_UPDATE -> showMessage(R.string.changed_password_init_error)
                else -> AccountState.UPDATING
            }
        })
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