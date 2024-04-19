package com.example.parcial2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.intl.Locale
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


       val  textPrestamo = findViewById<TextView>(R.id.textPrestamo)
       val textInteres = findViewById<TextView>(R.id.textInteres)
       val  textAmortizacion = findViewById<TextView>(R.id.textAmortizacion)
       val  buttonCalcular = findViewById<Button>(R.id.button)

        buttonCalcular.setOnClickListener {

            val montoPrestamo = textPrestamo.text.toString().toDoubleOrNull()
            val tasaInteres = textInteres.text.toString().toDoubleOrNull()
            val periodoAmortizacion = textAmortizacion.text.toString().toIntOrNull()
            val textViewResultado = findViewById<TextView>(R.id.textViewResultado)

            if (montoPrestamo != null) {
                // Verificar si el monto del préstamo está dentro del límite
                if (montoPrestamo <= 1000000) {



                    // Verificar si se ingresó un valor válido para el período de amortización
                    if (periodoAmortizacion != null) {
                        // Verificar si el período de amortización está dentro del límite
                        if (periodoAmortizacion <= 6) {
                            // Obtener el texto ingresado por el usuario desde el EditText de interés


                            // Verificar si se ingresó un valor válido para la tasa de interés
                            if (tasaInteres != null) {
                                // Verificar si la tasa de interés no supera el límite
                                if (tasaInteres <= 33.09) {

                                    val resultado = calcularResultado(montoPrestamo, tasaInteres, periodoAmortizacion)

                                    val formatoPesos = NumberFormat.getCurrencyInstance(
                                        java.util.Locale(
                                            "es",
                                            "CO"
                                        )
                                    )

// Configurar el formato para que use 0 decimales si la cuota es un número entero
                                    formatoPesos.minimumFractionDigits = if (resultado % 1 == 0.0) 0 else 2

                                    val resultadoFormateado = formatoPesos.format(resultado)

// Muestra el resultado formateado en el TextView
                                    textViewResultado.text = "Cuota mensual: $resultadoFormateado"
                                } else {
                                    // Mostrar un mensaje de error si la tasa de interés supera el límite
                                    Toast.makeText(this, "La tasa de interés no puede superar el límite de 33.09%", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // Mostrar un mensaje de error si se ingresó un valor no válido para la tasa de interés
                                Toast.makeText(this, "Por favor ingrese un valor válido para la tasa de interés", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Mostrar un mensaje de error si el período de amortización excede el límite
                            Toast.makeText(this, "El período de amortización no puede ser superior a 6 meses", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Mostrar un mensaje de error si se ingresó un valor no válido para el período de amortización
                        Toast.makeText(this, "Por favor ingrese un valor válido para el período de amortización", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Mostrar un mensaje de error si el monto del préstamo excede el límite
                    Toast.makeText(this, "El monto del préstamo no puede ser mayor a $1,000,000", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un mensaje de error si se ingresó un valor no válido para el monto del préstamo
                Toast.makeText(this, "Por favor ingrese un valor válido para el monto del préstamo", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun calcularResultado(montoPrestamo: Double, tasaInteres: Double, periodoAmortizacion: Int): Double {

        val tasaInteresMensual = tasaInteres / 100 / 12
        val numeroPagos = periodoAmortizacion * 12
        return montoPrestamo * tasaInteresMensual * Math.pow(1 + tasaInteresMensual, numeroPagos.toDouble()) /
                (Math.pow(1 + tasaInteresMensual, numeroPagos.toDouble()) - 1)


    }

    }