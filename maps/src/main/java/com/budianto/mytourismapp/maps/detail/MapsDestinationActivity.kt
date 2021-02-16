package com.budianto.mytourismapp.maps.detail

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.Toast
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.budianto.mytourismapp.maps.R
import com.budianto.mytourismapp.maps.databinding.ActivityMapsDestinationBinding
import com.budianto.mytourismapp.util.ActivityHelper
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import kotlinx.android.synthetic.main.activity_maps_destination.*
import retrofit2.Call
import retrofit2.Response


class MapsDestinationActivity : AppCompatActivity() {



    companion object {
        private const val ICON_ID_DETAIL = "ICON_ID_DETAIL"
    }

    private lateinit var mapboxMap: MapboxMap
    private lateinit var symbolManager: SymbolManager
    private lateinit var locationComponent: LocationComponent
    private lateinit var mylocation: LatLng
    private lateinit var permissionsManager: PermissionsManager
    private lateinit var navigationMapRoute: NavigationMapRoute
    private var currentRoute: DirectionsRoute? = null

    private lateinit var binding: ActivityMapsDestinationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        binding = ActivityMapsDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMapTourism = intent.getParcelableExtra<Tourism>(ActivityHelper.Maps.EXTRA_MAP)

        binding.mapViewDestination.onCreate(savedInstanceState)
        binding.mapViewDestination.getMapAsync { mapboxMap ->
            this.mapboxMap = mapboxMap
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
                symbolManager = SymbolManager(binding.mapViewDestination, mapboxMap, style)
                symbolManager.iconAllowOverlap = true

                style.addImage(
                    ICON_ID_DETAIL,
                    BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default)
                )

                navigationMapRoute = NavigationMapRoute(
                        null,
                        binding.mapViewDestination,
                        mapboxMap,
                        R.style.NavigationLocationLayerStyle
                )

                showMyLocation(style)
                showMarkerDetail(detailMapTourism)
                showNavigationToDestination()

                binding.fab.setOnClickListener{
                    showMyLocation(style)
                }
            }
        }
    }

    private fun showMarkerDetail(detailTourism: Tourism?){
        val locationDetailTourism = LatLng(detailTourism!!.latitude, detailTourism.longitude)
            symbolManager.create(
                    SymbolOptions()
                            .withLatLng(LatLng(locationDetailTourism.latitude, locationDetailTourism.longitude))
                            .withIconImage(ICON_ID_DETAIL)
                            .withIconSize(1.5f)
                            .withIconOffset(arrayOf(0f, -1.5f))
                            .withTextField(detailTourism.name)
                            .withTextHaloColor("rgba(255, 255, 255, 100)")
                            .withTextHaloWidth(5.0f)
                            .withTextAnchor("top")
                            .withTextOffset(arrayOf(0f, -1.5f))
                            .withDraggable(true)
            )

        val origin = Point.fromLngLat(mylocation.longitude, mylocation.latitude)
        val destination = Point.fromLngLat(locationDetailTourism.longitude, locationDetailTourism.latitude)
        requestRoute(origin, destination)

        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationDetailTourism, 10.0))
    }


    private fun requestRoute(origin: Point, destination: Point){
        navigationMapRoute.updateRouteVisibilityTo(false)
        NavigationRoute.builder(this)
                .accessToken(getString(R.string.mapbox_access_token))
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(object : retrofit2.Callback<DirectionsResponse>{
                    override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
                        if (response.body() == null) {
                            Toast.makeText(
                                    this@MapsDestinationActivity,
                                    "No routes found, make sure you set the right user and access token.",
                                    Toast.LENGTH_SHORT
                            ).show()
                            return
                        } else if (response.body()?.routes()?.size == 0) {
                            Toast.makeText(this@MapsDestinationActivity, "No routes found.", Toast.LENGTH_SHORT)
                                    .show()
                            return
                        }

                        currentRoute = response.body()?.routes()?.get(0)

                        navigationMapRoute.addRoute(currentRoute)
                    }

                    override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                        Toast.makeText(this@MapsDestinationActivity, "Error : $t", Toast.LENGTH_SHORT).show()
                    }
                })
    }


    private fun showNavigationToDestination(){
        btnNavigation.setOnClickListener {
            val simulateRoute = true

            val option = NavigationLauncherOptions.builder()
                    .directionsRoute(currentRoute)
                    .shouldSimulateRoute(simulateRoute)
                    .build()

            NavigationLauncher.startNavigation(this, option)
        }
    }


    @SuppressLint("MissingPermission")
    private fun showMyLocation(style: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            val locationComponentOptions = LocationComponentOptions.builder(this)
                .pulseEnabled(true)
                .pulseColor(Color.BLUE)
                .pulseAlpha(.4f)
                .pulseInterpolator(BounceInterpolator())
                .build()
            val locationComponentActivationOptions = LocationComponentActivationOptions
                .builder(this, style)
                .locationComponentOptions(locationComponentOptions)
                .build()
            locationComponent = mapboxMap.locationComponent
            locationComponent.activateLocationComponent(locationComponentActivationOptions)
            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS

            mylocation = LatLng(locationComponent.lastKnownLocation?.latitude as Double, locationComponent.lastKnownLocation?.longitude as Double)
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 12.0))

        } else {
            permissionsManager = PermissionsManager(object : PermissionsListener {
                override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
                    Toast.makeText(this@MapsDestinationActivity, "Anda harus mengizinkan location permission untuk menggunakan aplikasi ini", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionResult(granted: Boolean) {
                    if (granted) {
                        mapboxMap.getStyle { style ->
                            showMyLocation(style)
                        }
                    } else {
                        finish()
                    }
                }
            })
            permissionsManager.requestLocationPermissions(this)
        }
    }


    override fun onStart() {
        super.onStart()
        binding.mapViewDestination.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapViewDestination.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapViewDestination.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapViewDestination.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapViewDestination.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewDestination.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapViewDestination.onLowMemory()
    }
}