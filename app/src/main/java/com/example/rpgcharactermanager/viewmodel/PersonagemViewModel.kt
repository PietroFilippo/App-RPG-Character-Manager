package com.example.rpgcharactermanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rpgcharactermanager.data.entity.Personagem
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// Gerencia a lista e operações relacionadas a personagens
@HiltViewModel
class PersonagemViewModel @Inject constructor(
    private val repository: PersonagemRepository // Repositório para acessar os dados do banco
) : ViewModel() {

    // Fluxo que fornece a lista de personagens do banco de dados em tempo real
    val listarPersonagens: Flow<List<Personagem>> =
        repository.listarPersonagens() // Obtém os personagens do repositório
            .stateIn( // Converte para um StateFlow para melhor gerenciamento no escopo do ViewModel
                viewModelScope, // Escopo de execução para manter o fluxo ativo enquanto o ViewModel existe
                SharingStarted.Lazily, // Inicia o fluxo somente quando há observadores
                emptyList() // Valor inicial da lista enquanto os dados não são carregados
            )

    // Função para criar um novo personagem
    fun criarPersonagem(personagem: Personagem) {
        viewModelScope.launch {
            repository.inserirPersonagem(personagem) // Insere o novo personagem no banco
        }
    }

    // Função para deletar um personagem existente
    fun deletarPersonagem(personagem: Personagem) = viewModelScope.launch {
        repository.deletarPersonagem(personagem) // Remove o personagem do banco
    }
}