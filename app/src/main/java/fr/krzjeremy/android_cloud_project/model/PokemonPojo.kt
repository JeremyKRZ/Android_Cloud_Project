package fr.krzjeremy.android_cloud_project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.sql.Timestamp

data class PokemonRetrofit(
    @Expose
    @SerializedName("id")
    val number: Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("weight")
    val weight: Int,

    @Expose
    @SerializedName("sprites")
    @JsonAdapter(SpriteDeserializer::class)
    val image: String
)

class SpriteDeserializer : JsonDeserializer<String?> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        //return json.asJsonObject["other"].asJsonObject["official-artwork"].asJsonObject["front_default"].asString
        return json.asJsonObject["front_default"].asString
    }
}


@Entity(tableName = "pokemon_object_table")
data class PokemonRoom(
    @ColumnInfo(name = "number")
    val number: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "weight")
    val weight: Int,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}