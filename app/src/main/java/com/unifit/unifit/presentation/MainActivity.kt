package com.unifit.unifit.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unifit.unifit.R
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.repository.FitnessRepositoryImpl
import com.unifit.unifit.domain.repositories.FitnessRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    @Inject lateinit var firebaseApi: FirebaseApi
    @Inject lateinit var fitnessRepositoryImpl: FitnessRepositoryImpl
    var mAuth = Firebase.auth

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "onActivityResult: ")
        //if (requestCode == 123) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
                //}
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, *//*accessToken=*//* null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    Log.d("TAG", "firebaseAuthWithGoogle: $user")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("TAG", "firebaseAuthWithGoogle: ${task.exception}")
//                    Toast.makeText(this@MainActivity, "Authentication Failed.", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        /*CoroutineScope(Dispatchers.IO).launch {
            fitnessRepositoryImpl.getFitnessProgramExercises("Abdomen", "Ab Burn Circuit", "Main")
                .collect{
                    it.data?.forEach {
                        Log.d("Firebase", "onCreate: ${it.name}")
                    }
                }

        }*/

    }





}