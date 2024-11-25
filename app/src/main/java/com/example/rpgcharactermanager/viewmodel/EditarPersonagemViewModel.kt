package com.example.rpgcharactermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpgcharactermanager.data.entity.Personagem
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

// Gerencia a lógica de edição de personagens
@HiltViewModel
class EditarPersonagemViewModel @Inject constructor(
    private val repository: PersonagemRepository // Repositório para operações de banco de dados relacionadas a Personagem
) : ViewModel() {

    // Estado interno do personagem que está sendo editado
    private val _estadoPersonagem = MutableStateFlow<Personagem?>(null)

    // Exposição do estado como um fluxo somente leitura para observadores externos
    val estadoPersonagem = _estadoPersonagem.asStateFlow()

    // Carrega os dados de um personagem pelo ID e atualiza o estado interno
    fun carregarPersonagem(id: Int) {
        viewModelScope.launch { // Inicia uma coroutine vinculada ao ciclo de vida do ViewModel
            repository.selecionarPersonagemId(id)
                .firstOrNull() // Obtém o primeiro valor emitido pelo fluxo ou null se o fluxo estiver vazio
                ?.let { _estadoPersonagem.value = it } // Atualiza o estado interno com o personagem carregado
        }
    }

    // Atualiza os dados de um personagem no banco de dados
    fun atualizarPersonagem(personagem: Personagem) {
        viewModelScope.launch {
            repository.atualizarPersonagem(personagem) // Chama o repositório para salvar as alterações
        }
    }

    // Deleta um personagem do banco de dados
    fun deletarPersonagem(personagem: Personagem) = viewModelScope.launch {
        repository.deletarPersonagem(personagem) // Remove o personagem usando o repositório
    }
}