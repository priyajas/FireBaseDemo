package com.test.example.firebasedemo

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.test.example.firebasedemo.databinding.ActivityLoginBinding
import android.content.Intent




class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            loginUser();
        }
        binding.btnRegister.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }


    }

    private fun loginUser() {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.inputEmail.error = "Email cannot be empty";
            binding.inputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            binding.inputPassword.error = "Password cannot be empty";
            binding.inputPassword.requestFocus();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "User logged in successfully", Toast.LENGTH_SHORT)
                            .show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

}