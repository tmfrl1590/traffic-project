package com.traffic.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.setting.SettingScreen

fun NavGraphBuilder.settingNavGraph(
    navHostController: NavHostController,
) {
    composable<Screens.Setting> {
        SettingScreen()
    }
}
