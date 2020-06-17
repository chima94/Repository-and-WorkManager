package com.example.repository.database

import com.example.repository.domain.DevByteVideo

fun List<DatabaseVideo>.asDomainModel(): List<DevByteVideo>{
    return map{
        DevByteVideo(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }
}