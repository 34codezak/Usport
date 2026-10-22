package com.example.usport.data.remote.dto

// data/remote/dto/CursorResponse.kt
data class CursorResponse<T>(
    val results: List<T>,
    val nextCursor: String? = null, // Null means no more pages
    val previousCursor: String? = null
)