package com.gabriel.financas.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String
{
    if(this.length > caracteres)
    {
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, caracteres)}..."
    }

    return this
}
fun String.converteParaCalendar() : Calendar
{
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida: Date = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida

    return data
}