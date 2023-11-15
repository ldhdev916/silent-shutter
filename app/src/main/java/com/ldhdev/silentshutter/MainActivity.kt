package com.ldhdev.silentshutter

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ldhdev.silentshutter.component.CameraShutterManager
import com.ldhdev.silentshutter.component.PermissionRequester
import com.ldhdev.silentshutter.ui.theme.SilentShutterTheme

class MainActivity : ComponentActivity() {

    private fun canWriteSettings(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true

        return Settings.System.canWrite(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var canWrite by remember { mutableStateOf(canWriteSettings()) }

            LaunchedEffect(this) {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    canWrite = canWriteSettings()
                }
            }

            SilentShutterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (canWrite) {
                        CameraShutterManager()
                    } else {
                        PermissionRequester()
                    }
                }
            }
        }
    }
}