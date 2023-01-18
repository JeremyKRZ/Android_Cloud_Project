package fr.krzjeremy.android_cloud_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.krzjeremy.android_cloud_project.databinding.ActivityMainBinding
import fr.krzjeremy.android_cloud_project.view.FirebaseActivity
import fr.krzjeremy.android_cloud_project.view.RecyclerViewActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RecyclerViewActivityButton.setOnClickListener {
            generateRecyclerViewIntentAndGoTo()
        }

        binding.FirebaseActivityButton.setOnClickListener {
            generateFirebaseIntentAndGoTo()
        }
    }
    private fun generateRecyclerViewIntentAndGoTo() {
        val intent = Intent(this, RecyclerViewActivity::class.java)
        startActivity(intent)
    }
    private fun generateFirebaseIntentAndGoTo() {
        val intent = Intent(this, FirebaseActivity::class.java)
        startActivity(intent)
    }

}