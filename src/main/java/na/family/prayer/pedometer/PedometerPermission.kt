package na.family.prayer.pedometer

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PedometerPermission(
    content: @Composable (Boolean) -> Unit
) {
    val permissions = listOf(
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.ACTIVITY_RECOGNITION
    )

    val permissionState = rememberMultiplePermissionsState(permissions)

    LaunchedEffect(key1 = Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    content(permissionState.allPermissionsGranted)
}