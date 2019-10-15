package com.studies.bibleapplication.model.entity

import java.io.Serializable

data class User(var id: Long,
                var name: String,
                var phone: String,
                var email: String,
                var password: String): Serializable