package com.traffic.presentation.screens.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.common.R
import com.traffic.common.firebase.ScreenName
import com.traffic.common.firebase.TrackScreenView
import com.traffic.presentation.screens.setting.component.SettingRowItem

@Composable
internal fun SettingScreen(
    context: Context = LocalContext.current
){
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val version = info.versionName.orEmpty()

    SettingScreenContent(
        context = context,
        version = version,
    )
}

@Composable
private fun SettingScreenContent(
    context: Context,
    version: String,
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
            SettingRowItem(
                title = stringResource(R.string.setting_app_version),
                content = { Text(version) },
            )

            SettingRowItem(
                title = stringResource(R.string.setting_inquire),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color(0xFFA0A0A0)
                    )
                },
                onClick = {
                    val emailAddress = "tmfrl1590@gmail.com"
                    val emailSubject = "광주 버스 앱 건의"

                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.fromParts("mailto", emailAddress, null)
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    }

                    runCatching {
                        context.startActivity(
                            Intent.createChooser(
                                emailIntent,
                                context.getString(R.string.setting_inquire)
                            )
                        )
                    }
                },
            )

            SettingRowItem(
                title = stringResource(id = R.string.setting_open_source_list),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color(0xFFA0A0A0)
                    )
                },
                onClick = {
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }
            )
        }
    }
}