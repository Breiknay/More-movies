package com.example.moremovies.screen.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moremovies.R
import com.example.moremovies.navigation.MainRoute
import com.example.moremovies.screen.fitures.ErrorView
import com.example.moremovies.screen.fitures.GradientButton
import com.example.moremovies.ui.theme.Orange
import com.example.moremovies.ui.theme.Red
import com.example.moremovies.ui.theme.WhiteColor
import com.example.moremovies.ui.theme.styleTextBodyBig
import com.example.moremovies.ui.theme.styleTextBodyNormal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: State<LoginState>,
    processAction: (action: LoginAction) -> Unit,
    onNavigate: (route: String) -> Unit,
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.value.loading) {
            val strokeWidth = 5.dp
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = Color.LightGray,
                    strokeWidth = strokeWidth
                )
            }
        } else {
            if (state.value.error != null) {
                ErrorView(
                    state.value.error.toString()
                )
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kinopoisk_icon_main),
                    contentDescription = "",
                    alignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()
                )
            }

            if (state.value.successLogin) {
                onNavigate(MainRoute.PremieresFilm.value)
            }

            Text(
                text = context.getString(R.string.login),
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Serif,
                    color = WhiteColor
                )
            )

            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                value = email.value,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = WhiteColor,
                    unfocusedTextColor = WhiteColor,
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedLabelColor = MaterialTheme.colors.secondary,
                    cursorColor = WhiteColor
                ),
                textStyle = styleTextBodyBig,
                onValueChange = {
                    if (emailErrorState.value) {
                        emailErrorState.value = false
                    }
                    email.value = it
                },
                isError = emailErrorState.value,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = context.getString(R.string.enterEmail), color = WhiteColor,
                        style = styleTextBodyBig
                    )
                },
            )
            if (emailErrorState.value) {
                Text(text = context.getString(R.string.required), color = Color.Red)
            }
            Spacer(Modifier.size(16.dp))
            val passwordVisibility = remember { mutableStateOf(true) }
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = WhiteColor,
                    unfocusedTextColor = WhiteColor,
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedLabelColor = MaterialTheme.colors.secondary,
                    cursorColor = WhiteColor
                ),
                textStyle = styleTextBodyBig,
                value = password.value,
                onValueChange = {
                    if (passwordErrorState.value) {
                        passwordErrorState.value = false
                    }
                    password.value = it
                },
                isError = passwordErrorState.value,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = context.getString(R.string.enterPassword), color = WhiteColor,
                        style = styleTextBodyBig
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "visibility",
                            tint = WhiteColor
                        )
                    }
                },
                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (passwordErrorState.value) {
                Text(text = context.getString(R.string.required), color = Color.Red)
            }
            Spacer(Modifier.size(16.dp))

            GradientButton(
                text = context.getString(R.string.login),
                textColor = Color.Black,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Orange,
                        Red,
                        Orange
                    )
                )
            ) {
                when {
                    email.value.text.isEmpty() -> {
                        emailErrorState.value = true
                    }

                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }

                    else -> {
                        passwordErrorState.value = false
                        emailErrorState.value = false
                        processAction(
                            LoginAction.ClickLoginButton(
                                LoginModel(
                                    email = email.value.text.trim(),
                                    password = password.value.text.trim()
                                )
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.size(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = {
                    onNavigate(MainRoute.RegistrationScreen.value)
                }) {
                    Text(
                        text = context.getString(R.string.registerButton), color = WhiteColor,
                        style = styleTextBodyNormal
                    )
                }
            }
        }
    }
}


