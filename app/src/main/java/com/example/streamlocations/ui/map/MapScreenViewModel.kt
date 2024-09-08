package com.example.streamlocations.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamlocations.data.location.FetchLocationUpdatesUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapViewState(val location: LatLng? = null)

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val fetchLocationUpdatesUseCase: FetchLocationUpdatesUseCase
) : ViewModel() {

    private val _mapViewState = MutableStateFlow(MapViewState())
    val mapViewState: StateFlow<MapViewState> = _mapViewState.asStateFlow()

    fun streamLocationUpdates(userId: String) {
        viewModelScope.launch {
            fetchLocationUpdatesUseCase(userId).catch {
                Log.e(TAG, "Error while fetching location: $it")
            }.collectLatest { location ->
                _mapViewState.update { state ->
                    state.copy(
                        location = LatLng(
                            location.latitude, location.longitude
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "MapScreenViewModel"
    }
}
