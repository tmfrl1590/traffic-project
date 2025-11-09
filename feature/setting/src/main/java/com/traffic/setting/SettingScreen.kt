package com.traffic.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.common.R
import com.traffic.setting.component.SettingRowContent

@Composable
internal fun SettingScreen(
    context: Context = LocalContext.current
){
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val version = info.versionName

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
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            SettingRowContent(
                title = stringResource(R.string.setting_app_version),
                content = version,
            )

            SettingRowContent(
                title = stringResource(R.string.setting_inquire),
                content = "",
                onClick = {
                    val emailAddress = "tmfrl1590@gmail.com"
                    val emailSubject = "광주 버스 앱 건의"

                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.fromParts("mailto", emailAddress, null)
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    }

                    runCatching {
                        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.setting_inquire)))
                    }
                },
            )

            SettingRowContent(
                title = stringResource(id = R.string.setting_open_source_list),
                content = "",
                onClick = {
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }
            )
        }
    }
}