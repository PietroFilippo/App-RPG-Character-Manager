package com.example.rpgcharactermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpgcharactermanager.data.entity.Habilidade
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Gerencia as habilidades de um personagem
@HiltViewModel
class HabilidadeViewModel @Inject constructor(
    private val repository: PersonagemRepository // Repositório para acesso aos dados do banco
) : ViewModel() {

    // Estado interno que armazena o ID do personagem cujas habilidades estão sendo gerenciadas
    private val _personagemId = MutableStateFlow<Int?>(null)

    // Estado interno que armazena a lista de habilidades do personagem
    private val _habilidade = MutableStateFlow<List<Habilidade>>(emptyList())

    // Exposição da lista de habilidades como um fluxo somente leitura para a interface
    val habilidade = _habilidade.asStateFlow()

    // Carrega as habilidades de um personagem específico pelo ID
    fun carregarHabilidade(personagemId: Int) {
        _personagemId.value = personagemId // Atualiza o ID do personagem
        viewModelScope.launch { // Executa operações assíncronas no escopo do ViewModel
            repository.listarHabilidades(personagemId) // Consulta as habilidades no repositório
                .collect {
                    _habilidade.value = it // Atualiza o estado interno com a lista de habilidades
                }
        }
    }

    // Adiciona uma nova habilidade ao banco de dados
    fun adicionarHabilidade(habilidade: Habilidade) = viewModelScope.launch {
        repository.inserirHabilidade(habilidade) // Insere a habilidade no repositório
    }

    // Atualiza uma habilidade existente no banco de dados
    fun atualizarHabilidade(habilidade: Habilidade) = viewModelScope.launch {
        repository.atualizarHabilidade(habilidade) // Atualiza a habilidade no repositório
    }

    // Remove uma habilidade do banco de dados
    fun deletarHabilidade(habilidade: Habilidade) = viewModelScope.launch {
        repository.deletarHabilidade(habilidade) // Deleta a habilidade no repositório
    }
}