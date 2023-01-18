package fr.krzjeremy.android_cloud_project.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.krzjeremy.android_cloud_project.R
import fr.krzjeremy.android_cloud_project.databinding.ActivityRecyclerViewBinding
import fr.krzjeremy.android_cloud_project.model.PokemonItem
import fr.krzjeremy.android_cloud_project.model.PokemonItemGeneric
import fr.krzjeremy.android_cloud_project.viewmodel.PokemonViewModel

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var adapter: PokemonAdapter
    private lateinit var viewModel: PokemonViewModel

    private val pokemonListObserver = Observer<List<PokemonItemGeneric>> {
        adapter.submitList(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokemonViewModel::class.java]

        // Create the instance of adapter
        adapter = PokemonAdapter{ item, view ->
            onItemClick(item,view)
        }

        // We define the type of linear layout
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // We set the adapter to recycler view
        binding.recyclerView.adapter = adapter

        binding.addItemButton.setOnClickListener{addRandomPokemon()}
        binding.deleteAllItemButton.setOnClickListener { deleteAllPokemon() }
    }
    override fun onStart() {
        super.onStart()
        viewModel.pokemonList.observe(this, pokemonListObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.pokemonList.removeObserver(pokemonListObserver)
    }

    private fun onItemClick(pokemonObjectDataSample: PokemonItem, view : View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        //Toast.makeText(this, pokemonObjectDataSample.pokemonName, Toast.LENGTH_SHORT).show()
        with(CustomNotificationManager(this)) {
            createNotificationCompatBuilder(pokemonObjectDataSample)
        }
    }

    private fun addRandomPokemon() {
        viewModel.insertPokemon()
    }

    private fun deletePokemon() {
        /*viewModel.deletePokemon()*/
    }

    private fun deleteAllPokemon() {
        viewModel.deleteAllPokemon()
    }
}

class CustomNotificationManager(private val context: Context) {

    companion object {
        const val TAG = "CustomNotificationManager"
        private const val CHANNEL_ID = "demo_purpose"
        private const val CHANNEL_NAME = "demo purpose channel id"
        private const val CHANNEL_DESCRIPTION = "This channel will received only demo notification"
        private const val REQUEST_CODE = 94043
        private const val NOTIFICATION_ID = 42
    }

    /** Notification manager*/
    private val mNotificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (!channelNotificationExists()) {
            createNowPlayingChannel()
        }
    }

    fun createNotificationCompatBuilder(pokemonObjectDataSample: PokemonItem) {
        val notificationCompat = NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setContentTitle(pokemonObjectDataSample.pokemonName)
            .setContentText("id : " + pokemonObjectDataSample.pokemonNumber.toString())
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("id : " + pokemonObjectDataSample.pokemonNumber.toString())
            )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, notificationCompat.build())
        }
    }

    /**
     * Check if channel is already created
     */
    private fun channelNotificationExists() = mNotificationManager.getNotificationChannel(CHANNEL_ID) != null

    /**
     * Create the cancel id for notification
     */
    private fun createNowPlayingChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESCRIPTION
        }
        mNotificationManager.createNotificationChannel(notificationChannel)
    }
}