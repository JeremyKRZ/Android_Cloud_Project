package fr.krzjeremy.android_cloud_project.model

sealed class PokemonItemGeneric()

data class PokemonItemHeader(
    val header: String
) : PokemonItemGeneric()

data class PokemonItemFooter(
    val footer: String
) : PokemonItemGeneric()

data class PokemonItem(
    val pokemonName : String,
    val pokemonNumber : Int,
    val pokemonWeight: Int,
    val pokemonImage: String,
    val pokemonTimestamp: String
) : PokemonItemGeneric()