package com.lumins.sua.firebase_setup

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseHelper {

    fun initialize() {
        val messaging = FirebaseMessaging.getInstance()
        messaging.token.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("FirebaseHelper", "initialize: FCM Token= ${it.result}")

                val db = FirebaseFirestore.getInstance()
                // add the token to the all_users doc in the users collection to the array of tokens
                db.collection("users").document("all_users")
                    .update("fcm_tokens", FieldValue.arrayUnion(it.result))
            }
        }
    }


}