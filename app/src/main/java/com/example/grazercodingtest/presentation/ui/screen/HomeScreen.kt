package com.example.grazercodingtest.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.grazercodingtest.R
import com.example.grazercodingtest.presentation.ui.viewmodel.HomeScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {
    val userListState by viewModel.userListState.collectAsState()
    val systemUiController = rememberSystemUiController()

    val color = MaterialTheme.colorScheme.background
    val darkIcons = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = color, darkIcons = !darkIcons
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 6.dp), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = userListState is HomeScreenViewModel.UserRequestState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = userListState is HomeScreenViewModel.UserRequestState.Error) {
            Text(
                text = "Error: ${(userListState as HomeScreenViewModel.UserRequestState.Error).message}",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = userListState is HomeScreenViewModel.UserRequestState.Success) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.padding(horizontal = 8.dp),
                columns = StaggeredGridCells.Fixed(2)
            ) {
                items((userListState as HomeScreenViewModel.UserRequestState.Success).userList.size) { index ->
                    val user = (userListState as HomeScreenViewModel.UserRequestState.Success).userList[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(
                                        LocalContext.current
                                    ).data(data = user.profileImage).apply(block = fun ImageRequest.Builder.() {
                                        placeholder(R.drawable.profile_placeholder)
                                    }).build()
                                ),
                                contentDescription = "User Profile Image",
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = user.name,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

        }
    }
}