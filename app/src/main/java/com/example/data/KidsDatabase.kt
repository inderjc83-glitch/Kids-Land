package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.ActivityLogDao
import com.example.data.dao.ChildProfileDao
import com.example.data.entity.ActivityLogEntity
import com.example.data.entity.ChildProfileEntity

@Database(
    entities = [ChildProfileEntity::class, ActivityLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KidsDatabase : RoomDatabase() {
    abstract fun childProfileDao(): ChildProfileDao
    abstract fun activityLogDao(): ActivityLogDao

    companion object {
        @Volatile
        private var INSTANCE: KidsDatabase? = null

        fun getInstance(context: Context): KidsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KidsDatabase::class.java,
                    "kidsland_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
