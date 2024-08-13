package com.traffic.presentation.screen.setting

import android.content.Intent
import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.presentation.R
import com.traffic.presentation.component.CommonTitleComponent

@Composable
fun SettingScreen(){
    val context = LocalContext.current
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val version = info.versionName

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTitleComponent(
            title = stringResource(R.string.setting)
        )

        HorizontalDivider(
            modifier = Modifier.padding(16.dp),
        )

        RowContent(
            title = stringResource(R.string.setting_app_version),
            content = version
        )

        RowContent(
            title = stringResource(id = R.string.setting_open_source_list),
            content = "open source",
            onClick = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
        )

        /*RowContent(
            title = "앱테마 설정",
            content = version
        )*/
    }
}