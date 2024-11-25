package com.example.rpgcharactermanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//Tabela equipamentos
@Entity(tableName = "equipamentos")
data class Equipamento(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val personagemId: Int,
    val bonus: Int,
    val nome: String,
    val tipo: String,
    val descricao: String
)
