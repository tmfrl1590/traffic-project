package com.system.traffic.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * 권한 확인/요청을 담당하는 매니저.
 *
 * @ActivityScoped 라 액티비티 생명주기에 맞춰 생성되고,
 * Hilt 주입 시점(super.onCreate 내부)에 런처가 등록되므로
 * registerForActivityResult 의 "STARTED 이전 등록" 규칙을 만족한다.
 */
@ActivityScoped
class PermissionManager @Inject constructor(
    activity: Activity,
) {
    private val activity = activity as ComponentActivity

    private var onResult: ((Boolean) -> Unit)? = null

    private val launcher = this.activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onResult?.invoke(isGranted)
        onResult = null
    }

    fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(activity, permission) ==
            PackageManager.PERMISSION_GRANTED

    /**
     * 권한을 요청한다. 이미 허용된 상태면 즉시 onResult(true) 호출.
     */
    fun request(permission: String, onResult: (Boolean) -> Unit = {}) {
        if (hasPermission(permission)) {
            onResult(true)
            return
        }
        this.onResult = onResult
        launcher.launch(permission)
    }

    /**
     * 알림 권한 요청. POST_NOTIFICATIONS 는 Android 13(API 33) 이상에서만 필요.
     */
    fun requestNotificationPermission(onResult: (Boolean) -> Unit = {}) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            request(Manifest.permission.POST_NOTIFICATIONS, onResult)
        } else {
            onResult(true)
        }
    }
}
