package com.runcode.tasbee7.data.model.hadith

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hadith(
    val Ar_Sanad_1: String,
    val Ar_Text: String,
    val Book_ID: Int,
    val Chapter_ID: Int,
    @PrimaryKey
    val Hadith_ID: Int
)
{
    override fun toString(): String {
        return "$Ar_Sanad_1\n$Ar_Text"
    }
}