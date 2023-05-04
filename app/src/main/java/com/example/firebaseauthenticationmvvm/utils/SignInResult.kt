package com.example.firebaseauthenticationmvvm.utils

import com.google.firebase.auth.FirebaseUser

sealed class SignInResult{
    data class Success(val user: FirebaseUser?) : SignInResult()
    data class Error(val errorMessage: String?) : SignInResult()
}
