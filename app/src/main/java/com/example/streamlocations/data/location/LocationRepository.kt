package com.example.streamlocations.data.location

import android.location.Location
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val channel: ManagedChannel
) {

    fun streamLocationUpdates(locationRequest: Service.LocationRequest): Flow<Location> {
        val stub = LocationServiceGrpcKt.LocationServiceCoroutineStub(channel).withWaitForReady()

        return stub.getLocation(locationRequest).map { response ->
            Location("gps").apply {
                longitude = response.longitude
                latitude = response.latitude
            }
        }
    }
}
