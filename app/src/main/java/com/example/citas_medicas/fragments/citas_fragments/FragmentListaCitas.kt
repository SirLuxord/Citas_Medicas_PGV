package com.example.citas_medicas.fragments.citas_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citas_medicas.R
import com.example.citas_medicas.adapter.CitaAdapter
import com.example.citas_medicas.view_models.CitaViewModel


class FragmentListaCitas : Fragment(R.layout.fragment_lista_citas) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var citaAdapter: CitaAdapter
    private val citaViewModel: CitaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.listaReciclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        citaAdapter = CitaAdapter { cita ->
            citaViewModel.eliminarCita(cita)
        }
        recyclerView.adapter = citaAdapter

        // Observar los cambios en las citas y actualizar la lista
        citaViewModel.obtenerTodasLasCitas().observe(viewLifecycleOwner) { citas ->
            citaAdapter.submitList(citas)
        }

    }
}

