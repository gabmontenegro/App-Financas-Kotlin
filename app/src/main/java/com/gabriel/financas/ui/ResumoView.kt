package com.gabriel.financas.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.gabriel.financas.R
import com.gabriel.financas.extension.formataParaBrasileiro
import com.gabriel.financas.model.Resumo
import com.gabriel.financas.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(context: Context,
                 private val view: View,
                 transacoes: List<Transacao>)
{
    private val resumo : Resumo = Resumo(transacoes);
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza()
    {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }
    private fun adicionaReceita()
    {
        val totalReceita = resumo.receita

        with(view.resumo_card_receita)
        {
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()
        }

    }

    private fun adicionaDespesa()
    {
        val totalDespesa = resumo.despesa

        with(view.resumo_card_despesa)
        {
            setTextColor(corDespesa)
            text = totalDespesa.formataParaBrasileiro()
        }

    }

    private fun adicionaTotal()
    {
        val valorTotal = resumo.calculaTotal
        val cor = corPor(valorTotal)

        with(view.resumo_card_total)
        {
            setTextColor(cor)
            text = valorTotal.formataParaBrasileiro()
        }

    }

    private fun corPor(valor: BigDecimal) : Int
    {
        if (valor >= BigDecimal.ZERO)
        {
           return corReceita
        }

        return corDespesa
    }
}