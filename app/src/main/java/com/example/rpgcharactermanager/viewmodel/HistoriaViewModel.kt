package com.example.rpgcharactermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpgcharactermanager.data.entity.Personagem
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Gerencia o background de um personagem
@HiltViewModel
class HistoriaViewModel @Inject constructor(
    private val repository: PersonagemRepository // Repositório para interagir com o banco de dados
) : ViewModel() {

    // Estado interno que armazena o personagem carregado
    private val _personagem = MutableStateFlow<Personagem?>(null)

    // Estado exposto como fluxo somente leitura para a interface
    val personagem = _personagem.asStateFlow()

    // Carrega os dados de um personagem específico pelo ID
    fun carregarPersonagem(personagemId: Int) {
        viewModelScope.launch {
            repository.selecionarPersonagemId(personagemId) // Consulta o personagem no repositório
                .collect { _personagem.value = it } // Atualiza o estado interno com o personagem carregado
        }
    }

    // Atualiza o background de um personagem no banco de dados
    fun atualizarBackground(personagem: Personagem) {
        viewModelScope.launch {
            repository.atualizarPersonagem(personagem) // Envia os dados atualizados para o repositório
        }
    }
}