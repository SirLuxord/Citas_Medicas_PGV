package com.example.citas_medicas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.citas_medicas.adapter.CitaAdapter
import com.example.citas_medicas.databinding.ActivityMainBinding
import com.example.citas_medicas.grabaciones.GrabacionesActivity
import com.example.citas_medicas.view_models.CitaViewModel

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
                R.id.nav_citas -> navController.navigate(R.id.fragment_lista_citas)
                R.id.nav_grabaciones -> startActivity(Intent(this, GrabacionesActivity::class.java))
                //R.id.nav_funcionalidades -> startActivity(Intent(this, FuncionalidadesActivity::class.java))
                R.id.nav_logout -> logout()
            }
            binding.main.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun logout() {
        // Implementar lógica de cierre de sesión
        Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
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

