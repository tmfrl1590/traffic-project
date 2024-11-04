package com.traffic.station

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.traffic.common.R
import com.traffic.station.component.StationListArea
import com.traffic.station.viewmodel.StationViewModel


@Composable
fun StationScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
    onStationCardClick: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        //logEvent(context, "StationScreen")
        stationViewModel.getLikeStationList()
    }

    val searchedStationList by stationViewModel.searchResult.collectAsState(initial = listOf())

    var keyword by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchArea(
            modifier = Modifier
                .weight(0.15f),
            text = stringResource(R.string.station),
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { stationViewModel.getSearchedStationList(keyword = keyword) },
        )

        StationListArea(
            modifier = Modifier
                .weight(0.75f),
            stationViewModel = stationViewModel,
            navHostController = navHostController,
            searchedStationList = searchedStationList,
            onStationCardClick = onStationCardClick,
        )
    }
}

@Composable
fun SearchArea(
    modifier: Modifier,
    text: String,
    keyword: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = keyword,
            onValueChange = onValueChange,
            placeholder = { Text(text = "검색어를 입력해주세요") },
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = ""
                )
            },
            modifier = Modifier.weight(3f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                focusedTextColor = Color.Black,
                cursorColor = Color.Gray,
            )
        )
    }
}