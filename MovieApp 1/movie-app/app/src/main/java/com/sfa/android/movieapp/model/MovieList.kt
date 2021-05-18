package com.sfa.android.movieapp.model

object MovieList {
    private var movieList: MutableList<Movie> = mutableListOf()

    fun addMovie(movie: Movie) {
        movieList.add(movie)
    }

    fun getList(): List<Movie> {
        return movieList.toList()
    }

    fun sortMovieListByTitle() {
        movieList = movieList.sortedWith(compareBy { it.title }) as MutableList<Movie>
    }

    fun sortMovieListByReleaseDate() {
        movieList = movieList.sortedWith(compareBy { it.year }) as MutableList<Movie>
    }
}
