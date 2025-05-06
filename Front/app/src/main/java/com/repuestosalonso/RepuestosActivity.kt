package com.example.repuestosalonso.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repuestosalonso.network.RetrofitClient
import com.example.respuestosalonso.R
import kotlinx.coroutines.launch

class RepuestosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepuestosAdapter
    private var repuestosList = mutableListOf<Repuesto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repuestos)

        // Configurar la barra de acción
        supportActionBar?.title = "Catálogo de Repuestos"

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.repuestosRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar el adaptador
        adapter = RepuestosAdapter(repuestosList) { repuesto ->
            // Acción al hacer clic en un repuesto
            Toast.makeText(this, "Seleccionado: ${repuesto.nombre}", Toast.LENGTH_SHORT).show()
            // Aquí puedes navegar a una pantalla de detalles del repuesto
        }
        recyclerView.adapter = adapter

        // Cargar los repuestos
        loadRepuestos()
    }

    private fun loadRepuestos() {
        // Mostrar indicador de carga
        findViewById<View>(R.id.progressBar).visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Obtener el token guardado
                val sharedPref = getSharedPreferences("RepuestosApp", MODE_PRIVATE)
                val token = sharedPref.getString("token", "")

                // Llamar a la API
                val response = RetrofitClient.apiService.getRepuestos("Bearer $token")

                // Actualizar la UI
                if (response.isSuccessful && response.body() != null) {
                    repuestosList.clear()
                    repuestosList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()

                    // Mostrar mensaje si no hay repuestos
                    if (repuestosList.isEmpty()) {
                        findViewById<View>(R.id.emptyView).visibility = View.VISIBLE
                    } else {
                        findViewById<View>(R.id.emptyView).visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@RepuestosActivity,
                        "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RepuestosActivity,
                    "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                findViewById<View>(R.id.progressBar).visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_repuestos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                loadRepuestos()
                true
            }
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Limpiar datos de sesión
        val sharedPref = getSharedPreferences("RepuestosApp", MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("token")
            remove("userId")
            remove("username")
            apply()
        }

        // Volver a la pantalla de login
        finish()
    }
}

// Modelo de datos para un repuesto
data class Repuesto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagen: String?
)

// Adaptador para el RecyclerView
class RepuestosAdapter(
    private val repuestos: List<Repuesto>,
    private val onItemClick: (Repuesto) -> Unit
) : RecyclerView.Adapter<RepuestosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: android.widget.TextView = view.findViewById(R.id.nombreTextView)
        val descripcionTextView: android.widget.TextView = view.findViewById(R.id.descripcionTextView)
        val precioTextView: android.widget.TextView = view.findViewById(R.id.precioTextView)
        val stockTextView: android.widget.TextView = view.findViewById(R.id.stockTextView)
        val imagenImageView: android.widget.ImageView = view.findViewById(R.id.imagenImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repuesto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repuesto = repuestos[position]

        holder.nombreTextView.text = repuesto.nombre
        holder.descripcionTextView.text = repuesto.descripcion
        holder.precioTextView.text = "€${repuesto.precio}"
        holder.stockTextView.text = "Stock: ${repuesto.stock}"

        // Cargar imagen (puedes usar Glide o Picasso)
        // Ejemplo con Glide:
        // Glide.with(holder.itemView).load(repuesto.imagen).into(holder.imagenImageView)

        // Acción al hacer clic
        holder.itemView.setOnClickListener { onItemClick(repuesto) }
    }

    override fun getItemCount() = repuestos.size
}