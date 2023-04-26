package com.example.firebaseauthenticationmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.firebaseauthenticationmvvm.databinding.ActivityLoginBinding
import com.example.firebaseauthenticationmvvm.utils.Resource
import com.example.firebaseauthenticationmvvm.view_models.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        var email = "eslam@gmail.com"
        var password = "123456"
        viewModel.loginWithEmail(email, password).observe(this) { result ->
            when (result) {
                is Resource.Success -> {
                    // Login successful, navigate to home screen
                    Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show()

                }
                is Resource.Error -> {
                    // Login failed, show error message
                    Toast.makeText(this, result.exception.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    // Show progress bar
                    Toast.makeText(this, "LOADING", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}