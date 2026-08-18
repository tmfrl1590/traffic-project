package com.system.traffic.presentation.screens.setting

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.system.traffic.design.R
import com.system.traffic.design.component.TwoButtonDialog
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.presentation.PresentationConstants
import com.system.traffic.presentation.firebase.ScreenName
import com.system.traffic.presentation.firebase.TrackScreenView
import com.system.traffic.presentation.screens.setting.action.SettingAction
import com.system.traffic.presentation.screens.setting.component.AppFontSizeSection
import com.system.traffic.presentation.screens.setting.component.AppThemeSection
import com.system.traffic.presentation.screens.setting.component.AppVersionSection
import com.system.traffic.presentation.screens.setting.component.InquireSection
import com.system.traffic.presentation.screens.setting.component.LicenseSection
import com.system.traffic.presentation.screens.setting.component.ResetPinnedBusSection
import com.system.traffic.presentation.screens.setting.effect.SettingEffect
import com.system.traffic.presentation.screens.setting.state.SettingState
import com.system.traffic.presentation.screens.setting.viewmodel.SettingViewModel

@Composable
fun SettingScreenRoute(
    viewModel: SettingViewModel = hiltViewModel(),
){
    TrackScreenView(screenName = ScreenName.Setting)

    val context = LocalContext.current
    val appVersion = remember(context) { context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty() }

    val state by viewModel.state.collectAsStateWithLifecycle()

    // VM이 발행한 일회성 사이드 이펙트(Context 필요 작업) 수집
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = viewModel) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    SettingEffect.SendInquireEmail -> {
                        context.sendEmail(
                            to = PresentationConstants.INQUIRE_EMAIL,
                            subject = context.getString(R.string.setting_inquire_subject),
                            chooserTitle = context.getString(R.string.setting_inquire)
                        )
                    }
                    SettingEffect.OpenOssLicenses -> {
                        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                    }
                }
            }
        }
    }

    SettingScreen(
        state = state,
        appVersion = appVersion,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun SettingScreen(
    state: SettingState,
    appVersion: String,
    onAction: (SettingAction) -> Unit,
) {
    val scrollState = rememberScrollState()

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
                onClickFontSize = { onAction(SettingAction.OnClickFontSize(fontSize = it)) }
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
                    .testTag(SettingTestTags.RESET_PINNED_BUS_DIALOG)
                ,
                cancelButtonModifier = Modifier
                    .testTag(SettingTestTags.RESET_PINNED_BUS_DIALOG_CANCEL_BUTTON)
                ,
                dialogTitle = stringResource(R.string.setting_reset_pin_dialog_title),
                dialogDescription = stringResource(R.string.setting_reset_pin_dialog_description),
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
        Toast.makeText(this, getString(R.string.email_app_not_found), Toast.LENGTH_SHORT).show()
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