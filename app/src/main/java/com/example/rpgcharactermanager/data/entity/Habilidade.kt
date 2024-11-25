package com.example.rpgcharactermanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//Tabela habilidades
@Entity(tableName = "habilidades")
data class Habilidade(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val personagemId: Int,
    val levelRequirido: Int,
    val nome: String,
    val tipo: String,
    val descricao: String
)

