package com.example.rpgcharactermanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rpgcharactermanager.data.entity.Equipamento

import kotlinx.coroutines.flow.Flow

@Dao
interface EquipamentoDao {
    //Lista os equipamentos de um personagem, ordenados por ID
    @Query("SELECT * FROM equipamentos WHERE personagemId = :personagemId ORDER BY id")
    fun listarEquipamentos(personagemId: Int): Flow<List<Equipamento>>

    //Insere um equipamento no banco de dados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirEquipamento(equipamento: Equipamento)

    //Atualiza os dados de um equipamento
    @Update
    suspend fun atualizarEquipamento(equipamento: Equipamento)

    //Deleta um equipamento do banco de dados
    @Delete
    suspend fun deletarEquipamento(equipamento: Equipamento)

    //Deleta todos os equipamentos de um personagem
    @Query("DELETE FROM equipamentos WHERE personagemId = :personagemId")
    suspend fun deletarEquipamentosPersonagem(personagemId: Int)
}