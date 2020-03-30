package com.studies.bibleapplication.entity

import java.io.Serializable

data class User(var id: Long,
                var name: String,
                var phone: String,
                var email: String,
                var password: String): Serializable