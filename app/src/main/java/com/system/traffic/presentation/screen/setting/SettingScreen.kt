package com.system.traffic.presentation.screen.setting

import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.system.traffic.R
import com.system.traffic.presentation.component.TitleComponent

@Composable
fun SettingScreen(){
    val context = LocalContext.current
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val version = info.versionName


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TitleComponent(
            title = stringResource(R.string.setting)
        )

        HorizontalDivider(
            modifier = Modifier.padding(16.dp),
        )

        RowContent(
            title = "앱버전",
            content = version
        )

        /*RowContent(
            title = "앱테마 설정",
            content = version
        )*/
    }
}

@Composable
fun RowContent(
    title: String,
    content: String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start
        )

        Text(
            text = content,
            textAlign = TextAlign.End
        )
    }
}