package com.example.rek.minimalistfakeweatherapp.db

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.os.AsyncTask
import android.util.Log
import com.example.rek.minimalistfakeweatherapp.utils.Utils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

@Database(entities = [City::class], version = 1)
abstract class CitiesDb : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        private const val DB_NAME = "cities_db"
        private const val CSV_NAME = "cities.csv"
        private var INSTANCE: CitiesDb? = null
        private lateinit var app: Application

//        fun getInstance(application: Application) =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: returnDatabase(application).also { INSTANCE = it }
//            }

        fun getInstance(application: Application): CitiesDb {
            app = application
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = returnDatabase(application)
                    }
                }
            }
            return INSTANCE!!
        }

        private fun returnDatabase(application: Application): CitiesDb {

//            copyDatabaseFile(application, DB_NAME)
            return Room.databaseBuilder(
                application.applicationContext,
                CitiesDb::class.java,
                "amazing_cities"
            )
                .fallbackToDestructiveMigration()
                .addCallback(sCitiesCallback)
                .build()
        }

        private val sCitiesCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                PopulateDbAsyncTask(app, INSTANCE!!).execute()
            }
        }


        private class PopulateDbAsyncTask(private val application: Application, db: CitiesDb):
            AsyncTask<Void, Void, Void>() {

            private val dao = db.cityDao()

            override fun doInBackground(vararg params: Void?): Void? {

                try {
                    // Read the data
                    val inputStream = application.assets.open("databases/$CSV_NAME")
                    val streamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
                    val reader = BufferedReader(streamReader)

                    // Step over header
                    reader.readLine()

                    var line = reader.readLine()
                    while (line != null) {
                        Log.d(Utils.TAG, "line: $line")
                        // Insert line into db
                        val tokens = line.split(",")
                        dao.insert(City(tokens[0], tokens[1]))
                        line = reader.readLine()
                    }

                } catch (e: IOException) {
                    Log.d(Utils.TAG, "Failed to open file", e)
                    e.printStackTrace()
                }
                return null
            }
        }


//        private fun copyDatabaseFile(application: Application, dbName: String) {
//            val file = application.getDatabasePath(dbName)
//            Log.d(Utils.TAG, "file: $file")
//
//            // If the database already exists, return
//            if (file.exists()) {
//                Log.d(Utils.TAG, "file already exists")
//                return
//            }
//
//            // Make sure we have a path to the file
//            file.parentFile.mkdirs()
//
//            // Try to copy database file
//            try {
//                val inputStream = application.assets.open("databases/$dbName")
//                val output = FileOutputStream(file)
//                Log.d(Utils.TAG, "output: $output")
//
//                val buffer = ByteArray(8192)
//                var length = inputStream.read(buffer, 0, 8192)
//
//                while ( length > 0 ) {
//                    output.write(buffer, 0, length)
//                    length = inputStream.read(buffer, 0, 8192)
//                    Log.d(Utils.TAG, "writing>>")
//                }
//
//                output.flush()
//                output.close()
//                inputStream.close()
//            } catch (e: IOException) {
//                Log.d(Utils.TAG, "Failed to open file", e)
//                e.printStackTrace()
//            }
//        }
    }

}