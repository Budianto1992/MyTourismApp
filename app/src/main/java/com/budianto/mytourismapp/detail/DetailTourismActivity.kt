package com.budianto.mytourismapp.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.databinding.ActivityDetailTourismBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_tourism.*
import kotlinx.android.synthetic.main.content_detail_tourism.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTourismActivity : AppCompatActivity() {

    private val detailTourismViewModel: DetailTourismViewModel by viewModel()
    private var _binding: ActivityDetailTourismBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToMain.setOnClickListener {
            finish()
        }

        val detailTourism = intent.getParcelableExtra<Tourism>(EXTRA_DATA)
        showDetailTourism(detailTourism)
    }

    private fun showDetailTourism(detailTourism: Tourism?) {
        detailTourism?.let {
            binding.tvDetailTitle.text = detailTourism.name
            binding.tvDetailLocation.text = detailTourism.address
            binding.tvDetailDescription.text = detailTourism.description
            Glide.with(this@DetailTourismActivity)
                .load(detailTourism.image)
                .into(binding.textDetailImage)

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
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white))
        }
    }
}