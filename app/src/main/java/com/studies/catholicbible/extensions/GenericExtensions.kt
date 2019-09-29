package com.studies.catholicbible.extensions

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T> ArrayList<T>.filterArray(query: String): ArrayList<T>{
    return if (query.isNotBlank()) this.filter {
        it.containsInto(query)
    } as ArrayList<T> else this
}

inline fun <reified T> T.containsInto(query: String): Boolean {
    for(field in T::class.java.declaredFields){
        field.isAccessible = true
        field.get(this).let { value ->
            val attribute = when (value){
                is String -> value
                is Int -> value.toString()
                is Long -> value.toString()
                is CharSequence -> value.toString()
                is Float -> value.toString()
                is Double -> value.toString()
                is Char -> value.toString()
                is Short -> value.toString()
                is Boolean -> value.toString()
                is Serializable -> value.toString()
                is Bundle -> value.toString()
                is Parcelable -> value.toString()
                is Byte -> value.toString()
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> value.toString()
                    value.isArrayOf<String>() -> value.toString()
                    value.isArrayOf<Parcelable>() -> value.toString()
                    else -> ""
                }
                is IntArray -> value.toString()
                is LongArray -> value.toString()
                is FloatArray -> value.toString()
                is DoubleArray -> value.toString()
                is CharArray -> value.toString()
                is ShortArray -> value.toString()
                is BooleanArray -> value.toString()
                is ByteArray -> value.toString()
                else -> ""
            }
            if (attribute.contains(query, true)){
                return true
            }
        }
    }
    return false
}