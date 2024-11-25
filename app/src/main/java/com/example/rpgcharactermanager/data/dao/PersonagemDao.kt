package com.example.rpgcharactermanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rpgcharactermanager.data.entity.Personagem
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonagemDao {

    // Lista todos os personagens ordenados por ID
    @Query("SELECT * FROM personagens ORDER BY id")
    fun listarPersonagens(): Flow<List<Personagem>>

    // Seleciona um personagem pelo ID
    @Query("SELECT * FROM personagens WHERE id = :id")
    fun selecionarPersonagemId(id: Int): Flow<Personagem>

    // Busca personagens pelo nome
    @Query("SELECT * FROM personagens WHERE nome LIKE :nome")
    fun buscarPorNome(nome: String): Flow<List<Personagem>>

    // Insere ou substitui um personagem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirPersonagem(personagem: Personagem)

    // Atualiza um personagem existente
    @Update
    suspend fun atualizarPersonagem(personagem: Personagem)

    // Remove um personagem
    @Delete
    suspend fun deletarPersonagem(personagem: Personagem)
}