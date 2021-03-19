package com.example.core.model

data class Error(val message: String, val throwable: Throwable? = null)
