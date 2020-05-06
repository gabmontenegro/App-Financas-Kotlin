package com.gabriel.financas.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.gabriel.financas.R
import com.gabriel.financas.R.drawable.icone_transacao_item_despesa
import com.gabriel.financas.extension.formataParaBrasileiro
import com.gabriel.financas.extension.limitaEmAte
import com.gabriel.financas.model.Tipo
import com.gabriel.financas.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context) : BaseAdapter()

{
    private val limiteDaCategoria = 14

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View
    {
        val viewCriada: View = LayoutInflater.from(context).inflate(
            R.layout.transacao_item,
            parent,
            false
        )

        val transacao = transacoes[position]

        adicionaValor(viewCriada, transacao)
        adicionaIcone(viewCriada,transacao)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaValor(viewCriada: View, transacao: Transacao)
    {
        val cor : Int = corPor(transacao.tipo)

        viewCriada.transacao_valor.setTextColor(cor)
        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro();
    }

    private fun corPor(tipo: Tipo) : Int
    {
        if(tipo == Tipo.RECEITA)
        {
            return ContextCompat.getColor(context, R.color.receita)
        }

        return ContextCompat.getColor(context, R.color.despesa)
    }

    private fun adicionaData(viewCriada: View, transacao: Transacao)
    {
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(viewCriada: View, transacao: Transacao)
    {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(viewCriada: View, transacao: Transacao)
    {
        var icone = iconePor(transacao.tipo)
        viewCriada.transacao_icone.setBackgroundResource(icone)

    }

    private fun iconePor(tipo: Tipo) : Int
    {
        if(tipo == Tipo.RECEITA)
        {
            return R.drawable.icone_transacao_item_receita
        }
        return R.drawable.icone_transacao_item_despesa
    }


    override fun getItem(position: Int): Transacao
    {
        return transacoes[position]

    }

    override fun getItemId(position: Int): Long
    {
        return 0
    }

    override fun getCount(): Int
    {
        //chamada de property do kotlin collections, diferente do size do Java.String.Utils
        return transacoes.size
    }

}