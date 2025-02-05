package com.example.citas_medicas.fragments.funcionalidades_fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.citas_medicas.R

class BroadcastCarga : Fragment(){
    private lateinit var receiver: BroadcastReceiver
    private var isReceiverRegistered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_broadcast_carga, container, false)

        val statusTextView: TextView = view.findViewById(R.id.text_status)
        val toggleButton: Button = view.findViewById(R.id.toggle_receiver)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_POWER_CONNECTED -> statusTextView.text = "Cargando..."
                    Intent.ACTION_POWER_DISCONNECTED -> statusTextView.text = "Desconectado de la corriente"
                }
            }
        }

        toggleButton.setOnClickListener {
            if (!isReceiverRegistered) {
                val intentFilter = IntentFilter().apply {
                    addAction(Intent.ACTION_POWER_CONNECTED)
                    addAction(Intent.ACTION_POWER_DISCONNECTED)
                }
                requireContext().registerReceiver(receiver, intentFilter)
                isReceiverRegistered = true
                toggleButton.text = "Desactivar monitorización"
            } else {
                requireContext().unregisterReceiver(receiver)
                isReceiverRegistered = false
                statusTextView.text = "Monitor Desactivado"
                toggleButton.text = "Activar Monitorrización"
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isReceiverRegistered) {
            requireContext().unregisterReceiver(receiver)
        }
    }
}
