package com.runcode.tasbee7.ui.hadith

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runcode.tasbee7.domain.repository.HadithRepository
import com.runcode.tasbee7.data.model.hadith.Chapter
import com.runcode.tasbee7.data.model.hadith.Hadith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HadithViewModel(private val repository: HadithRepository) : ViewModel() {

    private val _hadithLiveData = MutableLiveData<Hadith>()
    val hadithLiveData: LiveData<Hadith> = _hadithLiveData

    suspend fun getRandomHadith() {
        viewModelScope.launch(Dispatchers.IO) {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val count = repository.hadithCount()
            if (count == 0) {
                //get from network
                val chapters = getFromApi().AllChapters
                //insert to db
                repository.insertAll(chapters)
                val hadith = repository.getRandom()
                _hadithLiveData.postValue(hadith)
            } else {
                val hadith = repository.getRandom()
                _hadithLiveData.postValue(hadith)
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
    }

    private suspend fun getFromApi(): Chapter {
        return repository.getChapter()
    }

    fun getHadithByID(id:Int){
        _hadithLiveData.value = repository.getHadithById(id).value
    }

}