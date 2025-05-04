package com.example.fwa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fwa.ui.theme.FWATheme
import com.google.android.gms.security.ProviderInstaller

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        try {
            ProviderInstaller.installIfNeeded(applicationContext)
        } catch (e: Exception) {
            Log.e("SecurityProvider", "Failed to install security provider", e)
        }
        setContent {
            FWATheme {
              NavScreen()
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FWATheme {

    }
}