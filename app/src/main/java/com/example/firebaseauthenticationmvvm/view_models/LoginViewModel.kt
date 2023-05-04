package com.example.firebaseauthenticationmvvm.view_models

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseauthenticationmvvm.repo.AuthRepository
import com.example.firebaseauthenticationmvvm.utils.Resource
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {


    val signInResultLiveData = authRepository.signInResultLiveData

    fun signIn() {
        authRepository.signIn()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authRepository.onActivityResult(requestCode, resultCode, data)
    }

    fun loginWithEmail(email: String, password: String): LiveData<Resource<FirebaseUser>> {
        return authRepository.loginWithEmail(email, password)
    }






}