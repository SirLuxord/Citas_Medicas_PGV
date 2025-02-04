package com.example.citas_medicas.BD_Room

import com.example.citas_medicas.BD_Room.models.CitaMedica
import kotlinx.coroutines.flow.Flow

class ConsultasCitas (private val citaDao: CitaDao, private val emailUsuario: String) {
    val todasLasCitas: Flow<List<CitaMedica>> = citaDao.obtenerTodasLasCitas(emailUsuario)

    suspend fun insertar(cita: CitaMedica) {
        citaDao.insertarCita(cita)
    }

    suspend fun actualizar(cita: CitaMedica) {
        citaDao.actualizarCita(cita)
    }

    suspend fun eliminar(cita: CitaMedica) {
        citaDao.eliminarCita(cita)
    }
}