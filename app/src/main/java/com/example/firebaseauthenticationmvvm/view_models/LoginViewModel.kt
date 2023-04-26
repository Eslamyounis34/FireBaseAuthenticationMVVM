package com.example.firebaseauthenticationmvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseauthenticationmvvm.repo.AuthRepository
import com.example.firebaseauthenticationmvvm.utils.Resource
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private val authRepo: AuthRepository

    init {
        authRepo = AuthRepository()
    }

fun loginWithEmail(email: String, password: String): LiveData<Resource<FirebaseUser>> {
    return authRepo.loginWithEmail(email, password)
}

}