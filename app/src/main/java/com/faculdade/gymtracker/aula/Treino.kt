package com.faculdade.gymtracker.aula

import java.io.Serializable

// Modelo de dados representando um treino (ou evento)
data class Treino(
    val id: Int = 0,
    val nome: String,
    val data: String,
    val local: String,
    val descricao: String,
    val favorito: Boolean = false
) : Serializable

// Mantem compatibilidade com o termo Evento da aula
typealias Evento = Treino
