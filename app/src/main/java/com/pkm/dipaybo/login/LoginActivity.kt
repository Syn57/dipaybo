package com.pkm.dipaybo.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pkm.dipaybo.MainActivity
import com.pkm.dipaybo.R
import com.pkm.dipaybo.databinding.ActivityLoginBinding
import com.pkm.dipaybo.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    private var resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun firebaseSignIn() {
        // Initialize Firebase Auth
        auth = Firebase.auth
        val mAuth = FirebaseAuth.getInstance()

        //Using google button
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(applicationContext, gso)
        binding.btnLoginFirebase.setOnClickListener {
            signIn()
        }

        //Using email and password
        val email = binding.edtEmailLog.text.toString()
        val pass = binding.edtPassLog.text.toString()
        signInEmailPass()
    }

    private fun signInEmailPass() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLog.text.toString()
            val pass = binding.edtPassLog.text.toString()
            if (email.isEmpty() && pass.isEmpty()){
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Login failed: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        firebaseSignIn()
    }

    companion object {
        private const val TAG = "Login Activity"
    }
}