package com.kolaemiola.core.model

data class Error(val message: String, val throwable: Throwable? = null)
