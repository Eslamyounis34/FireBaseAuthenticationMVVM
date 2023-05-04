package com.example.firebaseauthenticationmvvm

import ViewModelFactory
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauthenticationmvvm.databinding.ActivityLoginBinding
import com.example.firebaseauthenticationmvvm.repo.AuthRepository
import com.example.firebaseauthenticationmvvm.utils.Resource
import com.example.firebaseauthenticationmvvm.utils.SignInResult
import com.example.firebaseauthenticationmvvm.view_models.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = AuthRepository(this)
        val viewModelFactory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)


        binding.loginButton.setOnClickListener {
            loginUserWithEmail()
        }

        binding.googleLoginButton.setOnClickListener {
            loginUserWithGmail()
        }

        viewModel.signInResultLiveData.observe(this, Observer { result ->
            when (result) {
                is SignInResult.Success -> {
                    // Handle sign-in success
                    Log.e("GmailLogin",result.user!!.email.toString())
                }
                is SignInResult.Error -> {
                    // Handle sign-in error
                    Log.e("GmailLogin",result.toString())

                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)

    }


    private fun loginUserWithEmail() {
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

    private fun loginUserWithGmail(){
        viewModel.signIn()

    }
}