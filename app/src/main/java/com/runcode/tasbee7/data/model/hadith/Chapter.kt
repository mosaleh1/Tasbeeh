package com.runcode.tasbee7.data.model.hadith

data class Chapter(
    val AllChapters: List<Hadith>,
    val code: Int,
    var isFavorite: Boolean = false
)