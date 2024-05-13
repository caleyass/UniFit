package com.unifit.unifit.presentation

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unifit.unifit.R
import com.unifit.unifit.data.local.database.MyDatabase
import com.unifit.unifit.data.local.entity.AlarmEntity
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.data.repository.FitnessRepositoryImpl
import com.unifit.unifit.domain.repositories.FitnessRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    @Inject lateinit var firebaseApi: FirebaseApi
    @Inject lateinit var fitnessRepositoryImpl: FitnessRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val pillDao = MyDatabase.getDatabase(this@MainActivity).pillDao

        lifecycleScope.launch {
            val alarmDao = MyDatabase.getDatabase(this@MainActivity).alarmDao
            val pill = PillEntity(
                name = "fdf",
                dose = 2,
                endDate = LocalDate.of(2025,5,12)
            )
            launch(Dispatchers.IO) {


                pillDao.clearAllPills()
                pillDao.insertPill(
                    PillEntity(
                        name = "Ibuprofen",
                        dose = 2,
                        endDate = LocalDate.of(2024,5,12)
                    )
                )

                val alarms = listOf(AlarmEntity( hour = 12, minute = 0, isOn = true, label = "Lunch Alarm", text = "Time for lunch!"))
                pillDao.insertPillWithAlarms(
                    pill,
                    alarms
                )
                launch {
                pillDao.getAllPills().collect {
                    it.forEach {
                        Log.d("Pill", it.toString())
                    }
                    return@collect
                }
                    return@launch
                }
                Log.d("Pill", "${pillDao.getPillById("Ibuprofen")} ")

            }

            launch(Dispatchers.IO) {

                alarmDao.clearAllAlarms()

                alarmDao.insert(AlarmEntity(objectId = 13, hour = 8, minute = 0, isOn = true, label = "Morning Alarm", text = "Wake up!"),)

                alarmDao.getAllAlarms().collect {
                    it.forEach {
                        Log.d("Pill", it.toString())
                    }
                    return@collect
                }


            }
            launch(Dispatchers.IO) {
            }
            }
    }


}