package com.system.traffic.design.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.system.traffic.design.ui.theme.TrafficTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = TrafficTheme.typography.title,
                color = TrafficTheme.colors.textPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TrafficTheme.colors.mainBackground
        )
    )
}