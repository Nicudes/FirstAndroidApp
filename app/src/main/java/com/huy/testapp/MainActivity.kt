package com.huy.testapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {
private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userlocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLocationPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //Uer has granted access -> get location
            getUserLocation()
        }
        else{
            Toast.makeText(this, "We must access your location", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkLocationPermission(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
            //Ask user for permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1333)


        }
        else{
            //Get users location
            getUserLocation()

        }

    }

    private fun getUserLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient
            .lastLocation
            .addOnSuccessListener {
                userlocation = it
            }
            .addOnFailureListener {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_LONG).show()
            }
    }
}
