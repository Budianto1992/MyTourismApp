package com.budianto.mytourismapp.user.account.password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isInvisible
import com.budianto.mytourismapp.MainActivity
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.core.domain.model.state.AccountState
import com.budianto.mytourismapp.databinding.FragmentChangePasswordBinding
import com.budianto.mytourismapp.shared.ValidatorService
import com.budianto.mytourismapp.view.ViewElement
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment(), ViewElement {

    private var _binding : FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PasswordViewModel by viewModel()
    private val validatorService: ValidatorService by inject()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val (isValid, error) =validatorService.isValidPassword(binding.newPassword.text.toString(),
                                                                    binding.newPasswordConfirmation.text.toString())

            if (isValid){
                viewModel.updatePassword(
                    binding.currentPassword.text.toString(),
                    binding.newPassword.text.toString()
                )
            } else{
                showMessage(error)
            }

            binding.root.hideKeyboard()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.updatePasswordState.observe(viewLifecycleOwner, {
            when (it) {
                AccountState.UPDATE_COMPLETED -> handleUpdateComplete()
                AccountState.INVALID_UPDATE -> showMessage(R.string.incorrect_password)
                AccountState.UPDATING -> showMessage(R.string.updating_password)
                else ->  AccountState.INVALID_UPDATE
            }
        })
    }

    private fun handleUpdateComplete() {
        showMessage(R.string.password_change)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        binding.progressBar.visibility
    }

    override fun hideProgress() {
        binding.progressBar.isInvisible
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}