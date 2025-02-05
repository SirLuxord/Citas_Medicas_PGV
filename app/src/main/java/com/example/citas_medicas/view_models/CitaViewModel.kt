package com.example.citas_medicas.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.citas_medicas.BD_Room.CitasBD
import com.example.citas_medicas.BD_Room.ConsultasCitas
import com.example.citas_medicas.BD_Room.models.CitaMedica
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CitaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = CitasBD.getDatabase(application)  // Acceder a la base de datos
    private val emailUsuario: String = obtenerEmailUsuario()  // Obtener el email del usuario autenticado
    private val consultasCitas: ConsultasCitas = ConsultasCitas(db.citaDao(), emailUsuario)  // Usar ConsultasCitas para acceder a los datos

    // Esta propiedad ahora expone el Flow de todas las citas
    val todasLasCitas: Flow<List<CitaMedica>> = consultasCitas.todasLasCitas

    // Insertar una cita
    fun insertarCita(cita: CitaMedica) {
        viewModelScope.launch {
            consultasCitas.insertar(cita)
        }
    }

    // Actualizar una cita
    fun actualizarCita(cita: CitaMedica) {
        viewModelScope.launch {
            consultasCitas.actualizar(cita)
        }
    }

    // Eliminar una cita
    fun eliminarCita(cita: CitaMedica) {
        viewModelScope.launch {
            consultasCitas.eliminar(cita)
        }
    }

    // Obtener el email del usuario autenticado
    private fun obtenerEmailUsuario(): String {
        val usuario = FirebaseAuth.getInstance().currentUser
        return usuario?.email ?: "desconocido@example.com"  // Valor por defecto si no hay usuario autenticado
    }

    // Convertir el Flow de citas en LiveData para que pueda ser observado en el Fragment
    fun obtenerTodasLasCitas(): LiveData<List<CitaMedica>> {
        return todasLasCitas.asLiveData()  // Convertir Flow a LiveData
    }
}
