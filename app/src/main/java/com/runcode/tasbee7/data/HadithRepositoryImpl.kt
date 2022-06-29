package com.runcode.tasbee7.data

import com.runcode.tasbee7.data.database.HadithDao
import com.runcode.tasbee7.data.remote.HadithApi

class HadithRepositoryImplemtation(val api: HadithApi,val database:HadithDao) {
}