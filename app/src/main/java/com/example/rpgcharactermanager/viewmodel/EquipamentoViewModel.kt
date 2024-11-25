package com.example.rpgcharactermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpgcharactermanager.data.entity.Equipamento
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Gerencia os equipamentos de um personagem
@HiltViewModel
class EquipamentoViewModel @Inject constructor(
    private val repository: PersonagemRepository // Repositório para acesso aos dados do banco
) : ViewModel() {

    // Estado interno que armazena o ID do personagem atual
    private val _personagemId = MutableStateFlow<Int?>(null)

    // Estado interno que armazena a lista de equipamentos do personagem
    private val _equipamento = MutableStateFlow<List<Equipamento>>(emptyList())

    // Exposição da lista de equipamentos como um fluxo somente leitura para a interface
    val equipamento = _equipamento.asStateFlow()

    // Carrega os equipamentos de um personagem específico pelo ID
    fun carregarEquipamento(personagemId: Int) {
        _personagemId.value = personagemId // Atualiza o ID do personagem
        viewModelScope.launch { // Lida com operações assíncronas no escopo da ViewModel
            repository.listarEquipamentos(personagemId) // Consulta os equipamentos no repositório
                .collect {
                    _equipamento.value = it // Atualiza o estado interno com a lista de equipamentos
                }
        }
    }

    // Adiciona um novo equipamento ao banco de dados
    fun adicionarEquipamento(equipamento: Equipamento) = viewModelScope.launch {
        repository.inserirEquipamento(equipamento) // Insere o equipamento no repositório
    }

    // Atualiza um equipamento existente no banco de dados
    fun atualizarEquipamento(equipamento: Equipamento) = viewModelScope.launch {
        repository.atualizarEquipamento(equipamento) // Atualiza o equipamento no repositório
    }

    // Remove um equipamento do banco de dados
    fun deletarEquipamento(equipamento: Equipamento) = viewModelScope.launch {
        repository.deletarEquipamento(equipamento) // Deleta o equipamento no repositório
    }
}