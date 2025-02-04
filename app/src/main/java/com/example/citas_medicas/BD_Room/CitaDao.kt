package com.example.citas_medicas.BD_Room

import androidx.room.*
import com.example.citas_medicas.BD_Room.models.CitaMedica

@Dao
interface CitaDao {
    @Insert
    suspend fun insertarCita(cita: CitaMedica)

    @Update
    suspend fun actualizarCita(cita: CitaMedica)

    @Delete
    suspend fun eliminarCita(cita: CitaMedica)

    @Query("SELECT * FROM citas WHERE email = :emailusuario ORDER BY fecha ASC")
    fun obtenerTodasLasCitas(emailusuario: String): kotlinx.coroutines.flow.Flow<List<CitaMedica>>
}