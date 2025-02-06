package com.example.citas_medicas.fragments.citas_fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.citas_medicas.BD_Room.models.CitaMedica
import com.example.citas_medicas.R
import com.example.citas_medicas.databinding.FragmentCrearCitaBinding
import com.example.citas_medicas.view_models.CitaViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class FramentoModificarCita : Fragment(R.layout.fragment_crear_cita) {

    private var _binding: FragmentCrearCitaBinding? = null
    private val binding get() = _binding!! // Acceder de forma segura a binding
    private val citaViewModel: CitaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(citaViewModel.citaSeleccionada.value?.titulo + "Este es el titulo del fragmento")
        binding.crearCitaButton.setText("Modificar cita")
        binding.citaEditText.setText(citaViewModel.citaSeleccionada.value?.titulo)
        binding.dateEditText.setText(citaViewModel.citaSeleccionada.value?.fecha)
        binding.timeEditText.setText(citaViewModel.citaSeleccionada.value?.hora)

        // Observar la cita seleccionada
        citaViewModel.citaSeleccionada.observe(viewLifecycleOwner, Observer { cita ->
            if (cita != null) {
                // Llenamos los campos con la cita seleccionada
                binding.citaEditText.setText(cita.titulo)
                binding.dateEditText.setText(cita.fecha)
                binding.timeEditText.setText(cita.hora)
            } else {
                Log.d("Cita", "No hay cita seleccionada")
            }
        })

        binding.crearCitaButton.setOnClickListener { modificarCita() }
        binding.dateEditText.setOnClickListener { mostrarDatePicker() }
        binding.timeEditText.setOnClickListener { mostrarTimePicker() }
    }

    private fun mostrarDatePicker() {
        val calendario = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                binding.dateEditText.setText("$dayOfMonth/${month + 1}/$year")
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun mostrarTimePicker() {
        val calendario = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                binding.timeEditText.setText(String.format("%02d:%02d", hourOfDay, minute))
            },
            calendario.get(Calendar.HOUR_OF_DAY),
            calendario.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    private fun modificarCita() {
        val titulo = binding.citaEditText.text?.toString()?.trim() ?: ""
        val fecha = binding.dateEditText.text?.toString()?.trim() ?: ""
        val hora = binding.timeEditText.text?.toString()?.trim() ?: ""
        val email = obtenerEmailUsuario()

        if (titulo.isNotEmpty() && fecha.isNotEmpty() && hora.isNotEmpty() && email.isNotEmpty()) {
            val citaModificada = CitaMedica(
                id = citaViewModel.citaSeleccionada.value?.id ?: 0,
                titulo = titulo,
                fecha = fecha,
                hora = hora,
                email = email
            )
            citaViewModel.modificarCita(citaModificada)

            Toast.makeText(requireContext(), "Se ha modificado la cita con éxito", Toast.LENGTH_SHORT).show()

            val navController = findNavController()
            navController.navigate(R.id.fragment_lista_citas)
        } else {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerEmailUsuario(): String {
        return FirebaseAuth.getInstance().currentUser?.email ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Liberar binding para evitar memory leaks
    }
}