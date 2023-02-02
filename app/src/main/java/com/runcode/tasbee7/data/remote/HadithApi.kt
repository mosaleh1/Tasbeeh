package com.runcode.tasbee7.data.remote

import com.runcode.tasbee7.data.model.hadith.Chapter
import retrofit2.http.GET


interface HadithApi {

    @GET("api/ahadith/all/ar-tashkeel")
    suspend fun getChapter (): Chapter

}