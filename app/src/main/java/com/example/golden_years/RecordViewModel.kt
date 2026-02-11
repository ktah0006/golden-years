package com.example.golden_years

import com.example.golden_years.record_room.HealthRecord
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.golden_years.record_room.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


// taken and adapted from Lab Week 4
class RecordViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: RecordRepository
    init {
        cRepository = RecordRepository(application)
    }

    fun getRecords(userId: String): Flow<List<HealthRecord>> {
        return cRepository.getRecords(userId)
    }
    fun insertRecord(record: HealthRecord) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(record)
    }
    fun updateRecord(userId: String,record: HealthRecord) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(userId, record)
    }
    fun deleteRecord(record: HealthRecord) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(record)
    }

    val currEditingRecord = MutableStateFlow<HealthRecord?>(null)
    fun setRecordToEdit(recordToEdit: HealthRecord) {
        currEditingRecord.value = recordToEdit
    }

    fun clearEditingRecord() {
        currEditingRecord.value = null
    }


    fun getRecentRecords(userId: String): Flow<List<HealthRecord>> {
        return cRepository.getRecentRecords(userId)
    }
}

