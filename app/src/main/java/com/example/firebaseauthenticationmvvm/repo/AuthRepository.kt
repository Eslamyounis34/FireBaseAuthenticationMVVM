package com.example.firebaseauthenticationmvvm.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseauthenticationmvvm.utils.Resource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {
    private val auth = Firebase.auth

    fun loginWithEmail(email: String, password: String): LiveData<Resource<FirebaseUser>> {
        val result = MutableLiveData<Resource<FirebaseUser>>()
        result.value = Resource.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.value = Resource.Success(auth.currentUser!!)
                } else {
                    result.value = Resource.Error(task.exception!!)
                }
            }
        return result
    }
}