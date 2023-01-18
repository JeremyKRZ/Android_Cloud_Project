package fr.krzjeremy.android_cloud_project.model

import java.sql.Timestamp

data class PokemonDomain (
    val name : String,
    val pokedexNumber : Int,
    val weight: Int,
    val imageURL: String,
    val timestamp: String
)