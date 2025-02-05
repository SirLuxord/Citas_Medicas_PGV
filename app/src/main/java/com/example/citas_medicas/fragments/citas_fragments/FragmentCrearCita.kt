package com.example.citas_medicas.fragments.citas_fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citas_medicas.R
import com.example.citas_medicas.databinding.FragmentCrearCitaBinding
import com.example.citas_medicas.BD_Room.models.CitaMedica
import com.example.citas_medicas.view_models.CitaViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar


class FragmentCrearCita : Fragment(R.layout.fragment_crear_cita) {

    private var _binding: FragmentCrearCitaBinding? = null
    private val binding get() = _binding!! // Acceder de forma segura a binding
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.crearCitaButton.setOnClickListener { crearCita() }
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

    private fun crearCita() {
        val titulo = binding.citaEditText.text?.toString()?.trim() ?: ""
        val fecha = binding.dateEditText.text?.toString()?.trim() ?: ""
        val hora = binding.timeEditText.text?.toString()?.trim() ?: ""
        val email = obtenerEmailUsuario()

        if (titulo.isNotEmpty() && fecha.isNotEmpty() && hora.isNotEmpty() && email.isNotEmpty()) {
            val nuevaCita = CitaMedica(
                email = email,
                titulo = titulo,
                fecha = fecha,
                hora = hora
            )

            citaViewModel.insertarCita(nuevaCita)
            Toast.makeText(requireContext(), "Cita creada con éxito", Toast.LENGTH_SHORT).show()

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
