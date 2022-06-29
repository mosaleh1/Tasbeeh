package com.runcode.tasbee7.hadith_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.runcode.tasbee7.data.HadithRepository
import com.runcode.tasbee7.data.model.Hadith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.security.AccessControlContext

class HadithViewModel(private val repository: HadithRepository) : ViewModel() {



    suspend fun getRandomHadith(): Hadith {
       return if (repository.hadithCount() == 0) {
            repository.insertAll(repository.getChapter().AllChapters)
           repository.getRandom()
        }else{
           repository.getRandom()
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun isFirstTime(context: Context) {

    }

}