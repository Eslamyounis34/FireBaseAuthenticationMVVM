package com.example.firebaseauthenticationmvvm.repo

import android.app.Activity
import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseauthenticationmvvm.R
import com.example.firebaseauthenticationmvvm.utils.Resource
import com.example.firebaseauthenticationmvvm.utils.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository(private val activity: Activity) {
    private val auth = Firebase.auth



    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("112881952291-o4r8979u0r95andub88btbkcqrajjcla.apps.googleusercontent.com")
        .requestEmail()
        .build()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient =
        GoogleSignIn.getClient(activity, gso)


    val signInResultLiveData = MutableLiveData<SignInResult>()

    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = result.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                signInResultLiveData.postValue(SignInResult.Error(e.localizedMessage))
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    signInResultLiveData.postValue(SignInResult.Success(task.result?.user))
                } else {
                    signInResultLiveData.postValue(SignInResult.Error(task.exception?.message))
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }


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