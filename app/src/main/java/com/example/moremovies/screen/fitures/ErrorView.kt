package com.example.moremovies.screen.fitures

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moremovies.ui.theme.WhiteColor
import com.example.moremovies.ui.theme.styleTextBodyBig

@Composable
fun ErrorView(
    errorMessage: String,
    refreshText: String? = null,
    onRefreshClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            tint = WhiteColor,
            imageVector = Icons.Rounded.Warning,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = errorMessage,
            style = styleTextBodyBig,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        if (onRefreshClick != null) {
            if (refreshText != null) {
                Text(
                    text = refreshText,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier.clickable { onRefreshClick() }
                )
            }
        }

    }
}
