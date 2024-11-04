package com.traffic.map.navigation

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.silver.navigation.Screens
import com.traffic.map.MapScreen

fun NavGraphBuilder.mapNavGraph(
    context: Context,
    snackBarHostState: SnackbarHostState,
) {
    composable<Screens.Map> {
        MapScreen(context = context, snackBarHostState = snackBarHostState)
    }
}