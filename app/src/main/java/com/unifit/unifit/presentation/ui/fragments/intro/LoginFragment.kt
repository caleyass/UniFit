package com.unifit.unifit.presentation.ui.fragments.intro

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unifit.unifit.R
import com.unifit.unifit.databinding.FragmentLoginBinding
import com.unifit.unifit.presentation.ui.fragments.fitness.FitnessExerciseFragmentDirections

class LoginFragment : Fragment() {
    val RC_SIGN_IN = 123
    var mAuth = Firebase.auth
    private var binding : FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth

    private var etEmail : EditText? = null
    private var etPassword : EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "onActivityResult: ")
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let { firebaseAuthWithGoogle(it) }
        } catch (e: ApiException) {
            Log.d("TAG", "error ${e}")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        grantPostNotificationPermission()

        auth = Firebase.auth

        etEmail = binding?.etEmail
        etPassword = binding?.etPassword

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

        binding?.signInButtonGoogle?.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 123)
        }

        binding?.login?.setOnClickListener {
            val email = etEmail?.text.toString()
            val password = etPassword?.text.toString()
            if (checkEmailAndPassword(email, password)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this.requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser

                            Log.d("TAG", "signInWithEmail:success $user")

                            //updateUI(user)
                            navigateToTest()
                        } else {

                        }
                    }
                    .addOnFailureListener { e ->
                        if (e.message.toString().contains("password")) {
                            etPassword?.error = e.message
                        } else if (e.message.toString().contains("email")) {
                            etEmail?.error = e.message
                        } else {
                            showToastMessage(e.message.toString())
                        }

                        Log.d("TAG", "signInWithEmail:failure $e")
                        //showToastMessage("Authentication failed")
                    }
            }
        }
    }


    private fun checkEmailAndPassword(email: String?, password: String?) : Boolean {
        var isValid = true
        if (email.isNullOrEmpty()) {
            etEmail?.error = "Email is required."
            isValid = false
        }
        if (password.isNullOrEmpty()) {
            etPassword?.error = "Password is required."
            isValid = false
        }
        return isValid
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, /*accessToken=*/ null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    Log.d("TAG", "firebaseAuthWithGoogle: $user")
                    navigateToTest()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("TAG", "firebaseAuthWithGoogle: ${task.exception}")
//                    Toast.makeText(this@MainActivity, "Authentication Failed.", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }

    private fun grantPostNotificationPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
    }

    private fun navigateToTest(){
        val action = LoginFragmentDirections.actionLoginFragmentToTestFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}