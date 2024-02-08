package com.app.skiilstest.ui.screens.details

import androidx.lifecycle.ViewModel
import com.app.skiilstest.data.DataOrException
import com.app.skiilstest.model.Acteurs
import com.app.skiilstest.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    suspend fun getMovieActeursData(movieId: String)
            : DataOrException<Acteurs, Boolean, Exception> {
        return repository.getMovieActeurs(movieId)

    }

}