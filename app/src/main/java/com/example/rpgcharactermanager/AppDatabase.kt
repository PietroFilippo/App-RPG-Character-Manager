package com.example.rpgcharactermanager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rpgcharactermanager.data.dao.EquipamentoDao
import com.example.rpgcharactermanager.data.dao.HabilidadeDao
import com.example.rpgcharactermanager.data.dao.PersonagemDao
import com.example.rpgcharactermanager.data.entity.Equipamento
import com.example.rpgcharactermanager.data.entity.Habilidade
import com.example.rpgcharactermanager.data.entity.Personagem

// Declaração da classe de banco de dados usando Room
@Database(
    entities = [Personagem::class, Equipamento::class, Habilidade::class], // Entidades do banco
    version = 1 // Versão do banco de dados
)
abstract class AppDatabase : RoomDatabase() {

    // Define métodos abstratos para obter as DAOs
    abstract fun personagemDao(): PersonagemDao // DAO para acessar dados da tabela de Personagem
    abstract fun equipamentoDao(): EquipamentoDao // DAO para acessar dados da tabela de Equipamento
    abstract fun habilidadeDao(): HabilidadeDao // DAO para acessar dados da tabela de Habilidade
}