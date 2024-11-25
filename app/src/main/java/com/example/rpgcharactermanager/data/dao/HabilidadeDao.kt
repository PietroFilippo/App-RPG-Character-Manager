package com.example.rpgcharactermanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rpgcharactermanager.data.entity.Habilidade
import kotlinx.coroutines.flow.Flow

@Dao
interface HabilidadeDao {
    // Lista de habilidade de um personagem, ordenadas por ID
    @Query("SELECT * FROM habilidades WHERE personagemId = :personagemId ORDER BY id")
    fun listarHabilidades(personagemId: Int): Flow<List<Habilidade>>

    //Insere uma habilidade no banco de dados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirHabilidade(habilidade: Habilidade)

    //Atualiza os dados de uma habilidade
    @Update
    suspend fun atualizarHabilidade(habilidade: Habilidade)

    //Deleta uma habilidade do banco de dados
    @Delete
    suspend fun deletarHabilidade(habilidade: Habilidade)

    //Deleta todas as habilidades de um personagem
    @Query("DELETE FROM habilidades WHERE personagemId = :personagemId")
    suspend fun deletarHabilidadesPersonagem(personagemId: Int)
}