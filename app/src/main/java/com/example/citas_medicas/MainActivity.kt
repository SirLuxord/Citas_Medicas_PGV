package com.example.citas_medicas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.citas_medicas.adapter.CitaAdapter
import com.example.citas_medicas.databinding.ActivityMainBinding
import com.example.citas_medicas.funcionalidades.FuncionalidadesActivity
import com.example.citas_medicas.grabaciones.GrabacionesActivity
import com.example.citas_medicas.login_firebase.LoginActivity
import com.example.citas_medicas.view_models.CitaViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setupDrawer()

        if (savedInstanceState == null) {
            navController.navigate(R.id.fragment_lista_citas)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
                R.id.nav_aboutUs -> {
                    // Mostrar información sobre CineApp
                    mostrarAcercaDe()
                }
                R.id.nav_exit -> {
                    // Cerrar aplicación
                    salirAplicacion()
                }
            }
            binding.main.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun mostrarAcercaDe() {
        val message = """
        Versión 1.0
        Creador: Haendel
        Descripción: La aplicación VCA es una herramienta multifuncional que ofrece tres actividades principales:
        - Concertar citas médicas.
        - Realizar grabaciones de audio.
        - Consultar y administrar funcionalidades del dispositivo.

        Gracias por usar nuestra aplicación.
    """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Acerca de CitasMédicasApp")
            .setMessage(message)
            .setPositiveButton("Cerrar") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun salirAplicacion() {
        finishAffinity()
        System.exit(0)
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
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.citasListItem -> {
                navController.navigate(R.id.fragment_lista_citas)  // Usar el navController ya inicializado
                true
            }
            R.id.crearCitaItem -> {
                navController.navigate(R.id.fragment_crear_cita)  // Usar el navController ya inicializado
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

