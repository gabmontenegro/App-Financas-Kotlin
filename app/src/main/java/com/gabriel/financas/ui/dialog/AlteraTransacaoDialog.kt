package com.gabriel.financas.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.gabriel.financas.R
import com.gabriel.financas.delegate.TransacaoDelegate
import com.gabriel.financas.extension.converteParaCalendar
import com.gabriel.financas.extension.formataParaBrasileiro
import com.gabriel.financas.model.Tipo
import com.gabriel.financas.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int
    {
        if (tipo == Tipo.RECEITA)
        {
            return R.string.altera_receita
        }
        return   R.string.altera_despesa
    }

    fun configuraDialog(transacao: Transacao, delegate:(transacao : Transacao) -> Unit)
    {
        val tipo = transacao.tipo

        super.configuraDialog(tipo, transacaoDelegate)

        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao)
    {
        val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(tipo, transacao)
    }

    private fun inicializaCampoCategoria(
        tipo: Tipo,
        transacao: Transacao
    ) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

}