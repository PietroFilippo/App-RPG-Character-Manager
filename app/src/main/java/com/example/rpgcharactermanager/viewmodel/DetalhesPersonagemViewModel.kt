package com.example.rpgcharactermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// Gerencia os detalhes de um personagem específico
@HiltViewModel
class DetalhesPersonagemViewModel @Inject constructor(
    private val repository: PersonagemRepository // Repositório para acesso aos dados do personagem
) : ViewModel() {

    // Flow que armazena o ID do personagem atual carregado
    private val _personagemId = MutableStateFlow<Int?>(null)

    // Flow que expõe os detalhes do personagem, baseado no ID fornecido
    @OptIn(ExperimentalCoroutinesApi::class) // Habilita APIs experimentais (flatMapLatest neste caso)
    val personagem = _personagemId
        .filterNotNull() // Ignora valores nulos no fluxo de IDs
        .flatMapLatest { id ->
            repository.selecionarPersonagemId(id) // Consulta o personagem no repositório
        }
        .stateIn(
            viewModelScope, // Escopo do ViewModel, gerencia o ciclo de vida do fluxo
            SharingStarted.Lazily, // Inicia o fluxo apenas quando necessário
            null // Valor inicial emitido pelo fluxo
        )

    // Define o ID do personagem a ser carregado
    fun carregarPersonagem(id: Int) {
        _personagemId.value = id
    }
}