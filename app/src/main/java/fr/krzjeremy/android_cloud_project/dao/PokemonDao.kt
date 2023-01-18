package fr.krzjeremy.android_cloud_project.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.krzjeremy.android_cloud_project.model.PokemonRoom

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_object_table ORDER BY name ASC")
    fun selectAll(): LiveData<List<PokemonRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonRoom)

    @Delete
    fun delete(pokemon: PokemonRoom)

    @Query("DELETE FROM pokemon_object_table")
    fun deleteAll()
}