package com.example.citas_medicas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citas_medicas.databinding.ItemCitaBinding
import com.example.citas_medicas.BD_Room.models.CitaMedica
import kotlin.Unit as Unit

class CitaAdapter(
    private val onCloseClick: (CitaMedica) -> Unit,
    private val onItemClick: (CitaMedica) -> Unit // Añadido el parámetro para manejar el click en la cita
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    private var citasList = listOf<CitaMedica>()

    // Crea el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitaViewHolder(binding)
    }

    // Asocia los datos con el ViewHolder
    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citasList[position]
        holder.bind(cita, onCloseClick, onItemClick) // Pasamos el click del item
    }

    // Devuelve el tamaño de la lista
    override fun getItemCount(): Int {
        return citasList.size
    }

    // Actualiza la lista de citas
    fun submitList(citas: List<CitaMedica>) {
        citasList = citas
        notifyDataSetChanged()
    }

    // ViewHolder que conecta la vista con los datos
    class CitaViewHolder(private val binding: ItemCitaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cita: CitaMedica, onCloseClick: (CitaMedica) -> Unit, onItemClick: (CitaMedica) -> Unit) {
            binding.tituloCita.text = cita.titulo
            binding.fechaCita.text = cita.fecha
            binding.horaCita.text = cita.hora

            binding.iconoEliminar.setOnClickListener { onCloseClick(cita) }

            binding.root.setOnClickListener { onItemClick(cita) }
        }
    }
}
