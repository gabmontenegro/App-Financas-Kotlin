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
import java.util.Calendar

abstract class FormularioTransacaoDialog(
   private val context: Context,
   private val viewGroup: ViewGroup?
) {
    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    abstract protected val tituloBotaoPositivo : String

    fun configuraDialog(tipo: Tipo, delegate: (transacao: Transacao) -> Unit)
    {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit)
    {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo) { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()
                val valor = converteCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                 delegate(transacaoCriada)

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int

    private fun converteCampoValor(valorEmTexto: String) : BigDecimal
    {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException)
        {
            Toast.makeText(context, "Falha na conversão de valor!", Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo)
    {
        val categorias = categoriasPor(tipo)
        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )

        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipo: Tipo): Int
    {
        if (tipo == Tipo.RECEITA)
        {
            return  R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData()
    {
        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
                DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        campoData.setText(dataSelecionada.formataParaBrasileiro())
                    }, ano, mes, dia
                )
                    .show()
            }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.form_transacao,
                viewGroup,
                false
            )
    }
}