package com.gabriel.financas.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.gabriel.financas.R
import com.gabriel.financas.delegate.TransacaoDelegate
import com.gabriel.financas.delegate.TransacaoDelegateJava
import com.gabriel.financas.model.Tipo
import com.gabriel.financas.model.Transacao
import com.gabriel.financas.ui.ResumoView
import com.gabriel.financas.ui.adapter.ListaTransacoesAdapter
import com.gabriel.financas.ui.dialog.AdicionaTransacaoDialog
import com.gabriel.financas.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity()
{
    private val transacoes : MutableList<Transacao> = mutableListOf()
    private val viewDaActivity by lazy {
        window.decorView
    }
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()


    }

    private fun configuraFab()
    {
        lista_transacoes_adiciona_receita.setOnClickListener {
            dialogAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            dialogAdicao(Tipo.DESPESA)
        }
    }


    private fun dialogAdicao(tipo: Tipo)
    {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
            .configuraDialog(tipo, object: TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    adiciona(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }


    private fun adiciona(transacao: Transacao)
    {
        transacoes.add(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes()
    {

        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo()
    {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista()
    {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview)
        {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                dialogAlteracao(transacao, position)

            }
        }
    }

    private fun dialogAlteracao(
        transacao: Transacao,
        position: Int
    )
    {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
            .configuraDialog(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    alterar(transacao, position)
                }

            })
    }

    private fun alterar(transacao: Transacao, position: Int) {
        transacoes[position] = transacao
        atualizaTransacoes()
    }

}