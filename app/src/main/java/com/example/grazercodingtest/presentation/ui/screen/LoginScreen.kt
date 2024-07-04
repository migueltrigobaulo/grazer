package com.example.grazercodingtest.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grazercodingtest.R
import com.example.grazercodingtest.presentation.ui.theme.GrazerGreen
import com.example.grazercodingtest.presentation.ui.viewmodel.LoginScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navigateToHome = {})
}

@Composable
fun LoginScreen(navigateToHome: () -> Unit, viewModel: LoginScreenViewModel = koinViewModel()) {

    val systemUiController = rememberSystemUiController()
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp

    val expanded = viewModel.isExpanded

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Black, darkIcons = false
        )
    }

    val loginState = viewModel.loginState

    var startAnimation by remember { mutableStateOf(false) }

    if (loginState is LoginScreenViewModel.LoginState.Success && !startAnimation) {
        startAnimation = true
    }

    val targetValue = screenHeight/2
    val animatedValue by animateIntAsState(
        targetValue = if (startAnimation) targetValue else 0,
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )

    LaunchedEffect(animatedValue) {
        if (animatedValue == targetValue) {
            viewModel.clearLoginState()
            navigateToHome()
            startAnimation = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        LoginScreenProgress(viewModel, loginState)
        LoginScreenFields(viewModel, loginState)
        GrazerFieldsDecoration(animatedValue, screenHeight, expanded)
        GrazerMouthOutroAnim(viewModel, screenHeight)
        LoginScreenLogoAndButtons(viewModel, screenHeight, startAnimation)

    }
}

@Composable
private fun GrazerFieldsDecoration(
    animatedValue: Int,
    screenHeight: Int,
    expanded: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(animatedValue.dp)
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.plantpeoplefray)),
                contentDescription = "grazer_border",
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Box(
                modifier = Modifier
                    .height(screenHeight.dp)
                    .fillMaxWidth()
            )
        }
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Image(
                modifier = Modifier
                    .rotate(180f)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.plantpeoplefray)),
                contentDescription = "grazer_border",
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(animatedValue.dp)
            )
        }
    }

    Column {
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(Color.Black)
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.plantpeoplefray)),
            contentDescription = "grazer_border",
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}

@Composable
private fun LoginScreenProgress(
    viewModel: LoginScreenViewModel,
    loginState: LoginScreenViewModel.LoginState
) {
    AnimatedVisibility(
        visible = !viewModel.isExpanded && loginState is LoginScreenViewModel.LoginState.Loading,
        enter = fadeIn(tween(250)),
        exit = fadeOut(tween(250))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun LoginScreenFields(
    viewModel: LoginScreenViewModel,
    loginState: LoginScreenViewModel.LoginState
) {
    AnimatedVisibility(
        visible = !viewModel.isExpanded && loginState is LoginScreenViewModel.LoginState.Initial,
        enter = fadeIn(tween(1000)),
        exit = fadeOut(tween(250))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.enter_credentials).uppercase(),
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(id = R.string.email_label)) },
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                label = {
                    Text(text = stringResource(id = R.string.password_label))
                },
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { viewModel.onLogin() },
                colors = ButtonDefaults.buttonColors().copy(containerColor = GrazerGreen)
            ) {
                Text(text = stringResource(id = R.string.sign_in).uppercase())
            }
        }
    }
}

@Composable
private fun GrazerMouthOutroAnim(
    viewModel: LoginScreenViewModel,
    screenHeight: Int
) {
    Column {
        AnimatedVisibility(
            visible = viewModel.isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .height(screenHeight.dp - 150.dp)
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .rotate(180f),
            contentScale = ContentScale.FillWidth,
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.plantpeoplefray)),
            contentDescription = "grazer_border",
            colorFilter = ColorFilter.tint(Color.Black)
        )
        AnimatedVisibility(
            visible = !viewModel.isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }
    }
}

@Composable
private fun LoginScreenLogoAndButtons(
    viewModel: LoginScreenViewModel,
    screenHeight: Int,
    startAnimation: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AnimatedVisibility(
            visible = viewModel.isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box(modifier = Modifier.height(screenHeight.dp / 2 - 100.dp))
        }
        AnimatedVisibility(visible = !startAnimation) {
            Image(
                modifier = Modifier
                    .padding(top = 40.dp),
                painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.grazer_logo)),
                contentDescription = "grazer_logo",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }

        AnimatedVisibility(visible = viewModel.isExpanded) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(80.dp))
                Button(
                    onClick = { viewModel.onExpand() },
                    colors = ButtonDefaults.buttonColors().copy(containerColor = GrazerGreen)
                ) {
                    Text(text = stringResource(id = R.string.sign_in).uppercase())
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors().copy(containerColor = GrazerGreen)
                ) {
                    Text(text = stringResource(id = R.string.register_button).uppercase())
                }
            }
        }
    }
}