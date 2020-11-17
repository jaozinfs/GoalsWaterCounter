package com.example.orbitmvi.utils

import android.util.Log

fun <T> List<T>.logAll(tag: String = "LogAll"){
    map {
        Log.d(tag, it.toString())
    }
}