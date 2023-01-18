package fr.krzjeremy.android_cloud_project.remote

import fr.krzjeremy.android_cloud_project.model.PokemonRetrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonEndpoint {
    @GET("{id}")
    suspend fun getRandomPokemon(@Path("id") id : Int) : PokemonRetrofit

    @GET("?limit={limit}")
    suspend fun getAllPokemon(@Path("limit") limit: Int) : PokemonRetrofit
}