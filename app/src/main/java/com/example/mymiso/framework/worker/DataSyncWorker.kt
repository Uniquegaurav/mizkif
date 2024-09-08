package com.example.mymiso.framework.worker
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mymiso.domain.use_cases.SyncRestaurantsUseCase

class DataSyncWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val syncRestaurantsUseCase: SyncRestaurantsUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            // handle the data
            // save to database
            syncRestaurantsUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}