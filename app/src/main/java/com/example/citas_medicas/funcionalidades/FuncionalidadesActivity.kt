package com.example.citas_medicas.funcionalidades

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.citas_medicas.MainActivity
import com.example.citas_medicas.R
import com.example.citas_medicas.databinding.ActivityFuncionalidadesBinding
import com.example.citas_medicas.grabaciones.GrabacionesActivity
import com.example.citas_medicas.login_firebase.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class FuncionalidadesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFuncionalidadesBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFuncionalidadesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_funcionalidades) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupDrawer()

        if (savedInstanceState == null) {
            navController.navigate(R.id.fragment_resolution)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.funcionalidades)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupDrawer() {
        // Configurar la navegación en el Drawer
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_citas -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_grabaciones -> startActivity(Intent(this, GrabacionesActivity::class.java))
                R.id.nav_funcionalidades -> startActivity(Intent(this, FuncionalidadesActivity::class.java))
                R.id.nav_logout -> logout()
            }
            binding.funcionalidades.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun logout() {
        // Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut()

        // Mostrar un mensaje de Toast para informar al usuario
        Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()

        // Redirigir al usuario a la actividad de login
        val intent = Intent(this, LoginActivity::class.java) // Asegúrate de que LoginActivity sea el nombre de tu actividad de login
        startActivity(intent)

        // Finalizar la actividad actual
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.funcionalidades_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.resolutionListItem -> {
                navController.navigate(R.id.fragment_resolution)
                true
            }
            R.id.broadcastListItem -> {
                navController.navigate(R.id.fragment_broadcast_carga)
                true
            }
            R.id.lightSensorListItem -> {
                navController.navigate(R.id.fragment_light_sensor)
                true
            }
            R.id.locationListItem -> {
                navController.navigate(R.id.fragment_location)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}