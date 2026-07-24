package com.traffic.design.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.traffic.design.ui.theme.Black
import com.traffic.design.ui.theme.MainColor
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.design.ui.theme.White

@Composable
fun TwoButtonDialog(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogDescription: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 40.dp)
            ,
            border = BorderStroke(1.dp, TrafficTheme.colors.cardBorder),
            colors = CardDefaults.cardColors(
                containerColor = TrafficTheme.colors.cardBackground
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(24.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dialogTitle,
                    color = TrafficTheme.colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                Text(
                    text = dialogDescription,
                    color = TrafficTheme.colors.textSecondary,
                    textAlign = TextAlign.Center,
                )

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    DialogButton(
                        modifier = Modifier
                            .weight(1f)
                        ,
                        buttonText = "취소",
                        buttonColor = Black,
                        containerColor = Color.LightGray,
                        onClickButton = onCancel,
                    )
                    DialogButton(
                        modifier = Modifier
                            .weight(1f)
                        ,
                        buttonText = "삭제",
                        buttonColor = White,
                        containerColor = Color.Red,
                        onClickButton = onConfirm,
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogButton(
    modifier: Modifier,
    buttonText: String,
    buttonColor: Color,
    containerColor: Color,
    onClickButton: () -> Unit,
) {
    Card(
        onClick = onClickButton,
        modifier = modifier
            .height(48.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            ,
            contentAlignment = Alignment.Center
        ){
            Text(
                text = buttonText,
                color = buttonColor,
            )
        }
    }
}