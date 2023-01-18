package fr.krzjeremy.android_cloud_project.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import fr.krzjeremy.android_cloud_project.architecture.CustomApplication
import fr.krzjeremy.android_cloud_project.architecture.RetrofitBuilder
import fr.krzjeremy.android_cloud_project.model.PokemonDomain
import fr.krzjeremy.android_cloud_project.model.PokemonRetrofit
import fr.krzjeremy.android_cloud_project.model.PokemonRoom
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class PokemonRepository {
    private val mPokemonDao = CustomApplication.instance.mApplicationDatabase.mPokemonDao()

    fun selectAllPokemon() : LiveData<List<PokemonDomain>> {
        return mPokemonDao.selectAll().map { list ->
            list.fromRoomToDomain()
        }
    }

    private fun insertPokemon(pokemonRoom: PokemonRoom) {
        mPokemonDao.insert(pokemonRoom)
    }

    fun deletePokemon(pokemonRoom: PokemonRoom) {
        mPokemonDao.delete(pokemonRoom)
    }

    suspend fun fetchData() {
        val randomGenerator = Random(System.currentTimeMillis())
        val random = randomGenerator.nextInt(1, 600)
        insertPokemon(RetrofitBuilder.getPokemon().getRandomPokemon(random).fromRetrofitToRoom())
    }

    fun deleteAllPokemon() {
        mPokemonDao.deleteAll()
    }
}

private fun PokemonRetrofit.fromRetrofitToRoom():PokemonRoom {
    val currentTimestamp = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    return PokemonRoom(
        number= number,
        name= name,
        weight=weight,
        image=image,
        timestamp=currentTimestamp
    )
}

private fun List<PokemonRoom>.fromRoomToDomain(): List<PokemonDomain> {
    return map { eachItem ->
        PokemonDomain(
            pokedexNumber = eachItem.number,
            name = eachItem.name,
            weight = eachItem.weight,
            imageURL = eachItem.image,
            timestamp = eachItem.timestamp
        )
    }
}