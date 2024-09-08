package com.example.streamlocations.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.streamlocations.ui.map.MapScreen
import com.example.streamlocations.ui.theme.RealtimeLocationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(SystemBarStyle.dark(Color.parseColor("#801b1b1b")))
        setContent {
            RealtimeLocationTheme {
                MapScreen()
            }
        }
    }
}

