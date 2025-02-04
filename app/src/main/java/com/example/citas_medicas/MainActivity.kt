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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.citas_medicas.BD_Room.CitasBD
import com.example.citas_medicas.adapter.CitaAdapter
import com.example.citas_medicas.databinding.ActivityMainBinding
import com.example.citas_medicas.fragments.citas_fragments.FragmentCrearCita
import com.example.citas_medicas.fragments.citas_fragments.FragmentListaCitas
import com.example.citas_medicas.grabaciones.GrabacionesActivity
import com.example.citas_medicas.view_models.CitaViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val citaViewModel: CitaViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CitaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupDrawer()

        if (savedInstanceState == null) {
            loadFragment(FragmentListaCitas())
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
                loadFragment(FragmentListaCitas())
                true
            }
            R.id.crearCitaItem -> {
                loadFragment(FragmentCrearCita())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // Cargar el fragmento dentro del FragmentContainerView
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

