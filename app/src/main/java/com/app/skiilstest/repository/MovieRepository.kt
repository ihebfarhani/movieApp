package com.app.skiilstest.repository

import android.util.Log
import com.app.skiilstest.constant.Constants.AUTH_TOKEN
import com.app.skiilstest.data.DataOrException
import com.app.skiilstest.model.Acteurs
import com.app.skiilstest.model.Movies
import com.app.skiilstest.network.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getMovies()
            : DataOrException<Movies, Boolean, Exception> {
        val response = try {
            movieApi.getMovies(AUTH_TOKEN)

        } catch (e: Exception) {
            Log.d("Exception", "getMovies: $e")
            return DataOrException(e = e)
        }
        Log.d("RESULT", "getMovies: $response")
        return DataOrException(data = response)
    }

    suspend fun getMovieActeurs(movieId: String)
            : DataOrException<Acteurs, Boolean, Exception> {
        val response = try {
            movieApi.getMovieActeurs(AUTH_TOKEN, movieId)

        } catch (e: Exception) {
            Log.d("Exception", "getMovies: $e")
            return DataOrException(e = e)
        }
        Log.d("RESULT", "getMovies: $response")
        return DataOrException(data = response)
    }

}