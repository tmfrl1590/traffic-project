package com.traffic.presentation.screens.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.design.R
import com.traffic.presentation.PresentationConstants
import com.traffic.presentation.firebase.ScreenName
import com.traffic.presentation.firebase.TrackScreenView
import com.traffic.presentation.screens.setting.action.SettingAction
import com.traffic.presentation.screens.setting.component.AppFontSizeSection
import com.traffic.presentation.screens.setting.component.AppVersionSection
import com.traffic.presentation.screens.setting.component.InquireSection
import com.traffic.presentation.screens.setting.component.LicenseSection
import com.traffic.presentation.screens.setting.state.SettingState
import com.traffic.presentation.screens.setting.viewmodel.SettingViewModel

@Composable
fun SettingScreenRoute(
    context: Context = LocalContext.current,
    viewModel: SettingViewModel = hiltViewModel(),
){
    val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val appVersion = info.versionName.orEmpty()

    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingScreen(
        state = state,
        appVersion = appVersion,
        onAction = { action ->
            when(action){
                SettingAction.OnClickInquire -> {
                    context.sendEmail(
                        to = PresentationConstants.INQUIRE_EMAIL,
                        subject = PresentationConstants.INQUIRE_SUBJECT,
                        chooserTitle = context.getString(R.string.setting_inquire)
                    )
                }
                SettingAction.OnClickOpenSource -> {
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }

                is SettingAction.OnClickFontSize -> {
                    viewModel.selectFontSize(fontSizeText = action.fontSizeText)
                }
            }
        }
    )
}

@Composable
private fun SettingScreen(
    state: SettingState,
    appVersion: String,
    onAction: (SettingAction) -> Unit,
) {
    TrackScreenView(screenName = ScreenName.Setting)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(horizontal = 20.dp)
    ) {
        InquireSection(
            onClickInquire = { onAction(SettingAction.OnClickInquire) }
        )

        AppFontSizeSection(
            selectedFontSize = state.selectedFontSize,
            onClickFontSize = { onAction(SettingAction.OnClickFontSize(fontSizeText = it)) }
        )

        AppVersionSection(
            appVersion = appVersion,
        )

        LicenseSection(
            onClickOpenSource = { onAction(SettingAction.OnClickOpenSource) }
        )
    }
}


fun Context.sendEmail(to: String, subject: String, chooserTitle: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:$to?subject=${Uri.encode(subject)}".toUri()
    }

    runCatching {
        startActivity(Intent.createChooser(intent, chooserTitle))
    }.onFailure {
        Toast.makeText(this, "이메일을 보낼 수 있는 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingScreen() {
    SettingScreen(
        state = SettingState(),
        appVersion = "1.0.0",
        onAction = {}
    )
}