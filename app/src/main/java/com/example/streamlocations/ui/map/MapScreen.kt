package com.example.streamlocations.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streamlocations.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.UUID

@Composable
fun MapScreen() {
    val viewModel = hiltViewModel<MapScreenViewModel>()
    val state by viewModel.mapViewState.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(key1 = Unit) {
        viewModel.streamLocationUpdates(UUID.randomUUID().toString())
    }

    LaunchedEffect(state.location) {
        state.location?.let { location ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude), DEFAULT_ZOOM_LEVEL
                ), DURATION_MS_300
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        state.location?.let { location ->
            CarMarker(location)
        }
    }
}

@Composable
private fun CarMarker(location: LatLng) {
    val context = LocalContext.current
    Marker(
        state = MarkerState(position = location),
        icon = bitmapDescriptorFromVector(context, R.drawable.ic_car),
        contentDescription = null
    )
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

private const val DEFAULT_ZOOM_LEVEL: Float = 18f
private const val DURATION_MS_300: Int = 300
