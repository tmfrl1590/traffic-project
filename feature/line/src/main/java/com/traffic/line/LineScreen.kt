package com.traffic.line

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.traffic.common.LineInfo
import com.traffic.common.NoDataComponent
import com.traffic.common.R
import com.traffic.line.viewmodel.LineViewModel

@Composable
fun LineScreen(
    lineViewModel: LineViewModel = hiltViewModel(),
) {
    var keyword by remember {
        mutableStateOf("")
    }

    val searchedLineList by lineViewModel.searchedLineList.collectAsState(initial = listOf())

    LaunchedEffect(key1 = Unit) {
        //logEvent(context, "LineScreen")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchArea(
            modifier = Modifier
                .weight(0.15f),
            text = stringResource(R.string.line),
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { lineViewModel.getSearchedLineList(keyword = keyword) },
        )

        if (searchedLineList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(0.75f)
            ) {
                itemsIndexed(
                    items = searchedLineList
                ) { index, lineModel ->
                    LineInfo(
                        lineKind = lineModel.lineKind ?: "",
                        lineName = lineModel.lineName ?: "",
                        dirDownName = lineModel.dirDownName ?: "",
                        dirUpName = lineModel.dirUpName ?: ""
                    )
                }
            }
        } else {
            NoDataComponent(
                modifier = Modifier
                    .weight(0.9f),
                text = stringResource(R.string.searched_line_no_data)
            )
        }

        /*BannersAds(
            modifier = Modifier
                .weight(0.1f)
        )*/
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