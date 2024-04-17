package com.unifit.unifit

import com.unifit.unifit.data.remote.FirebaseApi
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Inject
    lateinit var firebaseApi:FirebaseApi

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        println(firebaseApi.getImage("fitness_program/arm_program.jpg"))
    }
}