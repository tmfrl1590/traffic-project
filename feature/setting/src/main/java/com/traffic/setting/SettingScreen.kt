package com.traffic.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.common.AdBannerView
import com.traffic.common.CommonTitleComponent
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
                title = stringResource(id = R.string.setting_open_source_list),
                content = "",
                onClick = {
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }
            )
        }

        /*AdBannerView(
            modifier = Modifier
                .padding(vertical = 32.dp)
        )*/
    }
}