package com.example.citas_medicas.fragments.funcionalidades_fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.citas_medicas.R

class LocationFragment : Fragment() {

    private lateinit var locationManager: LocationManager
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        textView = view.findViewById(R.id.text_location)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        getLastKnownLocation()

        return view
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let {
            textView.text = "Latitud: ${it.latitude}\nLongitud: ${it.longitude}\nAltitud: ${it.altitude}"
        }
    }
}