package com.test.example.firebasedemo

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.test.example.firebasedemo.databinding.ActivityLoginBinding
import com.test.example.firebasedemo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            createUser()
        }
        binding.btnRegister.setOnClickListener {
            startActivity(
                Intent(
                    this@RegisterActivity,
                        LoginActivity::class.java
                )
            )
        }
    }

    private fun createUser() {
        val email = binding.etRegEmail.text.toString()
        val password = binding.etRegPass.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etRegEmail.error = "Email cannot be empty";
            binding.etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            binding.etRegPass.error = "Password cannot be empty";
            binding.etRegPass.requestFocus();
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT)
                            .show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}