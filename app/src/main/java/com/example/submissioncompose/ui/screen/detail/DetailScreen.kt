package com.example.submissioncompose.ui.screen.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.submissioncompose.R
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.components.BuyButton


@Composable
fun DetailScreen(
    gameId: String,
    viewModel: DetailScreenViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
) {
    val isFavorite = remember { mutableStateOf(false) }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getGameById(gameId)
            }

            is UiState.Success -> {
                val data = uiState.data

                viewModel.checkFavorite(gameId) { isGameFavorite ->
                    isFavorite.value = isGameFavorite
                }

                val openBrowser = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    when (result.resultCode) {
                        Activity.RESULT_OK -> {}
                    }
                }

                DetailContent(
                    gameName = data.item.gameName,
                    gameBanner = data.item.bannerUrl,
                    gamePrice = data.item.price,
                    gameDeveloper = data.item.developer,
                    gameRelease = data.item.releaseDate,
                    gameDescription = data.item.desc,
                    onBackClick = navigateBack,
                    isFavorite = isFavorite.value,
                    onToggleFavorite = {
                        if (isFavorite.value) {
                            viewModel.removeFromFavorite(gameId)
                            isFavorite.value = false
                        } else {
                            viewModel.addToFavorites(gameId)
                            isFavorite.value = true
                        }
                    },
                    buttonBuy = {
                        val intent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(data.item.gameLink)
                            )
                        openBrowser.launch(intent)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailContent(
    gameName: String,
    gameBanner: String,
    gamePrice: String,
    gameDeveloper: String,
    gameRelease: String,
    gameDescription: String,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    buttonBuy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                tint = Color.Black,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )

            AsyncImage(
                model = gameBanner,
                contentDescription = "game banner",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(400.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp
                        )
                    )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = gameName,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = Color.Black
                    )

                )
                IconButton(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = { onToggleFavorite() }

                ) {
                    val icon =
                        if (isFavorite) ImageVector.vectorResource(id = R.drawable.ic_baseline_favorite_24) else ImageVector.vectorResource(
                            id = R.drawable.ic_baseline_favorite_border_24
                        )
                    Icon(
                        imageVector = icon,
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Black,
                        contentDescription = stringResource(R.string.menu_favorite),
                        modifier = Modifier.size(60.dp).testTag("ButtonFavorite")
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            ) {


                Text(
                    text = gamePrice,
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Red
                    )
                )

                Text(
                    text = "Developer: $gameDeveloper",
                    textAlign = TextAlign.Left,
                )
                Text(
                    text = "Release: $gameRelease",
                    textAlign = TextAlign.Left,
                )

                Text(
                    text = gameDescription,
                    textAlign = TextAlign.Justify,
                    modifier = modifier
                        .padding(top = 5.dp)
                )
                BuyButton(
                    buttonText = "Buy Now",
                    onClick = { buttonBuy() },
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }
    }
}