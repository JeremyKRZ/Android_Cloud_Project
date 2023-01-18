package fr.krzjeremy.android_cloud_project.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.krzjeremy.android_cloud_project.databinding.ActivityFirebaseBinding
import fr.krzjeremy.android_cloud_project.databinding.ActivityFirebaseInscriptionBinding
import fr.krzjeremy.android_cloud_project.view.CustomNotificationManager.Companion.TAG

class FirebaseInscriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirebaseInscriptionBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.FirebaseInscriptionActivityButtonInscription.setOnClickListener {
            checkInputs()
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

    private fun checkInputs() {
        var ok = true
        var email = ""
        var password = ""
        var confirmPassword = ""
        binding.apply {
            email = FirebaseInscriptionActivityEditTextEmail.text.toString()
            password = FirebaseInscriptionActivityEditTextPassword.text.toString()
            confirmPassword = FirebaseInscriptionActivityEditTextConfirmPassword.text.toString()
        }
        if (email == "" || password != confirmPassword){
            ok = false
        }
        if (!ok) return
        createAccount(email, password)

    }
    private fun createAccount(email : String, password :  String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    generateFirebaseHomeIntentAndGoTo()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}