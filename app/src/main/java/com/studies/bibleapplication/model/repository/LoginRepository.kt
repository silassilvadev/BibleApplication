package com.studies.bibleapplication.model.repository

import com.google.firebase.database.FirebaseDatabase
import com.studies.bibleapplication.model.entity.User
import com.studies.bibleapplication.model.entity.Verse

class LoginRepository {

    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun registerUser(userRegister: User){
        databaseReference.child("user")
            .setValue(userRegister) { databaseError, databaseReference ->

        }
    }

    fun loginUser(login: String, password: String){

    }

    fun setFavorites(verses: List<Verse>){
        databaseReference.child("favorites").setValue(verses)
    }

    fun getFavorites(){

    }
}