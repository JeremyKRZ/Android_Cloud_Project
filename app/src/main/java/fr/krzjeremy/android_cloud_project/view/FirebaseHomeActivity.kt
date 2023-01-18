package fr.krzjeremy.android_cloud_project.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.krzjeremy.android_cloud_project.MainActivity
import fr.krzjeremy.android_cloud_project.databinding.ActivityFirebaseBinding
import fr.krzjeremy.android_cloud_project.databinding.ActivityFirebaseHomeBinding

class FirebaseHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirebaseHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            /* val name = user.displayName*/
            val email = user.email
            /* val photoUrl = user.photoUrl*/

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            binding.FirebaseHomeActivityTextViewUserUID.text = "uid : " + uid
            binding.FirebaseHomeActivityTextViewUserEmail.text = "email : " + email
        }
        if(user == null){
            generateFirebaseIntentAndGoTo()
        }
        binding.FirebaseHomeActivityButtonMenu.setOnClickListener {
            generateMainIntentAndGoTo()
        }
        binding.FirebaseHomeActivityButtonDeconnexion.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            generateMainIntentAndGoTo()
        }
    }
    private fun generateMainIntentAndGoTo() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun generateFirebaseIntentAndGoTo() {
        val intent = Intent(this, FirebaseActivity::class.java)
        startActivity(intent)
    }
}