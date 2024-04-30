package com.example.moremovies.screen.fitures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moremovies.R
import com.example.moremovies.ui.theme.ColorAppBar
import com.example.moremovies.ui.theme.MainColor
import com.example.moremovies.ui.theme.WhiteColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun CustomAppBar(
    drawerState: DrawerState?,
    title: String?,
    onNavigate: (() -> Unit)? = null,
    onValueChange: ((String) -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()
    val values = LocalContext.current
    val localFocusManager = LocalFocusManager.current


    Row(
        Modifier
            .padding(top = 12.dp, bottom = 8.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(ColorAppBar, ColorAppBar)
                )
            )
            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (drawerState != null) {
            IconButton(onClick = {
                localFocusManager.clearFocus()
                coroutineScope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    Icons.Filled.Menu,
                    modifier = Modifier.size(100.dp),
                    contentDescription = "",
                    tint = WhiteColor,
                )
            }
        } else {
            IconButton(onClick = {
                if (onNavigate != null) {
                    onNavigate.invoke()
                }
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    modifier = Modifier.size(50.dp),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            }


        }
        if (title == null) {
            var queryState by remember { mutableStateOf(TextFieldValue("")) }
            var showClearIcon by rememberSaveable { mutableStateOf(false) }


            LaunchedEffect(queryState) {
                val currentQuery = queryState.text
                if (currentQuery.isEmpty()) {
                    showClearIcon = false
                } else if (currentQuery.isNotEmpty()) {
                    showClearIcon = true
                }
                val delayTime = 1000L
                delay(delayTime)
                if (currentQuery == queryState.text && currentQuery.isNotEmpty()) {
                    if (onValueChange != null) {
                        onValueChange(queryState.text)
                    }
                }
            }
            TextField(
                value = queryState,
                onValueChange = { newValue ->
                    queryState = newValue

                }, label = { Text(text = values.getString(R.string.search), fontSize = 12.sp) },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = values.getString(R.string.search)
                    )
                },
                trailingIcon = {
                    if (showClearIcon) {
                        IconButton(onClick = { queryState = TextFieldValue("") }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                tint = MainColor,
                                contentDescription = "Clear icon"
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)

                    .fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (onNavigate != null) {
                IconButton(onClick = {
                    localFocusManager.clearFocus()
                    onNavigate.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit, contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    ),
                )
            }
        }


    }

}