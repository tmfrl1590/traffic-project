package com.silver.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.traffic.common.noRippleClickable

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    onTabClick: (BottomBarScreen) -> Unit,
    onBack: (BottomBarScreen) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromBottomRoute()

    AppBottomNavigationBar(
        show = navController.shouldShowBottomBar
    ) {
        bottomDestinations.forEach { item ->
            AppBottomNavigationBarItem(
                icon = item.icon,
                label = item.name,
                selected = currentScreen == item,
                onTabClick = { onTabClick(item) },
                onBack = { onBack(currentScreen) }
            )
        }
    }
}

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    show: Boolean,
    content: @Composable (RowScope.() -> Unit),
) {
    Surface(
        color = Color.White,
        modifier = modifier.windowInsetsPadding(BottomAppBarDefaults.windowInsets)
    ) {
        if (show) {
            Column {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Composable
fun RowScope.AppBottomNavigationBarItem(
    label: String,
    selected: Boolean,
    icon: ImageVector? = null,
    onTabClick: () -> Unit,
    onBack: () -> Unit,
) {
    BackHandler(
        enabled = true,
        onBack = { onBack() }
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .noRippleClickable {
                onTabClick()
            }
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon!!,
            contentDescription = "",
            tint = if (selected) Color.Red else Color.Gray
        )

        Text(
            text = label,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) Color.Red else Color.Black
        )
    }
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry.fromBottomRoute()) {
        BottomBarScreen.Home,
        BottomBarScreen.Station,
        //BottomBarScreen.Line,
        //BottomBarScreen.Map,
        BottomBarScreen.Setting,
        -> true
    }