package fr.krzjeremy.android_cloud_project.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.krzjeremy.android_cloud_project.R
import fr.krzjeremy.android_cloud_project.databinding.ItemCustomRecyclerFooterBinding
import fr.krzjeremy.android_cloud_project.databinding.ItemCustomRecyclerHeaderBinding
import fr.krzjeremy.android_cloud_project.databinding.PokemonItemRecyclerBinding
import fr.krzjeremy.android_cloud_project.model.PokemonItem
import fr.krzjeremy.android_cloud_project.model.PokemonItemFooter
import fr.krzjeremy.android_cloud_project.model.PokemonItemGeneric
import fr.krzjeremy.android_cloud_project.model.PokemonItemHeader
import java.util.*

private val diffItemUtils = object : DiffUtil.ItemCallback<PokemonItemGeneric>() {
    override fun areItemsTheSame(oldItem: PokemonItemGeneric, newItem: PokemonItemGeneric): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: PokemonItemGeneric, newItem: PokemonItemGeneric): Boolean {
        return oldItem == newItem
    }
}

class PokemonAdapter(private val onItemClick: (quoteUi: PokemonItem, view: View) -> Unit) : ListAdapter<PokemonItemGeneric, RecyclerView.ViewHolder>(diffItemUtils) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {

            MyItemType.ROW.type -> {
                PokemonViewHolder(
                    PokemonItemRecyclerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onItemClick
                )
            }

            MyItemType.HEADER.type -> {
                PokemonHeaderViewHolder(
                    ItemCustomRecyclerHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            MyItemType.FOOTER.type -> {
                PokemonFooterViewHolder(
                    ItemCustomRecyclerFooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw RuntimeException("Wrong view type received $viewType")
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            MyItemType.ROW.type -> (holder as PokemonViewHolder).bind(getItem(position) as PokemonItem)
            MyItemType.HEADER.type -> (holder as PokemonHeaderViewHolder).bind(getItem(position) as PokemonItemHeader)
            MyItemType.FOOTER.type -> (holder as PokemonFooterViewHolder).bind(getItem(position) as PokemonItemFooter)
            else -> throw RuntimeException("Wrong view type received ${holder.itemView}")
        }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokemonItem -> MyItemType.ROW.type
            is PokemonItemHeader -> MyItemType.HEADER.type
            is PokemonItemFooter -> MyItemType.FOOTER.type
        }
    }
}

class PokemonViewHolder(
    private val binding: PokemonItemRecyclerBinding,
    onItemClick: (pokemonItem: PokemonItem, view: View) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var ui: PokemonItem

    init {
        binding.root.setOnClickListener {
            onItemClick(ui, itemView)
        }
    }

    fun bind(pokemonItem: PokemonItem) {
        ui = pokemonItem
        binding.itemRecyclerViewPokemonName.text =
            pokemonItem.pokemonName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        binding.itemRecyclerViewPokemonNumber.text = "id : " + pokemonItem.pokemonNumber.toString()
        binding.itemRecyclerViewPokemonWeight.text = pokemonItem.pokemonWeight.toString() + " kg"
        binding.itemRecyclerViewPokemonTimestamp.text = pokemonItem.pokemonTimestamp
        Glide.with(itemView.context)
            .load(pokemonItem.pokemonImage)
            .placeholder(R.color.ic_launcher_background)
            .into(binding.itemRecyclerViewPokemonImage)
    }
}

class PokemonHeaderViewHolder(
    private val binding: ItemCustomRecyclerHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonItemHeader: PokemonItemHeader) {
        binding.itemRecyclerViewHeader.text = pokemonItemHeader.header
    }
}

class PokemonFooterViewHolder(
    private val binding: ItemCustomRecyclerFooterBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonItemFooter: PokemonItemFooter) {
        binding.itemRecyclerViewFooter.text = pokemonItemFooter.footer
    }
}

enum class MyItemType(val type: Int) {
    ROW(0),
    HEADER(1),
    FOOTER(2)
}