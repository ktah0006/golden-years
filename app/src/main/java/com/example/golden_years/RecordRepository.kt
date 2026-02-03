package com.example.golden_years

import HealthRecord
import android.app.Application
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class RecordRepository(application: Application) {
    private val db = FirebaseFirestore.getInstance()

    private val recordDao: RecordDAO =
        RecordDatabase.getDatabase(application).recordDAO()

    fun getRecords(userId: String): Flow<List<HealthRecord>> {
        return recordDao.getUserRecords(userId)
    }

    fun getRecordsFirestore(userId: String){

        db.collection("users")
            .document(userId)
            .collection("records")
            .get()
            .addOnSuccessListener { snapshot ->
                CoroutineScope(Dispatchers.IO).launch {
                    for (singleRecord in snapshot.documents) {
                        val recordToAdd = HealthRecord(
                            userId = userId,
                            bpSystolic = singleRecord.getLong("bpSystolic")?.toInt() ?: 0,
                            bpDiastolic = singleRecord.getLong("bpDiastolic")?.toInt() ?: 0,
                            glucose = singleRecord.getLong("glucose")?.toInt() ?: 0,
                            mealTiming = singleRecord.getString("mealTiming") ?: "",
                            createdAt = singleRecord.getTimestamp("createdAt")?.toDate()?.time ?: 0L,
                            firestoreSynced = true,
                            firestoreId = singleRecord.id
                        )
                        recordDao.insertRecord(recordToAdd)
                        Log.d("FIRESTORE_RETRIEVAL", "SUCCESS")
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE_RETRIEVAL", "could not get records from firestore", e)
            }
    }

    suspend fun insert(record: HealthRecord) {

        try {

            val recordForFirestore = mapOf(
                "userId" to record.userId,
                "bpSystolic" to record.bpSystolic,
                "bpDiastolic" to record.bpDiastolic,
                "glucose" to record.glucose,
                "mealTiming" to record.mealTiming,
                "createdAt" to Timestamp(Date(record.createdAt))
            )

            val recordsDocRef = db.collection("users")
                .document(record.userId)
                .collection("records")
                .add(recordForFirestore)
                .await()

            val recordWithFirestoreId = record.copy(
                firestoreSynced = true,
                firestoreId = recordsDocRef.id
            )

            recordDao.insertRecord(recordWithFirestoreId)


        } catch (e: Exception){
            Log.e("RecordRepository", "Failed to sync record", e)
        }
    }

    suspend fun update(
        userId: String,
        record: HealthRecord
    ) {
        try {
            val recFirestoreId = record.firestoreId

            val recordForFirestore = mapOf(
                "userId" to record.userId,
                "bpSystolic" to record.bpSystolic,
                "bpDiastolic" to record.bpDiastolic,
                "glucose" to record.glucose,
                "mealTiming" to record.mealTiming,
                "createdAt" to Timestamp(Date(record.createdAt))
            )

            Log.d("RecordRepository", "is firestore record null ${recFirestoreId != null}")
            if (recFirestoreId != null) {
                db.collection("users")
                    .document(userId)
                    .collection("records")
                    .document(recFirestoreId)
                    .update(recordForFirestore)
                    .await()
            }

            recordDao.updateRecord(record)

            Log.d("RecordRepository", "Record updated in Firestore")

        } catch(e: Exception) {
            Log.e("RecordRepository", "could not update record", e)
        }
    }

    suspend fun delete(record: HealthRecord) {

        try {
            val recFirestoreId = record.firestoreId
            if (recFirestoreId != null) {
                Log.d("RecordRepository", "Firestore id is not null")
                db.collection("users")
                    .document(record.userId)
                    .collection("records")
                    .document(recFirestoreId)
                    .delete()
                    .await()

                Log.d("RecordRepository", "Record deleted from Firestore")
            }

            recordDao.deleteRecord(record)
            Log.d("RecordRepository", "Record deleted from Room")
        }
        catch (e: Exception){
            Log.e("RecordRepository", "could not delete record", e)
        }
    }

    suspend fun clearAllRecords() {
        recordDao.deleteAllRecords()
    }
}