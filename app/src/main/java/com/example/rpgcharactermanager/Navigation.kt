package com.example.rpgcharactermanager

// Define uma classe selada para representar as telas de navegação do aplicativo
sealed class Screen(val route: String) {

    // Tela para listar todos os personagens
    object ListarPersonagens : Screen("listarPersonagens")

    // Tela para criar um novo personagem
    object NovoPersonagem : Screen("NovoPersonagem")

    // Tela para exibir os detalhes de um personagem específico
    object DetalhesPersonagem : Screen("DetalhesPersonagem/{personagemId}") {
        // Função para criar a rota com o ID do personagem dinâmico
        fun createRoute(personagemId: Int) = "detalhesPersonagem/$personagemId"
    }

    // Tela para editar um personagem específico
    object EditarPersonagem : Screen("EditarPersonagem") {
        // Função para criar a rota com o ID do personagem dinâmico
        fun createRoute(personagemId: Int) = "editarPersonagem/$personagemId"
    }

    // Tela para gerenciar os equipamentos de um personagem
    object Equipamentos : Screen("Equipamentos") {
        // Função para criar a rota com o ID do personagem dinâmico
        fun createRoute(personagemId: Int) = "equipamentos/$personagemId"
    }

    // Tela para gerenciar as habilidades de um personagem
    object Habilidades : Screen("Habilidades") {
        // Função para criar a rota com o ID do personagem dinâmico
        fun createRoute(personagemId: Int) = "habilidades/$personagemId"
    }

    // Tela para editar a história (background) de um personagem
    object Historia : Screen("Historia") {
        // Função para criar a rota com o ID do personagem dinâmico
        fun createRoute(personagemId: Int) = "historia/$personagemId"
    }
}