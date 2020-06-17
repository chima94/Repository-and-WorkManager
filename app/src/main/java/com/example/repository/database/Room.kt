package com.example.repository.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class DatabaseVideo constructor(
        @PrimaryKey
        val url : String,
        val updated : String,
        val title : String,
        val description : String,
        val thumbnail : String
)

@Dao
interface VideoDao{
        @Query("select * from databasevideo")
        fun getVideos(): LiveData<List<DatabaseVideo>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll( videos: List<DatabaseVideo>)
}


@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
        abstract val videoDao: VideoDao
}


private lateinit var INSTANCE: VideosDatabase

fun getDatabase(context: Context): VideosDatabase {
        synchronized(VideosDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                VideosDatabase::class.java,
                                "videos").build()
                }
        }
        return INSTANCE
}