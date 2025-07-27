package com.traffic.common

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}
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
            color = Color.Black
        )
    }
}

@Composable
fun LineInfo(
    lineKind: String,
    lineName: String,
    dirDownName: String,
    dirUpName: String
){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(12.dp),
    ){
        val lineColor = lineTestColor(lineKind)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = lineName,
                    modifier = Modifier.height(52.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = lineColor
                )

                Text(
                    text = "$dirDownName ~ $dirUpName",
                    modifier = Modifier.weight(5f)
                )
            }
        }
    }
}

fun lineTestColor(lienKind: String): Color {
    return when (lienKind) {
        "1" -> Color.Red
        "2" -> Color.Green
        "3" -> Color.Blue
        else -> Color.Black
    }
}

/*@Composable
fun BannersAds(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                //adUnitId = "ca-app-pub-3940256099942544/6300978111" // 테스트
                adUnitId = "ca-app-pub-3991873148102758/7356139432" // 실
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}*/

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
        color = Color.Black
    )
}

/*@Composable
fun CustomLoadingBar(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        BallPulseSyncProgressIndicator(
            modifier = Modifier,
            color = Color.Gray,
            animationDuration = 800,
            animationDelay = 200,
            startDelay = 0,
            ballDiameter = 12.dp,
            ballJumpHeight = 42.dp,
            ballCount = 4
        )
    }
}*/

@Composable
fun ScaffoldBackIcon() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "back",
        tint = Color.Black
    )
}

fun snackBarMessage(
    snackBarHostState: SnackbarHostState,
    message: String,
    durationTime: Long = 2000L
) {
    CoroutineScope(Dispatchers.Main).launch {
        val job = launch {
            snackBarHostState.showSnackbar(message)
        }
        delay(durationTime)
        job.cancel()
    }
}

@Composable
fun SearchArea(
    keyword: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit,
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
                    text = stringResource(id = R.string.common1),
                    color = Color(0xFFADAEBC),
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
                focusedBorderColor = Color(0xFFE5E7EB),
                unfocusedBorderColor = Color(0xFFE5E7EB),
                focusedContainerColor = Color(0xFFF9FAFB),
                unfocusedContainerColor = Color(0xFFF9FAFB),
                focusedTextColor = Color.Black,
                cursorColor = Color.Gray,
            ),
        )
    }
}