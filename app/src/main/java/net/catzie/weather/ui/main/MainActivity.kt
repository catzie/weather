package net.catzie.weather.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setUpViews()
        setUpTabbedLayout()

        setUpLocationClient()
        requestLocationPermission()
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
            .setTitle("Location Permission Not Granted")
            .setMessage("Until Location permission is granted, we'll fetch weather data for Taguig City, PH.")
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
                    showNoPermissionAlertDialog()
                }

                // Do Not Show Rationale
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
                //todo Retrieve lat-lon
            }
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}