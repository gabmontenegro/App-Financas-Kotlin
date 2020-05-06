package com.gabriel.financas.model

import java.math.BigDecimal
import java.util.Calendar

//no construtor ao colocar o modificador val, "transforma-se" os atributos em properties.
// entao pode-se acessar os valores como se fosse utilizar modificadores getter and setters
class Transacao(val valor: BigDecimal,
                val categoria: String = "Indefinida",
                val tipo: Tipo,
                val data: Calendar = Calendar.getInstance())


