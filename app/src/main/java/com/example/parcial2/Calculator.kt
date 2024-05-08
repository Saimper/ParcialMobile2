package com.example.parcial2

import java.lang.IllegalArgumentException

class Calculator (private val montoPrestamo: Double, private val tasaInteres: Double, private val periodoAmortizacion: Int) {

    @Throws(IllegalArgumentException::class)
    fun calcularResultado(): Double {



        if (montoPrestamo <= 0 || montoPrestamo > 1_000_000) {
            throw IllegalArgumentException("El monto del préstamo debe estar entre 0 y $1,000,000")
        }

        if (periodoAmortizacion <= 0 || periodoAmortizacion > 6) {
            throw IllegalArgumentException("El período de amortización debe estar entre 0 y 6 meses")
        }
        if (tasaInteres <= 0 || tasaInteres > 33.09) {
            throw IllegalArgumentException("La tasa de interés debe estar entre 0% y 33.09%")
        }

        val tasaInteresMensual = tasaInteres / 100 / 12
        val numeroPagos = periodoAmortizacion * 12
        return montoPrestamo * tasaInteresMensual * Math.pow(1 + tasaInteresMensual, numeroPagos.toDouble()) /
                (Math.pow(1 + tasaInteresMensual, numeroPagos.toDouble()) - 1)
    }



}