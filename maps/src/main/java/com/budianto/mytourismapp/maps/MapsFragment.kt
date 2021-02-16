package com.budianto.mytourismapp.maps

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.budianto.mytourismapp.core.data.Resource
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.detail.DetailTourismActivity
import com.budianto.mytourismapp.maps.databinding.FragmentMapsBinding
import com.google.gson.Gson
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MapsFragment : Fragment() {


    companion object{
        private const val ICON_ID = "ICON_ID"
    }

    private lateinit var symbolManager: SymbolManager

    private lateinit var binding: FragmentMapsBinding

    private lateinit var mapBoxMap: MapboxMap
    private val mapViewModel: MapsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Mapbox.getInstance(requireActivity(), getString(R.string.mapbox_access_token))
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadKoinModules(mapsModule)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync { mapBoxMap ->
            this.mapBoxMap = mapBoxMap
            mapBoxMap.setStyle(Style.MAPBOX_STREETS){ style->
                symbolManager = SymbolManager(mapView, mapBoxMap, style)
                symbolManager.iconAllowOverlap = true

                style.addImage(
                        ICON_ID,
                        BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default)
                )

                getTourismData()
            }
        }
    }


    private fun getTourismData(){
        mapViewModel.tourism.observe(this, { tourism ->
            if (tourism != null) {
                when (tourism) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showMarker(tourism.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = tourism.message
                    }
                }
            }
        })
    }

    private fun showMarker(tourismData: List<Tourism>?) {
        mapBoxMap.setStyle(Style.MAPBOX_STREETS) { style ->
            style.addImage(ICON_ID, BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default))
            val latLngBoundsBuilder = LatLngBounds.Builder()

            val symbolManager = SymbolManager(binding.mapView, mapBoxMap, style)
            symbolManager.iconAllowOverlap = true
            val options = ArrayList<SymbolOptions>()
            tourismData?.forEach { data ->
                latLngBoundsBuilder.include(LatLng(data.latitude, data.longitude))
                options.add(
                        SymbolOptions()
                                .withLatLng(LatLng(data.latitude, data.longitude))
                                .withIconImage(ICON_ID)
                                .withData(Gson().toJsonTree(data))
                )
            }
            symbolManager.create(options)
            val latLngBounds = latLngBoundsBuilder.build()
            mapBoxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000)
            symbolManager.addClickListener {
                val data = Gson().fromJson(it.data, Tourism::class.java)
                val intent = Intent(context, DetailTourismActivity::class.java)
                intent.putExtra(DetailTourismActivity.EXTRA_DATA, data)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}