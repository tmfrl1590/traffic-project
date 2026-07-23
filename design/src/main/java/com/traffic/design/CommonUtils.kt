package com.traffic.design

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.traffic.design.ui.theme.TrafficTheme

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
    onClick = onClick
)

@Composable
fun NoDataComponent(
    modifier: Modifier = Modifier,
    text: String,
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = TrafficTheme.colors.textPrimary
        )
    }
}

fun lineKindToColor(lineKind: String): Color {
    return when (lineKind) {
        "1" -> Color(0xFFDC2626)
        "2" -> Color(0xFF16A34A)
        "3" -> Color(0xFF2563EB)
        else -> Color.Black
    }
}

@Composable
fun AdBannerView(
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = CommonConfig.adUnitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
fun CommonTitleComponent(
    title: String,
){
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp),
        color = TrafficTheme.colors.textPrimary,
    )
}

@Composable
fun SearchBarSection(
    keyword: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit,
    onDeleteInputText: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = keyword,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TrafficTheme.colors.searchBarPlaceholder,
                )
            },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = true
            ),
            keyboardActions = KeyboardActions(
                onSearch = { searchAction() }
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TrafficTheme.colors.searchBarBackground,
                unfocusedBorderColor = TrafficTheme.colors.searchBarBackground,
                focusedContainerColor = TrafficTheme.colors.searchBarBackground,
                unfocusedContainerColor = TrafficTheme.colors.searchBarBackground,
                focusedTextColor = TrafficTheme.colors.searchBarText,
                cursorColor = Color.Gray,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close",
                    modifier = Modifier
                        .noRippleClickable { onDeleteInputText() },
                    tint = TrafficTheme.colors.searchBarClearIcon
                )
            }
        )
    }
}