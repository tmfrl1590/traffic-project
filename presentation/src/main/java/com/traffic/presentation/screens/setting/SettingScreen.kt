package com.traffic.presentation.screens.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.design.R
import com.traffic.presentation.firebase.ScreenName
import com.traffic.presentation.firebase.TrackScreenView
import com.traffic.presentation.PresentationConstants
import com.traffic.presentation.screens.setting.action.SettingAction
import com.traffic.presentation.screens.setting.component.SettingItem
import com.traffic.presentation.screens.setting.component.sendEmail

@Composable
fun SettingScreenRoute(
    context: Context = LocalContext.current
){
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val appVersion = info.versionName.orEmpty()

    SettingScreen(
        appVersion = appVersion,
        onAction = { action ->
            when(action){
                is SettingAction.OnClickInquire -> {
                    context.sendEmail(
                        to = PresentationConstants.INQUIRE_EMAIL,
                        subject = PresentationConstants.INQUIRE_SUBJECT,
                        chooserTitle = context.getString(R.string.setting_inquire)
                    )
                }
                is SettingAction.OnClickOpenSource -> {
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }
            }
        }
    )
}

@Composable
private fun SettingScreen(
    appVersion: String,
    onAction: (SettingAction) -> Unit,
) {
    TrackScreenView(screenName = ScreenName.Setting)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            SettingItem(
                title = stringResource(R.string.setting_app_version),
                content = { Text(appVersion) },
            )

            SettingItem(
                title = stringResource(R.string.setting_inquire),
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color(0xFFA0A0A0)
                    )
                },
                onClick = { onAction(SettingAction.OnClickInquire) },
            )

            SettingItem(
                title = stringResource(id = R.string.setting_open_source_list),
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color(0xFFA0A0A0)
                    )
                },
                onClick = { onAction(SettingAction.OnClickOpenSource) }
            )
        }
    }
}