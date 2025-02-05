package com.example.citas_medicas.fragments.funcionalidades_fragments

import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.citas_medicas.R

class ResolutionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resolution, container, false)
        val textView: TextView = view.findViewById(R.id.text_resolution)

        val displayMetrics = DisplayMetrics()

        val display = requireContext().getSystemService(DisplayManager::class.java)
            ?.getDisplay(Display.DEFAULT_DISPLAY)

        display?.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        textView.text = "Resolución: ${width}x${height} px"

        return view
    }
}