package com.example.moremovies.screen.registration_screen

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
import com.example.moremovies.screen.fitures.GradientButton
import com.example.moremovies.screen.fitures.MinimalDialog
import com.example.moremovies.screen.registration_screen.RegistrationAction.*
import com.example.moremovies.ui.theme.Orange
import com.example.moremovies.ui.theme.Red
import com.example.moremovies.ui.theme.WhiteColor
import com.example.moremovies.ui.theme.styleTextBodyBig
import com.example.moremovies.ui.theme.styleTextBodyNormal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    state: State<RegistrationState>,
    processAction: (action: RegistrationAction) -> Unit,
    onNavigate: (route: String) -> Unit,
) {
    val context = LocalContext.current
    val name = remember {
        mutableStateOf(TextFieldValue(text = "123"))
    }
    val email = remember { mutableStateOf(TextFieldValue(text = "123")) }
    val password = remember { mutableStateOf(TextFieldValue(text = "123")) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue(text = "123")) }
    val nameErrorState = remember { mutableStateOf(false) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val confirmPasswordErrorState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val currentState = state.value

    when (currentState) {

        is RegistrationState.Loading -> {
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
            return;
        }

        is RegistrationState.RegistrationStateError -> MinimalDialog(
            error = currentState.error, {
                processAction(
                    ClickOnDismiss(

                    )
                )
            }
        )

        is RegistrationState.SuccessRegistration -> {
            onNavigate(MainRoute.LoginScreen.value);
            return
        }

        is RegistrationState.InitialState -> {
            Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {


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

        Text(
            text = context.getString(R.string.register),
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Serif,
                color = WhiteColor
            )
        )

        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = WhiteColor,
                unfocusedTextColor = WhiteColor,
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = WhiteColor
            ),
            value = name.value,
            onValueChange = {
                if (nameErrorState.value) {
                    nameErrorState.value = false
                }
                name.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = styleTextBodyBig,

            isError = nameErrorState.value,
            label = {
                Text(
                    text = context.getString(R.string.enterName), color = WhiteColor,
                    style = styleTextBodyBig
                )
            },
        )
        if (nameErrorState.value) {
            Text(text = context.getString(R.string.required), color = Color.Red)
        }
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
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },
            textStyle = styleTextBodyBig,

            modifier = Modifier.fillMaxWidth(),
            isError = emailErrorState.value,
            label = {
                Text(
                    text = context.getString(R.string.email), color = WhiteColor,
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
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = context.getString(R.string.password), color = WhiteColor,
                    style = styleTextBodyBig
                )
            },
            isError = passwordErrorState.value,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
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
        val cPasswordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            textStyle = styleTextBodyBig,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = WhiteColor,
                unfocusedTextColor = WhiteColor,
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = WhiteColor
            ),
            value = confirmPassword.value,
            onValueChange = {
                if (confirmPasswordErrorState.value) {
                    confirmPasswordErrorState.value = false
                }
                confirmPassword.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordErrorState.value,
            label = {
                Text(
                    text = context.getString(R.string.confirmPassword), color = WhiteColor,
                    style = styleTextBodyBig
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    cPasswordVisibility.value = !cPasswordVisibility.value
                }) {
                    Icon(
                        imageVector = if (cPasswordVisibility.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "visibility",
                        tint = WhiteColor
                    )
                }
            },
            visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (confirmPasswordErrorState.value) {
            val msg = if (confirmPassword.value.text.isEmpty()) {
                context.getString(R.string.required)
            } else if (confirmPassword.value.text != password.value.text) {
                context.getString(R.string.notMatchingPassword)
            } else {
                ""
            }
            Text(text = msg, color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))

        GradientButton(
            text = context.getString(R.string.register),
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
                name.value.text.isEmpty() -> {
                    nameErrorState.value = true
                }

                email.value.text.isEmpty() -> {
                    emailErrorState.value = true
                }

                password.value.text.isEmpty() -> {
                    passwordErrorState.value = true
                }

                confirmPassword.value.text.isEmpty() -> {
                    confirmPasswordErrorState.value = true
                }

                confirmPassword.value.text != password.value.text -> {
                    confirmPasswordErrorState.value = true
                }

                else -> {
                    nameErrorState.value = false
                    emailErrorState.value = false
                    passwordErrorState.value = false
                    confirmPasswordErrorState.value = false
                    processAction(
                        RegistrationAction.ClickRegistrationButton(
                            RegistrationModel(
                                name = name.value.text.trim(),
                                email = email.value.text.trim(),
                                password = password.value.text.trim()
                            )
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = {
                onNavigate(MainRoute.LoginScreen.value)

            }) {
                Text(
                    text = context.getString(R.string.login), color = WhiteColor,
                    style = styleTextBodyNormal
                )
            }
        }
    }



}