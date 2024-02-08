package com.app.skiilstest.ui.screens.main

import androidx.lifecycle.ViewModel
import com.app.skiilstest.data.DataOrException
import com.app.skiilstest.model.Movies
import com.app.skiilstest.repository.MovieRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    suspend fun getMoviesData()
            : DataOrException<Movies, Boolean, Exception> {
        return repository.getMovies()

    }

}