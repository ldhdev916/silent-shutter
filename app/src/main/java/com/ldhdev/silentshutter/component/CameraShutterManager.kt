package com.ldhdev.silentshutter.component

import android.content.Context
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ldhdev.silentshutter.CAMERA_SHUTTER_SETTINGS_NAME

private fun Context.isSilent(): Boolean {
    return try {
        Settings.System.getInt(contentResolver, CAMERA_SHUTTER_SETTINGS_NAME) == 0
    } catch (e: SettingNotFoundException) {
        false
    }
}

private fun Context.setSilent(silent: Boolean) {
    Settings.System.putInt(contentResolver, CAMERA_SHUTTER_SETTINGS_NAME, if (silent) 0 else 1)
}

@Composable
fun CameraShutterManager() {

    val context = LocalContext.current

    var silent by remember { mutableStateOf(context.isSilent()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "조용한 셔터", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.width(10.dp))

        Switch(
            checked = silent,
            onCheckedChange = {
                context.setSilent(it)

                silent = context.isSilent()
            },
        )
    }
}