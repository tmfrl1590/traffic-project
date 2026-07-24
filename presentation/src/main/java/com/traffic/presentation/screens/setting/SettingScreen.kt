package com.traffic.presentation.screens.setting

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.traffic.design.R
import com.traffic.design.component.TwoButtonDialog
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.presentation.PresentationConstants
import com.traffic.presentation.firebase.ScreenName
import com.traffic.presentation.firebase.TrackScreenView
import com.traffic.presentation.screens.setting.action.SettingAction
import com.traffic.presentation.screens.setting.component.AppFontSizeSection
import com.traffic.presentation.screens.setting.component.AppThemeSection
import com.traffic.presentation.screens.setting.component.AppVersionSection
import com.traffic.presentation.screens.setting.component.InquireSection
import com.traffic.presentation.screens.setting.component.LicenseSection
import com.traffic.presentation.screens.setting.component.ResetPinnedBusSection
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
                SettingAction.OnClickOpenSource -> { context.startActivity(Intent(context, OssLicensesMenuActivity::class.java)) }
                is SettingAction.OnClickFontSize -> { viewModel.selectFontSize(fontSizeText = action.fontSizeText) }
                is SettingAction.OnClickTheme -> { viewModel.selectTheme(themeType = action.themeType) }
                SettingAction.OnClickReset -> { viewModel.showResetConfirmDialog() }
                SettingAction.OnDismissResetDialog -> { viewModel.dismissResetConfirmDialog() }
                SettingAction.OnClickResetConfirm -> { viewModel.resetPinnedBusData() }
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
    val scrollState = rememberScrollState()

    TrackScreenView(screenName = ScreenName.Setting)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TrafficTheme.colors.mainBackground)
                .padding(horizontal = 20.dp)
                .verticalScroll(state = scrollState)
        ) {
            InquireSection(
                onClickInquire = { onAction(SettingAction.OnClickInquire) }
            )

            AppFontSizeSection(
                selectedFontSize = state.selectedFontSize,
                onClickFontSize = { onAction(SettingAction.OnClickFontSize(fontSizeText = it)) }
            )

            AppThemeSection(
                selectedTheme = state.selectedTheme,
                onClickTheme = { onAction(SettingAction.OnClickTheme(themeType = it)) }
            )

            ResetPinnedBusSection(
                onClickReset = { onAction(SettingAction.OnClickReset) }
            )

            AppVersionSection(
                appVersion = appVersion,
            )

            LicenseSection(
                onClickOpenSource = { onAction(SettingAction.OnClickOpenSource) }
            )
        }

        if(state.isShowResetConfirmDialog){
            TwoButtonDialog(
                modifier = Modifier
                    .align(Alignment.Center)
                ,
                dialogTitle = "핀 데이터 초기화",
                dialogDescription = "등록된 모든 핀 버스 목록을 삭제하시겠습니까?\n이 작업은 되돌릴 수 없습니다.",
                onCancel = { onAction(SettingAction.OnDismissResetDialog) },
                onConfirm = { onAction(SettingAction.OnClickResetConfirm) },
            )
        }
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