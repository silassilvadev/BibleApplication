package com.studies.catholicbibleapplication.model.entity

import java.io.Serializable

data class Book(val abbrev: String,
                val author: String,
                val chapters: String,
                val group: String,
                val name: String,
                val testament: String): Serializable
