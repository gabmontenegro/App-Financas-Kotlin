package com.gabriel.financas.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>)
{
    val receita get() = somaPor(Tipo.RECEITA)

    val despesa get() = somaPor(Tipo.DESPESA)

    val calculaTotal get() = receita.subtract(despesa)

    fun somaPor(tipo: Tipo) : BigDecimal
    {
        val somaDeTransacoesPorTipo = transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() }

        return BigDecimal(somaDeTransacoesPorTipo)
    }
}