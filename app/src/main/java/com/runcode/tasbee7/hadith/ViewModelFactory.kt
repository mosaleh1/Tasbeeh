package com.runcode.tasbee7.hadith_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.runcode.tasbee7.data.HadithRepository
import com.runcode.tasbee7.data.HadithRepositoryImpl
import com.runcode.tasbee7.data.database.HadithDao
import com.runcode.tasbee7.data.remote.HadithApi

class ViewModelFactory private constructor(private val taskRepository: HadithRepository) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(api: HadithApi, dao: HadithDao): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    HadithRepositoryImpl(api, dao)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HadithViewModel::class.java) -> {
                HadithViewModel(taskRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}