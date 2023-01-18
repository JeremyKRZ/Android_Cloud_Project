package fr.krzjeremy.android_cloud_project.architecture

import com.google.gson.GsonBuilder
import fr.krzjeremy.android_cloud_project.remote.PokemonEndpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/pokemon/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    fun getPokemon(): PokemonEndpoint = retrofit.create(PokemonEndpoint::class.java)
}