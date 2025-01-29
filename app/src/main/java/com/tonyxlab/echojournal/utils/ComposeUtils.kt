package com.tonyxlab.echojournal.utils

import android.Manifest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestRecordPermission(onPermissionResult: (Boolean) -> Unit) {
    val recordPermission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val currentOnPermissionResult by rememberUpdatedState(onPermissionResult)

    LaunchedEffect(recordPermission) {
        val permissionResult = recordPermission.status
        if (permissionResult.isGranted.not()) {
            if (permissionResult.shouldShowRationale) {
                // TODO Show Alert to Grant Permission
            } else {
                recordPermission.launchPermissionRequest()
            }
        }
        currentOnPermissionResult(permissionResult.isGranted)
    }
}