package com.app.skiilstest.ui.screens.main

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.skiilstest.data.DataOrException
import com.app.skiilstest.model.MovieItem
import com.app.skiilstest.model.Movies
import com.app.skiilstest.navigation.MovieScreens
import com.app.skiilstest.ui.screens.widgets.TextWithIcon
import com.google.gson.Gson

@Composable
fun MainScreen(navController: NavController) {

    val mainViewModel: MainViewModel = hiltViewModel<MainViewModel>()

    val moviesData = produceState<DataOrException<Movies, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getMoviesData()
    }.value

    if (moviesData.loading == true) {
        CircularProgressIndicator()
    } else if (moviesData.data != null) {
        MainContent(navController, moviesData.data!!.results)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(navController: NavController, results: List<MovieItem>) {
    val searchText = remember {
        mutableStateOf("")
    }

    val keyboard = LocalSoftwareKeyboardController.current
    val gson = Gson()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(
            text = "Popular movies", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ), color = Color.Black
        )
        Row(
            modifier = Modifier.padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (searchText.value.isNotEmpty()) Arrangement.Start else Arrangement.SpaceBetween
        ) {
            TextField(
                value = searchText.value, onValueChange = {
                    searchText.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                modifier = if (searchText.value.isNotEmpty()) Modifier.weight(1f) else Modifier.fillMaxWidth()
            )
            if (searchText.value.isNotEmpty())
                Text(
                    text = "Cancel",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        keyboard?.hide()
                        searchText.value = ""
                    })
        }

        LazyColumn {
            items(items = results.filter {
                it.title.contains(searchText.value, ignoreCase = true)
            }) {
                MovieRow(movie = it) {
                    val encodedJson = Uri.encode(gson.toJson(it))
                    navController.navigate(route = "${MovieScreens.DETAILS_SCREEN.name}/$encodedJson")
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: MovieItem, onItemClicked: (MovieItem) -> Unit) {
    val imgUrl = "https://media.themoviedb.org/t/p/w220_and_h330_face/${movie.poster_path}"
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onItemClicked(movie)
            },
        shape = RoundedCornerShape(10.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(model = imgUrl, contentDescription = null, modifier = Modifier.width(100.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = movie.title, style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ), color = Color.Black
                )
                TextWithIcon(
                    text = movie.vote_average.toString(),
                    icon = Icons.Default.Star,
                    tint = Color.Yellow
                )
            }

            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)

        }
    }
}