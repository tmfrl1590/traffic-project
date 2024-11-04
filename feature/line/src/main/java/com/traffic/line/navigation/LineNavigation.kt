package com.traffic.line.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.line.LineScreen

fun NavGraphBuilder.lineNavGraph() {
    composable<Screens.Line> {
        LineScreen()
    }
}