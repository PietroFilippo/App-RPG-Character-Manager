package com.example.rpgcharactermanager.data.repository

import com.example.rpgcharactermanager.data.dao.EquipamentoDao
import com.example.rpgcharactermanager.data.dao.HabilidadeDao
import com.example.rpgcharactermanager.data.dao.PersonagemDao
import com.example.rpgcharactermanager.data.entity.Equipamento
import com.example.rpgcharactermanager.data.entity.Habilidade
import com.example.rpgcharactermanager.data.entity.Personagem
import javax.inject.Inject

class PersonagemRepository @Inject constructor(
    private val personagemDao: PersonagemDao,
    private val equipamentoDao: EquipamentoDao,
    private val habilidadeDao: HabilidadeDao
) {
    // Métodos para listar, selecionar, inserir, buscar e atualizar personagens
    fun listarPersonagens() = personagemDao.listarPersonagens()
    fun selecionarPersonagemId(id: Int) = personagemDao.selecionarPersonagemId(id)
    suspend fun inserirPersonagem(personagem: Personagem) = personagemDao.inserirPersonagem(personagem)
    suspend fun atualizarPersonagem(personagem: Personagem) = personagemDao.atualizarPersonagem(personagem)

    // Método para deletar personagem e excluir seus equipamentos e habilidades relacionados
    suspend fun deletarPersonagem(personagem: Personagem){
        personagemDao.deletarPersonagem(personagem)
        equipamentoDao.deletarEquipamentosPersonagem(personagem.id)
        habilidadeDao.deletarHabilidadesPersonagem(personagem.id)
    }

    // Métodos para listar, inserir, atualizar e deletar equipamentos de um personagem
    fun listarEquipamentos(personagemId: Int) = equipamentoDao.listarEquipamentos(personagemId)
    suspend fun inserirEquipamento(equipamento: Equipamento) = equipamentoDao.inserirEquipamento(equipamento)
    suspend fun atualizarEquipamento(equipamento: Equipamento) = equipamentoDao.atualizarEquipamento(equipamento)
    suspend fun deletarEquipamento(equipamento: Equipamento) = equipamentoDao.deletarEquipamento(equipamento)

    // Métodos para listar, inserir, atualizar e deletar habilidades de um personagem
    fun listarHabilidades(personagemId: Int) = habilidadeDao.listarHabilidades(personagemId)
    suspend fun inserirHabilidade(habilidade: Habilidade) = habilidadeDao.inserirHabilidade(habilidade)
    suspend fun atualizarHabilidade(habilidade: Habilidade) = habilidadeDao.atualizarHabilidade(habilidade)
    suspend fun deletarHabilidade(habilidade: Habilidade) = habilidadeDao.deletarHabilidade(habilidade)
}