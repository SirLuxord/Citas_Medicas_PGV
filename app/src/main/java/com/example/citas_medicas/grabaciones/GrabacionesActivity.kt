package com.example.citas_medicas.grabaciones

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.citas_medicas.R
import com.example.citas_medicas.databinding.ActivityGrabacionBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class GrabacionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGrabacionBinding
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var fileName: String? = null
    private var isRecording = false
    private var isPaused = false
    private val permissionsRequired = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrabacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Configurar el archivo donde se guardará la grabación
        fileName = "${externalCacheDir?.absolutePath}/grabacion.3gp"

        checkPermissions()

        binding.btnRecord.setOnClickListener { toggleRecording() }
        binding.btnPause.setOnClickListener { pauseRecording() }
        binding.fabPlay.setOnClickListener { playRecording() }

        binding.seekBarVolume.setOnSeekBarChangeListener(volumeChangeListener)
        binding.seekBarSpeed.setOnSeekBarChangeListener(speedChangeListener)
    }

    private fun toggleRecording() {
        if (isRecording) {
            stopRecording()
            binding.btnRecord.text = "Grabar"
            Snackbar.make(binding.root, "Grabación detenida", Snackbar.LENGTH_SHORT).show()
        } else {
            startRecording()
            binding.btnRecord.text = "Detener"
            Snackbar.make(binding.root, "Grabación iniciada", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(fileName)

            try {
                prepare()
                start()
                isRecording = true
            } catch (e: IOException) {
                Toast.makeText(this@GrabacionesActivity, "Error al grabar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pauseRecording() {
        if (isRecording) {
            if (isPaused) {
                mediaRecorder?.resume()
                binding.btnPause.text = "Pausar"
                Snackbar.make(binding.root, "Grabación reanudada", Snackbar.LENGTH_SHORT).show()
            } else {
                mediaRecorder?.pause()
                binding.btnPause.text = "Reanudar"
                Snackbar.make(binding.root, "Grabación pausada", Snackbar.LENGTH_SHORT).show()
            }
            isPaused = !isPaused
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
    }

    private fun playRecording() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            // Si está reproduciendo, detener y liberar recursos
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
            binding.fabPlay.setImageResource(android.R.drawable.ic_media_play) // Cambia el ícono a "play"
            Toast.makeText(this@GrabacionesActivity, "Reproducción detenida", Toast.LENGTH_SHORT).show()
        } else {
            // Si no está reproduciendo, iniciar la reproducción
            mediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    setVolume(binding.seekBarVolume.progress / 100f, binding.seekBarVolume.progress / 100f)
                    start()
                    binding.fabPlay.setImageResource(android.R.drawable.ic_media_pause) // Cambia el ícono a "pause"
                    Toast.makeText(this@GrabacionesActivity, "Reproduciendo...", Toast.LENGTH_SHORT).show()

                    // Cambia el ícono de nuevo a "play" cuando termine la reproducción
                    setOnCompletionListener {
                        binding.fabPlay.setImageResource(android.R.drawable.ic_media_play)
                        mediaPlayer?.release()
                        mediaPlayer = null
                    }
                } catch (e: IOException) {
                    Toast.makeText(this@GrabacionesActivity, "Error al reproducir", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val volumeChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            mediaPlayer?.setVolume(progress / 100f, progress / 100f)
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val speedChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) { // Verifica si está reproduciendo
                mediaPlayer!!.playbackParams = mediaPlayer!!.playbackParams.setSpeed(progress / 100f)
            }
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private fun checkPermissions() {
        if (permissionsRequired.any { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }) {
            ActivityCompat.requestPermissions(this, permissionsRequired, REQUEST_RECORD_AUDIO_PERMISSION)
        }
    }
}