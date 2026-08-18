package com.system.traffic.presentation.screens.station.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.system.traffic.design.R
import com.system.traffic.design.component.noRippleClickable
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.presentation.screens.station.StationTestTags

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
            .padding(horizontal = 20.dp)
            .testTag(tag = StationTestTags.SEARCH_BAR)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = keyword,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TrafficTheme.colors.searchBarPlaceholder,
                    style = TrafficTheme.typography.placeHolder,
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
                .fillMaxHeight()
                .testTag(tag = StationTestTags.SEARCH_INPUT)
            ,
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
                    contentDescription = stringResource(R.string.cd_clear_search),
                    modifier = Modifier
                        .noRippleClickable { onDeleteInputText() }
                        .testTag(tag = StationTestTags.SEARCH_CLEAR_ICON)
                    ,
                    tint = TrafficTheme.colors.searchBarClearIcon
                )
            }
        )
    }
}