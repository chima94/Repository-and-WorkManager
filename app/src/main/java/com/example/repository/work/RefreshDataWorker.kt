package com.example.repository.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.repository.database.getDatabase
import com.example.repository.repository.VideoRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext : Context, params : WorkerParameters): CoroutineWorker(appContext,params) {

    companion object {
        const val WORK_NAME = "com.example.repository.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database  = getDatabase(applicationContext)
        val repository = VideoRepository(database)

        try{
            repository.refreshVideos()
            Log.i("RefreshDataworker", "work request for syn is running")
        }catch (e : HttpException){
            return Result.retry()
        }
        return Result.success()
    }
}