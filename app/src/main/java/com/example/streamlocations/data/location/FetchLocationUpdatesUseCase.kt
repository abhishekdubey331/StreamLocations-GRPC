package com.example.streamlocations.data.location

import android.location.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface FetchLocationUpdatesUseCase {
    operator fun invoke(userId: String): Flow<Location>
}

class FetchLocationUpdatesUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
) : FetchLocationUpdatesUseCase {

    override fun invoke(userId: String): Flow<Location> {
        val locationRequest = locationRequest { this.userId = userId }
        return locationRepository.streamLocationUpdates(locationRequest)
    }
}
