package com.traffic.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.common.CommonTitleComponent
import com.traffic.common.R

@Composable
fun SettingScreen(
    context: Context,
){
    LaunchedEffect(key1 = Unit) {
        //logEvent(context, "SettingScreen")
    }
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val version = info.versionName

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTitleComponent(
            title = stringResource(id = R.string.setting)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        RowContent(
            title = stringResource(com.traffic.common.R.string.setting_app_version),
            content = version
        )

        RowContent(
            title = stringResource(id = com.traffic.common.R.string.setting_open_source_list),
            content = "",
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