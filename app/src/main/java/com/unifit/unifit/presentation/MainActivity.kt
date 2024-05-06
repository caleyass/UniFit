package com.unifit.unifit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.unifit.unifit.R
import com.unifit.unifit.data.remote.FirebaseApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    @Inject lateinit var firebaseApi: FirebaseApi

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        CoroutineScope(Dispatchers.IO).launch {
            /*firebaseApi.getFitnessProgramsWorkouts("Abdomen")
                .get()
                .await()
                .map { documentSnapshot ->
                    val d = documentSnapshot.data["Warm-up"].toString()
                    if (d != "null") {
                        d.substring(1, d.length - 1).split(", ").forEach {
                            Log.d("Firebase", "ref $it")
                            firebaseApi.getWorkoutExercises()
                                .get()
                                .await()
                                .map { documentSnapshot ->
                                    Log.d("Firebase", "${documentSnapshot.data["gif"]} ${documentSnapshot.data["time"]}")
                                }
                        }

                    }

                }*/

        }

    }



}