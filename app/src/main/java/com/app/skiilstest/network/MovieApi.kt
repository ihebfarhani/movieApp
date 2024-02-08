package com.app.skiilstest.network

import com.app.skiilstest.model.Acteurs
import com.app.skiilstest.model.Movies
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface MovieApi {

    @GET("movie/popular")
    suspend fun getMovies(
        @Header("Authorization") authorization: String
    ): Movies

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActeurs(
        @Header("Authorization") authorization: String,
        @Path("movie_id") movieId: String
    ): Acteurs

}