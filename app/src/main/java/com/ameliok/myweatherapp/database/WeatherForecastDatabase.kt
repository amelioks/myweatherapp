package com.ameliok.myweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ameliok.myweatherapp.data.WeatherForecastDao

@Database(entities = [WeatherForecastEntity::class], version = 1)
abstract class WeatherForecastDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherForecastDao
    companion object {
        @Volatile
        private var INSTANCE: WeatherForecastDatabase? = null

        fun getInstance(context: Context): WeatherForecastDatabase {
            synchronized(this ) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherForecastDatabase::class.java,
                        DatabaseConstants.DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}