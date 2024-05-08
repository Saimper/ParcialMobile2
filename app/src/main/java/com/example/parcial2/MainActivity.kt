package com.example.parcial2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



       val  buttonCalcular = findViewById<Button>(R.id.button)

        buttonCalcular.setOnClickListener {

            calcularCuota()

        }




    }

    private fun calcularCuota() {
        val textPrestamo = findViewById<TextView>(R.id.textPrestamo)
        val textInteres = findViewById<TextView>(R.id.textInteres)
        val textAmortizacion = findViewById<TextView>(R.id.textAmortizacion)

        val montoPrestamo = textPrestamo.text.toString().toDoubleOrNull() ?: return
        val tasaInteres = textInteres.text.toString().toDoubleOrNull() ?: return
        val periodoAmortizacion = textAmortizacion.text.toString().toIntOrNull() ?: return

        try {
            val Calculator = Calculator(montoPrestamo, tasaInteres, periodoAmortizacion)
            val resultado = Calculator.calcularResultado()

            val formatoPesos = NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
            formatoPesos.minimumFractionDigits = if (resultado % 1 == 0.0) 0 else 2
            val resultadoFormateado = formatoPesos.format(resultado)

            findViewById<TextView>(R.id.textViewResultado).text = "Cuota mensual: $resultadoFormateado"
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }




    }