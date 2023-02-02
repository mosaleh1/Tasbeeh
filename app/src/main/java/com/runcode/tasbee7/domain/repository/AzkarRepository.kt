package com.runcode.tasbee7.domain.repository

import com.runcode.tasbee7.ui.Azkar.Zikr
import io.reactivex.Observable

interface AzkarRepository
{

    fun insertZikr(zikr: Zikr)

    fun getMorningAzkar(): Observable<List<Zikr?>>

    fun getEveningAzkar(): Observable<List<Zikr?>>
}