package com.budianto.mytourismapp.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.checkout.CheckoutActivity
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.databinding.ActivityDetailTourismBinding
import com.budianto.mytourismapp.user.account.setting.SettingViewModel
import com.budianto.mytourismapp.user.login.LoginActivity
import com.budianto.mytourismapp.util.ActivityHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_tourism.*
import kotlinx.android.synthetic.main.content_detail_tourism.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTourismActivity : AppCompatActivity() {

    private val detailTourismViewModel: DetailTourismViewModel by viewModel()
    private val settingViewModel: SettingViewModel by viewModel()
    private lateinit var binding: ActivityDetailTourismBinding

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailTourism = intent.getParcelableExtra<Tourism>(EXTRA_DATA)
        showDetailTourism(detailTourism)

        binding.content.btnLocation.setOnClickListener{
            moveToDestination(detailTourism)
        }

        binding.content.btnBooking.setOnClickListener {
            val user = settingViewModel.getAccount()
            statusAccount(user.equals(null))
        }
    }

    private fun statusAccount(account: Boolean){
        if (account){
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "You are at the checkout page please confirm payment", Toast.LENGTH_LONG).show()
        } else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "please sign in first", Toast.LENGTH_LONG).show()
        }
    }

    private fun showDetailTourism(detailTourism: Tourism?) {
        detailTourism?.let {
            supportActionBar?.title = detailTourism.name
            binding.content.tvDetailDescription.text = detailTourism.description
            Glide.with(this@DetailTourismActivity)
                .load(detailTourism.image)
                .into(binding.ivDetailImage)

            var statusFavorite = detailTourism.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailTourismViewModel.setFavoriteTourism(detailTourism, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white))
        }
    }

    private fun moveToDestination(detailTourism: Tourism?){
        val intent = Intent()
        intent.putExtra(ActivityHelper.Maps.EXTRA_MAP, detailTourism)
        intent.setClassName(ActivityHelper.PACKAGE_NAME, ActivityHelper.Maps.className)
        startActivity(intent)
    }
}