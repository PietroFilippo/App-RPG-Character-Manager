package com.example.rpgcharactermanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//Tabela personagem
@Entity(tableName = "personagens")
data class Personagem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val raca: String,
    val classe: String,
    val level: Int,
    val forca: Int,
    val destreza: Int,
    val vigor: Int,
    val inteligencia: Int,
    val sabedoria: Int,
    val carisma: Int,
    val background: String
)
