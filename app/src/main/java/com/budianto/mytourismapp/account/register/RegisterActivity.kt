package com.budianto.mytourismapp.account.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.budianto.mytourismapp.account.AccountViewModel
import com.budianto.mytourismapp.account.login.LoginActivity
import com.budianto.mytourismapp.databinding.ActivityRegisterBinding
import com.budianto.mytourismapp.utils.KeyGlobal
import com.budianto.mytourismapp.utils.alertDialogDefaultTitle
import com.budianto.mytourismapp.utils.setDataPrefString
import com.google.gson.JsonObject
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val accountViewModel: AccountViewModel by viewModel()
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setInitUI()
        liveData()
    }

    private fun setInitUI(){

        binding.btnSubmitRegister.setOnClickListener {
            btnSubmitNewRegister()
        }
    }


    private fun validation(): Boolean{
        return when{
            binding.edtNameRegistrationBuyer.text.toString().isEmpty() ->{
                binding.edtNameRegistrationBuyer.apply {
                    requestFocus()
                    error = "Harus di isi"
                }
                false
            }

            binding.edtPhone.text.toString().isEmpty() -> {
                binding.edtPhone.apply {
                    requestFocus()
                    error = "Harus di isi"
                }
                false
            }

            binding.edtPassword.text.toString().isEmpty() -> {
                binding.edtPassword.apply {
                    requestFocus()
                    error = "Harus di isi"
                }
                false
            }

            else -> true
        }
    }

    private fun liveData(){
        accountViewModel.liveRegister.observe(this, {
            if (it.isSuccessful){
                if (it.code() == 201) {
                    setDataPrefString(this, KeyGlobal.EMAIL, binding.edtEmail.text.toString())
                    setDataPrefString(this, KeyGlobal.PASSWORD, binding.edtPassword.text.toString())
                    Toast.makeText(this, "Register is successfully", Toast.LENGTH_SHORT).show()
                    val startLogin = Intent(this, LoginActivity::class.java)
                    startActivity(startLogin)
                }
            } else{
                alertDialogDefaultTitle(this, it.code().toString(), "Email already is registered" )
            }
        })
    }

    private fun requestRegister(){
        val jsonObject = JsonObject()
        jsonObject.addProperty("first_name", binding.edtNameRegistrationBuyer.text.toString())
        jsonObject.addProperty("last_name", binding.edtLastName.text.toString())
        jsonObject.addProperty("address", binding.edtAddress.text.toString())
        jsonObject.addProperty("phone", binding.edtPhone.text.toString())
        jsonObject.addProperty("email", binding.edtEmail.text.toString())
        jsonObject.addProperty("password", binding.edtPassword.text.toString())
        accountViewModel.register(jsonObject)
    }

    private fun btnSubmitNewRegister(){
        if (validation()){
            if (binding.edtEmail.text.toString().isNotEmpty()){
                if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()){
                    Toast.makeText(this, "${binding.edtEmail} Email tidak valid", Toast.LENGTH_SHORT).show()
                } else{
                    requestRegister()
                }
            } else{
                requestRegister()
            }
        }
    }
}