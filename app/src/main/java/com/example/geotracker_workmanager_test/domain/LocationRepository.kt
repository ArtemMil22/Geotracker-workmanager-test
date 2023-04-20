@file:Suppress("DEPRECATION")

package com.example.geotracker_workmanager_test.domain

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.reactivex.Single

class LocationRepository(private val context: Context) {

    fun getLocation(): Single<Location> {
        return Single.create { emitter ->

            var location: Location? = null
            val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
            val locationCallback = object : LocationCallback() {}
            val locationRequest = LocationRequest()

            locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
            locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            try {
                fusedLocation
                    .lastLocation
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            location = task.result
                            Log.d("Location", location.toString())
                            fusedLocation.removeLocationUpdates(locationCallback)
                            emitter.onSuccess(location!!)
                        } else {
                            Log.d("Location", "Failed to get location")
                            emitter.onError(RuntimeException("Failed to get location"))
                        }
                    }
            } catch (it: SecurityException) {
                Log.d("Location", "Lost location permission.$it")
                emitter.onError(it)
            }
        }
    }

    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }
}