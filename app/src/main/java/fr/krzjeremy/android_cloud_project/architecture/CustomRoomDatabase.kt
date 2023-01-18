package fr.krzjeremy.android_cloud_project.architecture

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.krzjeremy.android_cloud_project.dao.PokemonDao
import fr.krzjeremy.android_cloud_project.model.PokemonRoom

@Database(
    entities = [
        PokemonRoom::class
    ],
    version = 2,
    exportSchema = false
)

abstract class CustomRoomDatabase() : RoomDatabase(){
    abstract fun mPokemonDao() : PokemonDao
}
