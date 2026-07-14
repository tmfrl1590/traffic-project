package com.traffic.presentation.screens.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.traffic.navigation.Screens
import com.traffic.presentation.screens.setting.SettingScreenRoute

fun NavGraphBuilder.settingNavGraph() {
    composable<Screens.Setting> {
        SettingScreenRoute()
    }
}
