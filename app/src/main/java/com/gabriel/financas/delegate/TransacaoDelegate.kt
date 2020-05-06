package com.gabriel.financas.delegate

import com.gabriel.financas.model.Transacao

interface TransacaoDelegate
{
    fun delegate(transacao: Transacao)

}