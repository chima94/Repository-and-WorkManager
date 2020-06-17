package com.example.repository.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.repository.database.VideosDatabase
import com.example.repository.database.asDomainModel
import com.example.repository.domain.DevByteVideo

import com.example.repository.network.DevByteNetwork

import com.example.repository.network.asDatabaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository(private val videosDatabase: VideosDatabase) {

    val videos: LiveData<List<DevByteVideo>> = Transformations.map(videosDatabase.videoDao.getVideos()) {
        it.asDomainModel()
    }


    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = DevByteNetwork.devbytes.getPlaylist().await()
            videosDatabase.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }
}