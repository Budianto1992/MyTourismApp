package com.budianto.mytourismapp.user.account.setting

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
import com.budianto.mytourismapp.databinding.FragmentSettingBinding
import com.budianto.mytourismapp.shared.ValidatorService
import com.budianto.mytourismapp.view.ViewElement
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SettingFragment : Fragment(), ViewElement {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModel()
    private val validatorService: ValidatorService by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAccount()

        observeViewModel()

        binding.saveButton.setOnClickListener {
            val (isValid, error) = validatorService.isValidEmail(binding.email.text.toString())
            if (isValid){
                showProgress()
                viewModel.updateAccount(
                        binding.username.text.toString(),
                        binding.email.text.toString(),
                        binding.firstName.text.toString(),
                        binding.lastName.text.toString()
                )
            } else{
                showMessage(error)
            }
            binding.root.hideKeyboard()
        }

    }

    private fun observeViewModel(){
        showProgress()
        viewModel.user.observe(viewLifecycleOwner, {
            binding.username.text = it.username
            binding.firstName.setText(it.firstName)
            binding.lastName.setText(it.lastName)
            binding.email.setText(it.email)
        })

        viewModel.updateAccountState.observe(viewLifecycleOwner, {
            when(it){
                AccountState.UPDATE_COMPLETED -> manageUpdateComplate()
                AccountState.INVALID_UPDATE -> showMessage(R.string.settings_saved_error)
                AccountState.UPDATING -> showMessage(R.string.updating_setting)
                else -> AccountState.UPDATING
            }
        })
        hideProgress()
    }

    private fun manageUpdateComplate(){
        showMessage(R.string.settings_save)
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