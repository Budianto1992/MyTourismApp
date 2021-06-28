package com.budianto.mytourismapp.account.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.budianto.mytourismapp.MainActivity
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.account.AccountViewModel
import com.budianto.mytourismapp.account.register.RegisterActivity
import com.budianto.mytourismapp.databinding.ActivityLoginBinding
import com.budianto.mytourismapp.utils.KeyGlobal
import com.budianto.mytourismapp.utils.alertDialogBiasa
import com.budianto.mytourismapp.utils.setDataPrefString
import com.google.gson.JsonObject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressDialog: ProgressDialog

    private val accountViewModel: AccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Silahkan Tunggu..")

        initView()
        liveData()
    }


    private fun initView(){
        binding.closeLogin.setOnClickListener {
            finish()
        }
        binding.btnDaftar.setOnClickListener(this)

        binding.btnLogin.setOnClickListener(this)
    }

    private fun validation(): Boolean{
        return when{
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

        accountViewModel.isLoading.observe(this) {
            if (it) progressDialog.show() else progressDialog.dismiss()
        }
        accountViewModel.throwableLive.observe(this) {
            alertDialogBiasa(this, it.message)
        }
        accountViewModel.liveLogin.observe(this) {
            if (it.isSuccessful) {
                setDataPrefString(this, KeyGlobal.EMAIL, binding.edtEmail.text.toString())
                setDataPrefString(this, KeyGlobal.PASSWORD, binding.edtPassword.text.toString())
                Toast.makeText(this, "Login is successfully", Toast.LENGTH_SHORT).show()
                val startLogin = Intent(this, MainActivity::class.java)
                startActivity(startLogin)
            } else {
                alertDialogBiasa(this, it.body()?.error_message)
            }
        }
    }

    private fun requestLogin(){
        val jsonObject = JsonObject()
        jsonObject.addProperty("email", binding.edtEmail.text.toString())
        jsonObject.addProperty("password", binding.edtPassword.text.toString())
        accountViewModel.login(jsonObject)
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.btnDaftar -> {
                val startRegisterActivity = Intent(this, RegisterActivity::class.java)
                startActivity(startRegisterActivity)
            }

            R.id.btnLogin ->{
                if (validation()){
                    if (binding.edtEmail.text.toString().isNotEmpty()){
                        if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.text.toString()).matches()){
                            Toast.makeText(this, "${binding.edtEmail} Email tidak valid", Toast.LENGTH_SHORT).show()
                        } else{
                            requestLogin()
                        }
                    } else{
                        requestLogin()
                    }
                }
            }
        }
    }
}