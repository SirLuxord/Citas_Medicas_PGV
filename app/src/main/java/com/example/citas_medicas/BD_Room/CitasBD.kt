package com.example.citas_medicas.BD_Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.citas_medicas.BD_Room.models.CitaMedica

@Database(entities = [CitaMedica::class], version = 1, exportSchema = false)
abstract class CitasBD : RoomDatabase() {

    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile
        private var INSTANCE: CitasBD? = null

        fun getDatabase(context: Context): CitasBD {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitasBD::class.java,
                    "citas_db"
                ).fallbackToDestructiveMigration() // 👈 IMPORTANTE para evitar errores de migración
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}