package com.ameliok.myweatherapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ameliok.myweatherapp.data.WeatherAppDao


abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherAppDao()
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            synchronized(this ) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        DatabaseConstants.DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}