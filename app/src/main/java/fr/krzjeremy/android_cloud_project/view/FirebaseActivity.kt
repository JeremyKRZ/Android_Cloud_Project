package fr.krzjeremy.android_cloud_project.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.krzjeremy.android_cloud_project.R
import fr.krzjeremy.android_cloud_project.databinding.ActivityFirebaseBinding
import fr.krzjeremy.android_cloud_project.databinding.ActivityMainBinding

class FirebaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirebaseBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.FirebaseActivityButtonInscription.setOnClickListener {
            generateFirebaseInscriptionIntentAndGoTo()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            generateFirebaseHomeIntentAndGoTo()
        }
    }

    private fun generateFirebaseHomeIntentAndGoTo() {
        val intent = Intent(this, FirebaseHomeActivity::class.java)
        startActivity(intent)
    }
    private fun generateFirebaseInscriptionIntentAndGoTo() {
        val intent = Intent(this, FirebaseInscriptionActivity::class.java)
        startActivity(intent)
    }

    private fun signIn(email : String, password :  String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(CustomNotificationManager.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    generateFirebaseHomeIntentAndGoTo()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(CustomNotificationManager.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    generateFirebaseHomeIntentAndGoTo()
                }
            }
    }
}