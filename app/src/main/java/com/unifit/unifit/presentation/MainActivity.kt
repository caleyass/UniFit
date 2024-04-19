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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        /*CoroutineScope(Dispatchers.Main).launch {
            firebaseApi.getFitnessPrograms(limit = 3)
            delay(4000)
            firebaseApi.getFitnessPrograms(limit = 3)


        }*/

    }



}