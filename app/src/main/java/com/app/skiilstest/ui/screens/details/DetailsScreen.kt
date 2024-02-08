package com.app.skiilstest.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.skiilstest.data.DataOrException
import com.app.skiilstest.model.Acteurs
import com.app.skiilstest.model.Cast
import com.app.skiilstest.model.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, movieItem: MovieItem) {

    val detailsViewModel: DetailsViewModel = hiltViewModel<DetailsViewModel>()
    val imgUrl = "https://media.themoviedb.org/t/p/w220_and_h330_face/${movieItem.poster_path}"

    val moviesActeursData = produceState<DataOrException<Acteurs, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = detailsViewModel.getMovieActeursData(movieItem.id.toString())
    }.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Popular movies", color = Color.Blue) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.Blue,
                        contentDescription = "back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                })
        }
    ) {
        if (moviesActeursData.loading == true) {
            CircularProgressIndicator()
        } else if (moviesActeursData.data != null) {
            DetailsContains(it, imgUrl, movieItem, moviesActeursData)
        }
    }
}

@Composable
private fun DetailsContains(
    paddingValues: PaddingValues,
    imgUrl: String,
    movieItem: MovieItem,
    moviesActeursData: DataOrException<Acteurs, Boolean, Exception>
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        MovieBanner(imgUrl, movieItem.title)
        Text(
            text = "Synopsis",
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = movieItem.overview,
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(5.dp)
        )

        Text(
            text = "Cast",
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(5.dp)
        )

        LazyRow {
            items(items = moviesActeursData.data!!.cast.take(5)) { cast ->
                CastRow(cast)
            }
        }
    }
}

@Composable
private fun CastRow(cast: Cast) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imgCastUrl =
            "https://media.themoviedb.org/t/p/w220_and_h330_face/${cast.profile_path}"
        AsyncImage(
            model = imgCastUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp)
        )
        Text(
            text = cast.name,
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}


@Composable
fun MovieBanner(imgUrl: String, movieTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.3F)
        ) {
            Text(
                text = movieTitle,
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }

    }
}