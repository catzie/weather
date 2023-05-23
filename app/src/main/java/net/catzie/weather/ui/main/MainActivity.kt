package net.catzie.weather.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import net.catzie.weather.R
import net.catzie.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setUpViews()
        setUpTabbedLayout()

        setUpLocationClient()
        requestLocationPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Handle Location Permission Request Result
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            // Granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                retrieveDeviceLocation()
            }

            // Denied
            else {

                // Request weather data using default coords
                viewModel.getCurrentWeather()

                // Show Rationale, If We Should...
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    showNoPermissionAlertDialog()
                }
            }
        }
    }

    private fun setUpViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setUpTabbedLayout() {

        //Setup viewpager
        binding.viewPager.adapter = ViewPagerAdapter(this)

        //Setup tablayout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_current)
                1 -> getString(R.string.tab_history)
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()
    }

    private fun setUpLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun showNoPermissionAlertDialog() {

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.not_granted_title))
            .setMessage(getString(R.string.location_permission_rationale))
            .setPositiveButton("OK") { dialog, _ ->
                // Dismiss the dialog when the OK button is clicked
                dialog.dismiss()
            }
            .create()
            .show()

    }

    private fun requestLocationPermission() {

        // Android versions below Android 10 (Q)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
        // Android 10 (Q) and higher
        else {

            // Not Granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {

                // Should Show Rationale (explain why this permission is needed)
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    // Request weather data using default coords
                    viewModel.getCurrentWeather()

                    // Show rationale
                    showNoPermissionAlertDialog()
                }

                // Do Not Show Rationale, Just Request!
                else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }

            // Already Granted
            else {
                // Retrieve lat-lon
                retrieveDeviceLocation()
            }
        }
    }

    private fun retrieveDeviceLocation() {

        // Do not continue if permission is not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED

            &&

            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            return

        }

        // Proceed with location retrieval since we have permission
        else {

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

                // Location retrieved successfully
                location?.let {

                    // Request weather data
                    viewModel.getCurrentWeather(it)
                }
            }
                .addOnFailureListener { exception: Exception ->
                    // Handle the failure case
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_rationale),
                        Toast.LENGTH_LONG
                    ).show();
                }
        }
    }


    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}