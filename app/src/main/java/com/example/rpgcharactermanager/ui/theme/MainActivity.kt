package com.example.rpgcharactermanager.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rpgcharactermanager.Screen
import com.example.rpgcharactermanager.data.entity.Equipamento
import com.example.rpgcharactermanager.data.entity.Habilidade
import com.example.rpgcharactermanager.data.entity.Personagem
import com.example.rpgcharactermanager.viewmodel.DetalhesPersonagemViewModel
import com.example.rpgcharactermanager.viewmodel.EditarPersonagemViewModel
import com.example.rpgcharactermanager.viewmodel.EquipamentoViewModel
import com.example.rpgcharactermanager.viewmodel.HabilidadeViewModel
import com.example.rpgcharactermanager.viewmodel.HistoriaViewModel
import com.example.rpgcharactermanager.viewmodel.PersonagemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
                }
            }
        }

@Composable
fun AppNavigation() {
    // Controller de navegação para gerenciar as rotas da aplicação
    val navController = rememberNavController()

    // Define o NavHost para gerenciar as telas com base no estado de navegação
    NavHost(
        navController = navController,
        startDestination = Screen.ListarPersonagens.route // Tela inicial da aplicação
    ) {
        // Rota para a tela de listar personagens
        composable(Screen.ListarPersonagens.route) {
            MainScreen(
                // Navega para a tela de criação de um novo personagem
                onNavigateToNovoPersonagem = {
                    navController.navigate(Screen.NovoPersonagem.route)
                },
                // Navega para a tela de detalhes de um personagem específico
                onNavigateToDetalhesPersonagem = { personagemId ->
                    navController.navigate(Screen.DetalhesPersonagem.createRoute(personagemId))
                }
            )
        }

        // Rota para a tela de criar um novo personagem
        composable(Screen.NovoPersonagem.route) {
            NovoPersonagemScreen(
                // Retorna para a tela anterior
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Rota para a tela de detalhes de um personagem, recebe o ID do personagem como argumento
        composable(
            Screen.DetalhesPersonagem.route,
            arguments = listOf(navArgument("personagemId") { type = NavType.IntType }) // Define o tipo do argumento como inteiro
        ) { backStackEntry ->
            // Obtém o ID do personagem dos argumentos da navegação
            val personagemId = backStackEntry.arguments?.getInt("personagemId") ?: return@composable
            DetalhesPersonagemScreen(
                personagemId = personagemId,
                // Define ações de navegação para outras telas a partir da tela de detalhes
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEditarPersonagem = { navController.navigate(Screen.EditarPersonagem.createRoute(personagemId)) },
                onNavigateToEquipamentos = { navController.navigate(Screen.Equipamentos.createRoute(personagemId)) },
                onNavigateToHabilidades = { navController.navigate(Screen.Habilidades.createRoute(personagemId)) },
                onNavigateToHistoria = { navController.navigate(Screen.Historia.createRoute(personagemId)) }
            )
        }

        // Rota para a tela de edição de um personagem, também recebe o ID do personagem
        composable(
            Screen.EditarPersonagem.route + "/{personagemId}",
            arguments = listOf(navArgument("personagemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personagemId = backStackEntry.arguments?.getInt("personagemId") ?: return@composable
            EditarPersonagemScreen(
                personagemId = personagemId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Rota para a tela de gerenciamento de equipamentos, recebe o ID do personagem
        composable(
            Screen.Equipamentos.route + "/{personagemId}",
            arguments = listOf(navArgument("personagemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personagemId = backStackEntry.arguments?.getInt("personagemId") ?: return@composable
            EquipamentosScreen(
                personagemId = personagemId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Rota para a tela de gerenciamento de habilidades, recebe o ID do personagem
        composable(
            Screen.Habilidades.route + "/{personagemId}",
            arguments = listOf(navArgument("personagemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personagemId = backStackEntry.arguments?.getInt("personagemId") ?: return@composable
            HabilidadesScreen(
                personagemId = personagemId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Rota para a tela de histórico do personagem, recebe o ID do personagem
        composable(
            Screen.Historia.route + "/{personagemId}",
            arguments = listOf(navArgument("personagemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personagemId = backStackEntry.arguments?.getInt("personagemId") ?: return@composable
            HistoriaScreen(
                personagemId = personagemId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToNovoPersonagem: () -> Unit, // Callback para navegar para a tela de criação de personagem
    onNavigateToDetalhesPersonagem: (Int) -> Unit, // Callback para navegar para a tela de detalhes de um personagem
    viewModel: PersonagemViewModel = hiltViewModel() // Injeta automaticamente o ViewModel usando Hilt
) {
    // Observa a lista de personagens fornecida pelo ViewModel e atualiza automaticamente a UI
    val listarPersonagens by viewModel.listarPersonagens.collectAsState(initial = emptyList())

    // Estados mutáveis para controlar o diálogo de exclusão, o personagem selecionado para exclusão e a busca
    var showDialog by remember { mutableStateOf(false) }
    var personagemToDelete by remember { mutableStateOf<Personagem?>(null) }
    var searchQuery by remember { mutableStateOf("") } // Query para filtrar personagens na barra de busca

    Scaffold(
        topBar = {
            Column {
                // Barra superior da aplicação com título
                TopAppBar(title = { Text("RPG Character Manager") })
                // Campo de texto para busca
                OutlinedTextField(
                    value = searchQuery, // Valor atual da busca
                    onValueChange = { searchQuery = it }, // Atualiza a query ao digitar
                    label = { Text("Buscar Personagem") }, // Rótulo do campo
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") }, // Ícone de busca
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        },
        floatingActionButton = {
            // Botão flutuante para adicionar um novo personagem
            FloatingActionButton(onClick = onNavigateToNovoPersonagem) {
                Icon(Icons.Filled.Add, contentDescription = "Novo Personagem")
            }
        }
    ) { paddingValues ->
        // Filtra os personagens com base na query de busca
        val filteredPersonagens = listarPersonagens.filter { personagem ->
            personagem.nome.contains(searchQuery, ignoreCase = true) // Busca sem diferenciar maiusculas/minusculas
        }

        if (filteredPersonagens.isEmpty()) {
            // Mensagem de quando não há personagens encontrados ou adicionados
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues), // Respeita o espaço reservado pela Scaffold
                contentAlignment = Alignment.Center // Centraliza o conteúdo
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear, // Ícone de feedback visual
                        contentDescription = "Nenhum Personagem",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Nenhum personagem encontrado.",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Adicione um novo personagem clicando no botão abaixo.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        } else {
            // Lista de personagens filtrados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Itera pelos personagens filtrados e exibe cada um como um item
                items(filteredPersonagens) { personagem ->
                    PersonagemItem(
                        personagem = personagem, // Dados do personagem
                        onClick = { onNavigateToDetalhesPersonagem(personagem.id) }, // Navega para detalhes ao clicar
                        onDeletar = {
                            personagemToDelete = personagem // Armazena o personagem para exclusão
                            showDialog = true // Exibe o diálogo de confirmação
                        }
                    )
                }
            }
        }
    }

    // Diálogo de confirmação para deletar um personagem
    if (showDialog && personagemToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Fecha o diálogo ao clicar fora
            title = { Text("Confirmação") },
            text = { Text("Você tem certeza que deseja deletar este personagem?") },
            confirmButton = {
                Button(
                    onClick = {
                        personagemToDelete?.let {
                            viewModel.deletarPersonagem(it) // Exclui o personagem via ViewModel
                        }
                        showDialog = false // Fecha o diálogo após confirmar
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false } // Fecha o diálogo após cancelar
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun PersonagemItem(
    personagem: Personagem, // O personagem que será exibido no item
    onClick: () -> Unit, // Callback para ação de clique no item
    onDeletar: () -> Unit // Callback para ação de deletar o personagem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = personagem.nome,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1, // Restringe a uma linha.
                overflow = TextOverflow.Ellipsis // Adiciona "..." para nomes longos.
            )
            Text(
                text = "Level: ${personagem.level}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        // Botão para deletar o personagem.
        IconButton(onClick = onDeletar) { // Adiciona um botão no lado direito do item.
            Icon(
                imageVector = Icons.Filled.Delete, // Ícone de lixeira para deletar
                contentDescription = "Deletar Personagem",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoPersonagemScreen(
    viewModel: PersonagemViewModel = hiltViewModel(), // Injeta o ViewModel usando Hilt para gerenciamento de dependência
    onNavigateBack: () -> Unit // Callback para navegar de volta à tela anterior
) {
    // Estados locais para armazenar os valores dos campos do formulário.
    var nome by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var classe by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("1") }
    var mensagemErro by remember { mutableStateOf<String?>(null) } // Armazena uma mensagem de erro, se houver

    // Estrutura principal da tela usando Scaffold para gerenciar layout e barras.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Personagem") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar") // Ícone de voltar
                    }
                }
            )
        }
    ) { padding -> // Padding adicional fornecido pelo Scaffold
        // Layout principal da tela
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Campo de texto para o nome do personagem
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Personagem") },
                modifier = Modifier.fillMaxWidth(),
                isError = nome.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para a raça do personagem
            OutlinedTextField(
                value = raca,
                onValueChange = { raca = it },
                label = { Text("Raça do Personagem") },
                modifier = Modifier.fillMaxWidth(),
                isError = raca.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para a classe do personagem
            OutlinedTextField(
                value = classe,
                onValueChange = { classe = it },
                label = { Text("Classe do Personagem") },
                modifier = Modifier.fillMaxWidth(),
                isError = classe.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para o nível do personagem
            OutlinedTextField(
                value = level,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) level = newValue // Permite apenas dígitos
                },
                label = { Text("Level do Personagem") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Configura o teclado numérico
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Exibe a mensagem de erro, caso exista
            if (mensagemErro != null) {
                Text(
                    text = mensagemErro!!, // Mostra o texto da mensagem de erro
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Botão para criar um novo personagem
            Button(
                onClick = {
                    // Verifica se algum campo está vazio
                    if (nome.isBlank() || raca.isBlank() || classe.isBlank()) {
                        mensagemErro = "Preencha todos os campos."
                    } else {
                        // Cria um objeto Personagem com os valores dos campos
                        val novoPersonagem = Personagem(
                            nome = nome,
                            raca = raca,
                            classe = classe,
                            level = level.toIntOrNull() ?: 1, // Converte o nível para inteiro ou usa 1 como padrão
                            // Valores padrão para os atributos
                            forca = 10,
                            destreza = 10,
                            vigor = 10,
                            inteligencia = 10,
                            sabedoria = 10,
                            carisma = 10,
                            background = ""
                        )
                        viewModel.criarPersonagem(novoPersonagem) // Envia o personagem para ser salvo pelo ViewModel
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Criar Personagem")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesPersonagemScreen(
    personagemId: Int, // ID do personagem cujos detalhes serão exibidos
    viewModel: DetalhesPersonagemViewModel = hiltViewModel(), // ViewModel injetado com Hilt
    onNavigateBack: () -> Unit, // Callback para navegar de volta à tela anterior
    onNavigateToEditarPersonagem: () -> Unit, // Callback para navegar para a tela de edição do personagem
    onNavigateToEquipamentos: () -> Unit, // Callback para navegar para a tela de equipamentos
    onNavigateToHabilidades: () -> Unit, // Callback para navegar para a tela de habilidades
    onNavigateToHistoria: () -> Unit // Callback para navegar para a tela de história
) {
    // Observa o estado do personagem no ViewModel
    val personagem by viewModel.personagem.collectAsState()

    // Carrega os detalhes do personagem quando a tela é iniciada
    LaunchedEffect(personagemId) {
        viewModel.carregarPersonagem(personagemId) // Chama o método do ViewModel para buscar os dados do personagem
    }

    // Estrutura principal da tela usando Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(personagem?.nome ?: "Detalhes do Personagem") // Exibe o nome do personagem ou um texto padrão se estiver nulo
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Botão para voltar à tela anterior
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToEditarPersonagem) { // Botão para editar o personagem
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
            )
        }
    ) { padding -> // Adiciona padding do Scaffold
        // Exibe os detalhes do personagem apenas se ele não for nulo
        personagem?.let { char ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Permite rolagem vertical
            ) {
                // Exibição do nome e nível do personagem
                Text(
                    text = char.nome,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Level ${char.level}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Exibição de classe e raça
                Text(
                    text = "${char.raca} | ${char.classe}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Componente personalizado para exibir os atributos do personagem
                Atributos(char)

                Spacer(modifier = Modifier.height(16.dp))

                // Botão para acessar a tela de equipamentos
                Button(
                    onClick = onNavigateToEquipamentos,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Equipamentos")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botão para acessar a tela de habilidades
                Button(
                    onClick = onNavigateToHabilidades,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Habilidades")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botão para acessar a tela de história do personagem
                Button(
                    onClick = onNavigateToHistoria,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("História do Personagem")
                }
            }
        }
    }
}

@Composable
private fun Atributos(personagem: Personagem) {
    // Cria um Card para encapsular o conteúdo dos atributos
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Atributos",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Exibe cada atributo do personagem usando o composable LinhaAtributos
            LinhaAtributos("Força", personagem.forca)
            LinhaAtributos("Destreza", personagem.destreza)
            LinhaAtributos("Vigor", personagem.vigor)
            LinhaAtributos("Inteligência", personagem.inteligencia)
            LinhaAtributos("Sabedoria", personagem.sabedoria)
            LinhaAtributos("Carisma", personagem.carisma)
        }
    }
}

@Composable
private fun LinhaAtributos(name: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween // Espaça igualmente os itens a esquerda e a direita
    ) {
        Text(name) // Exibe o nome do atributo à esquerda
        Text(value.toString()) // Exibe o valor do atributo à direita
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPersonagemScreen(
    personagemId: Int, // ID do personagem a ser editado
    viewModel: EditarPersonagemViewModel = hiltViewModel(), // ViewModel gerenciado por Hilt
    onNavigateBack: () -> Unit // Callback para voltar a tela anterior
) {
    // Estado que observa o personagem carregado do ViewModel
    val personagem by viewModel.estadoPersonagem.collectAsState()

    // Estado para exibir ou ocultar o diálogo de exclusão
    var showDialog by remember { mutableStateOf(false) }

    // Estados para os campos editáveis do personagem
    var nome by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var classe by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var forca by remember { mutableStateOf("") }
    var destreza by remember { mutableStateOf("") }
    var vigor by remember { mutableStateOf("") }
    var inteligencia by remember { mutableStateOf("") }
    var sabedoria by remember { mutableStateOf("") }
    var carisma by remember { mutableStateOf("") }

    // Estado para exibir mensagens de erro
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    // Carrega os dados do personagem ao abrir a tela
    LaunchedEffect(personagemId) {
        viewModel.carregarPersonagem(personagemId) // Busca os dados com base no ID
    }

    // Atualiza os valores dos campos quando os dados do personagem são carregados
    LaunchedEffect(personagem) {
        personagem?.let {
            nome = it.nome
            raca = it.raca
            classe = it.classe
            level = it.level.toString()
            forca = it.forca.toString()
            destreza = it.destreza.toString()
            vigor = it.vigor.toString()
            inteligencia = it.inteligencia.toString()
            sabedoria = it.sabedoria.toString()
            carisma = it.carisma.toString()
        }
    }

    // Layout principal da tela usando Scaffold
    Scaffold(
        topBar = {
            // Barra superior com título e botão de navegação
            TopAppBar(
                title = { Text("Editar Personagem") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Permite rolar o conteúdo verticalmente
        ) {
            // Seção de informações principais
            Text(
                text = "Dados do Personagem",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Campos editáveis para as informações principais
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                isError = nome.isBlank() // Exibe erro se o campo estiver vazio
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = raca,
                onValueChange = { raca = it },
                label = { Text("Raça") },
                modifier = Modifier.fillMaxWidth(),
                isError = raca.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = classe,
                onValueChange = { classe = it },
                label = { Text("Classe") },
                modifier = Modifier.fillMaxWidth(),
                isError = classe.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = level,
                onValueChange = { level = it },
                label = { Text("Level") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Somente números
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Seção de atributos
            Text(
                text = "Atributos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Campos para edição de atributos
            AttributosTextField("Força", forca) { forca = it }
            AttributosTextField("Destreza", destreza) { destreza = it }
            AttributosTextField("Vigor", vigor) { vigor = it }
            AttributosTextField("Inteligência", inteligencia) { inteligencia = it }
            AttributosTextField("Sabedoria", sabedoria) { sabedoria = it }
            AttributosTextField("Carisma", carisma) { carisma = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Exibe mensagens de erro, se houver
            if (mensagemErro != null) {
                Text(
                    text = mensagemErro!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Botão para salvar alterações
            Button(
                onClick = {
                    if (nome.isBlank() || raca.isBlank() || classe.isBlank()) {
                        mensagemErro = "Preencha todos os campos obrigatórios."
                    } else {
                        personagem?.let {
                            // Atualiza o personagem com os novos valores
                            viewModel.atualizarPersonagem(
                                it.copy(
                                    nome = nome,
                                    raca = raca,
                                    classe = classe,
                                    level = level.toIntOrNull() ?: it.level,
                                    forca = forca.toIntOrNull() ?: it.forca,
                                    destreza = destreza.toIntOrNull() ?: it.destreza,
                                    vigor = vigor.toIntOrNull() ?: it.vigor,
                                    inteligencia = inteligencia.toIntOrNull() ?: it.inteligencia,
                                    sabedoria = sabedoria.toIntOrNull() ?: it.sabedoria,
                                    carisma = carisma.toIntOrNull() ?: it.carisma
                                )
                            )
                        }
                        onNavigateBack() // Volta à tela anterior
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Mudanças")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para excluir o personagem
            Button(
                onClick = { showDialog = true }, // Exibe o diálogo de confirmação
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Deletar Personagem", color = MaterialTheme.colorScheme.onError)
            }
        }
    }

    // Diálogo de confirmação para exclusão
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Fecha o diálogo sem excluir
            title = { Text("Confirmar Exclusão") },
            text = { Text("Você tem certeza que deseja excluir este personagem?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        personagem?.let {
                            viewModel.deletarPersonagem(it) // Exclui o personagem
                        }
                        showDialog = false
                        onNavigateBack() // Volta à tela anterior
                    }
                ) {
                    Text("Confirmar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun AttributosTextField(
    label: String, // O rótulo do campo
    value: String, // O valor atual do campo de entrada
    onValueChange: (String) -> Unit // Callback para atualizar o valor conforme o usuário digita
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipamentosScreen(
    personagemId: Int, // ID do personagem ao qual os equipamentos pertencem
    viewModel: EquipamentoViewModel = hiltViewModel(), // ViewModel injetado pelo Hilt
    onNavigateBack: () -> Unit // Função para navegação de volta
) {
    // Estados para gerenciar os valores dos campos de entrada e interação
    var itemNovoNome by remember { mutableStateOf("") } // Nome do equipamento
    var itemNovoTipo by remember { mutableStateOf("") } // Tipo do equipamento
    var itemNovoBonus by remember { mutableStateOf("") } // Bônus do equipamento
    var itemNovaDescricao by remember { mutableStateOf("") } // Descrição do equipamento
    var itemEditando by remember { mutableStateOf<Equipamento?>(null) } // Equipamento em edição, se houver
    var mensagemErro by remember { mutableStateOf<String?>(null) } // Mensagem de erro para feedback

    // Observa a lista de equipamentos do ViewModel
    val equipamento by viewModel.equipamento.collectAsState()

    // Carrega os equipamentos ao alterar o ID do personagem
    LaunchedEffect(personagemId) {
        viewModel.carregarEquipamento(personagemId)
    }

    // Estrutura da tela
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Equipamento") }, // Título da barra superior
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Botão para voltar
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp) // Espaçamento interno
        ) {
            // Se estiver editando um item, exibe os campos de edição
            itemEditando?.let { equipamentoEditar ->
                OutlinedTextField(
                    value = itemNovoNome,
                    onValueChange = {
                        itemNovoNome = it
                        mensagemErro = null // Reseta mensagem de erro
                    },
                    label = { Text("Nome do Item") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = itemNovoNome.isBlank() // Valida campo vazio
                )

                Spacer(modifier = Modifier.height(8.dp)) // Espaço entre campos

                OutlinedTextField(
                    value = itemNovoTipo,
                    onValueChange = {
                        itemNovoTipo = it
                        mensagemErro = null
                    },
                    label = { Text("Tipo do Item") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = itemNovoTipo.isBlank()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = itemNovaDescricao,
                    onValueChange = { itemNovaDescricao = it },
                    label = { Text("Descrição do Item") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = itemNovoBonus,
                    onValueChange = { itemNovoBonus = it },
                    label = { Text("Bônus do Item") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Apenas números
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Exibe mensagem de erro, se houver
                if (mensagemErro != null) {
                    Text(
                        text = mensagemErro!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Botão para salvar alterações no equipamento
                Button(
                    onClick = {
                        if (itemNovoNome.isBlank() || itemNovoTipo.isBlank()) {
                            mensagemErro = "Preencha todos os campos obrigatórios."
                        } else {
                            val valorBonus = itemNovoBonus.toIntOrNull() ?: 0 // Converte bônus para número
                            viewModel.atualizarEquipamento(
                                Equipamento(
                                    id = equipamentoEditar.id,
                                    personagemId = personagemId,
                                    nome = itemNovoNome,
                                    tipo = itemNovoTipo,
                                    descricao = itemNovaDescricao,
                                    bonus = valorBonus
                                )
                            )
                            // Limpa os estados após salvar
                            itemEditando = null
                            itemNovoNome = ""
                            itemNovoTipo = ""
                            itemNovaDescricao = ""
                            itemNovoBonus = ""
                            mensagemErro = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Atualizar Equipamento")
                }
            } ?: run {
                // Campo de texto para o nome do equipamento
                OutlinedTextField(
                    value = itemNovoNome,
                    onValueChange = {
                        itemNovoNome = it
                        mensagemErro = null
                    },
                    label = { Text("Nome do Item") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = itemNovoNome.isBlank()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de texto para o tipo do equipamento
                OutlinedTextField(
                    value = itemNovoTipo,
                    onValueChange = {
                        itemNovoTipo = it
                        mensagemErro = null
                    },
                    label = { Text("Tipo do Item") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = itemNovoTipo.isBlank()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de texto para a descrição do equipamento
                OutlinedTextField(
                    value = itemNovaDescricao,
                    onValueChange = { itemNovaDescricao = it },
                    label = { Text("Descrição do Item") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de texto para o bônus do equipamento
                OutlinedTextField(
                    value = itemNovoBonus,
                    onValueChange = { itemNovoBonus = it },
                    label = { Text("Bônus do Item") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Exibe mensagem de erro
                if (mensagemErro != null) {
                    Text(
                        text = mensagemErro!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Botão para adicionar novo equipamento
                Button(
                    onClick = {
                        if (itemNovoNome.isBlank() || itemNovoTipo.isBlank()) {
                            mensagemErro = "Preencha todos os campos obrigatórios."
                        } else {
                            val valorBonus = itemNovoBonus.toIntOrNull() ?: 0
                            viewModel.adicionarEquipamento(
                                Equipamento(
                                    personagemId = personagemId,
                                    nome = itemNovoNome,
                                    tipo = itemNovoTipo,
                                    descricao = itemNovaDescricao,
                                    bonus = valorBonus
                                )
                            )
                            // Reseta campos após adicionar
                            itemNovoNome = ""
                            itemNovoTipo = ""
                            itemNovaDescricao = ""
                            itemNovoBonus = ""
                            mensagemErro = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Adicionar Equipamento")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de equipamentos cadastrados
            LazyColumn {
                items(equipamento) { item ->
                    EquipamentoItem(
                        equipamento = item,
                        onDelete = { viewModel.deletarEquipamento(item) }, // Função para deletar equipamento
                        onEdit = { equipamentoEditado -> // Função para editar equipamento
                            itemEditando = equipamentoEditado
                            itemNovoNome = equipamentoEditado.nome
                            itemNovoTipo = equipamentoEditado.tipo
                            itemNovaDescricao = equipamentoEditado.descricao
                            itemNovoBonus = equipamentoEditado.bonus.toString()
                            mensagemErro = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EquipamentoItem(
    equipamento: Equipamento, // O equipamento a ser exibido na interface
    onDelete: () -> Unit, // Callback acionado quando o equipamento for deletado
    onEdit: (Equipamento) -> Unit // Callback acionado quando o equipamento for editado
) {
    // Estado local para controlar se o diálogo de confirmação de exclusão está visível
    var showDialog by remember { mutableStateOf(false) }

    // Organiza o conteúdo horizontalmente.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Distribui os elementos nas extremidades
    ) {
        // Coluna para exibir os detalhes do equipamento
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Exibe o nome do equipamento
            Text(
                text = equipamento.nome,
                style = MaterialTheme.typography.headlineSmall
            )
            // Exibe o tipo do equipamento
            Text(
                text = "Tipo: ${equipamento.tipo}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Exibe a descrição do equipamento
            Text(
                text = "Descrição: ${equipamento.descricao}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Exibe o bônus do equipamento
            Text(
                text = "Bonus: ${equipamento.bonus}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Linha de botões para editar e deletar
        Row {
            // Botão para editar o equipamento
            IconButton(onClick = { onEdit(equipamento) }) {
                Icon(
                    Icons.Default.Edit, // Ícone de edição
                    contentDescription = "Editar Equipamento" // Descrição do ícone para acessibilidade
                )
            }

            // Botão para deletar o equipamento
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    Icons.Default.Delete, // Ícone de exclusão
                    contentDescription = "Deletar Equipamento" // Descrição do ícone para acessibilidade
                )
            }
        }
    }

    // Verifica se o diálogo de confirmação deve ser exibido.
    if (showDialog) {
        // Diálogo de alerta para confirmar a exclusão
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Fecha o diálogo ao clicar fora dele
            title = { Text("Confirmação") },
            text = { Text("Você tem certeza que deseja deletar este equipamento?") },
            confirmButton = { // Botão de confirmação para deletar o equipamento
                Button(
                    onClick = {
                        onDelete() // Aciona o callback de exclusão
                        showDialog = false // Fecha o diálogo
                    }
                ) {
                    Text("Confirmar") // Texto do botão de confirmação
                }
            },
            dismissButton = { // Botão de cancelamento para fechar o diálogo
                Button(
                    onClick = { showDialog = false } // Fecha o diálogo
                ) {
                    Text("Cancelar") // Texto do botão de cancelamento
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabilidadesScreen(
    personagemId: Int, // ID do personagem para o qual as habilidades serão carregadas
    viewModel: HabilidadeViewModel = hiltViewModel(), // ViewModel injetado via Hilt para gerenciar os dados de habilidades
    onNavigateBack: () -> Unit // Callback para ação de voltar à tela anterior
) {
    // Estados locais para controlar os valores dos campos de entrada
    var habilidadeNovoNome by remember { mutableStateOf("") }
    var habilidadeNovoTipo by remember { mutableStateOf("") }
    var habilidadeNovaDescricao by remember { mutableStateOf("") }
    var habilidadeNovoLevelRequirido by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) } // Mensagem de erro para validação de entrada

    // Estado que coleta a lista de habilidades do ViewModel
    val habilidades by viewModel.habilidade.collectAsState()

    // Estado para controlar a habilidade atualmente sendo editada
    var habilidadeEditando by remember { mutableStateOf<Habilidade?>(null) }

    // Carrega as habilidades do personagem ao inicializar a composable
    LaunchedEffect(personagemId) {
        viewModel.carregarHabilidade(personagemId)
    }

    // Estrutura base da tela com AppBar e espaço para conteúdo
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habilidades") }, // Título do AppBar
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Botão para voltar
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding -> // Padding fornecido pelo Scaffold
        // Layout principal da tela
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Respeita o padding fornecido pelo Scaffold
                .padding(16.dp)
        ) {
            // Campo de texto para o nome da habilidade
            OutlinedTextField(
                value = habilidadeNovoNome,
                onValueChange = {
                    habilidadeNovoNome = it
                    mensagemErro = null // Limpa mensagem de erro ao editar o campo
                },
                label = { Text("Nome da Habilidade") },
                modifier = Modifier.fillMaxWidth(),
                isError = habilidadeNovoNome.isBlank() // Exibe erro se o campo estiver vazio
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para o tipo da habilidade
            OutlinedTextField(
                value = habilidadeNovoTipo,
                onValueChange = {
                    habilidadeNovoTipo = it
                    mensagemErro = null
                },
                label = { Text("Tipo da Habilidade") },
                modifier = Modifier.fillMaxWidth(),
                isError = habilidadeNovoTipo.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para descrição da habilidade
            OutlinedTextField(
                value = habilidadeNovaDescricao,
                onValueChange = { habilidadeNovaDescricao = it },
                label = { Text("Descrição da Habilidade") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para nível requerido (aceita apenas números)
            OutlinedTextField(
                value = habilidadeNovoLevelRequirido,
                onValueChange = { habilidadeNovoLevelRequirido = it },
                label = { Text("Level Requirido da Habilidade") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Exibição da mensagem de erro, se houver
            if (mensagemErro != null) {
                Text(
                    text = mensagemErro!!,
                    color = MaterialTheme.colorScheme.error, // Estilo de erro
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Botão para adicionar ou atualizar uma habilidade
            Button(
                onClick = {
                    // Validações dos campos obrigatórios
                    if (habilidadeNovoNome.isBlank() || habilidadeNovoTipo.isBlank()) {
                        mensagemErro = "Preencha todos os campos obrigatórios."
                    } else {
                        // Converte o nível requerido para inteiro, com valor padrão 1 em caso de erro
                        val levelRequirido = habilidadeNovoLevelRequirido.toIntOrNull() ?: 1
                        if (habilidadeEditando == null) {
                            // Adiciona uma nova habilidade
                            viewModel.adicionarHabilidade(
                                Habilidade(
                                    personagemId = personagemId,
                                    nome = habilidadeNovoNome,
                                    tipo = habilidadeNovoTipo,
                                    descricao = habilidadeNovaDescricao,
                                    levelRequirido = levelRequirido
                                )
                            )
                        } else {
                            // Atualiza a habilidade existente
                            habilidadeEditando?.let { habilidade ->
                                viewModel.atualizarHabilidade(
                                    habilidade.copy(
                                        nome = habilidadeNovoNome,
                                        tipo = habilidadeNovoTipo,
                                        descricao = habilidadeNovaDescricao,
                                        levelRequirido = levelRequirido
                                    )
                                )
                            }
                        }
                        // Limpa os campos após adicionar ou atualizar
                        habilidadeNovoNome = ""
                        habilidadeNovoTipo = ""
                        habilidadeNovaDescricao = ""
                        habilidadeNovoLevelRequirido = ""
                        habilidadeEditando = null
                        mensagemErro = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Texto do botão muda dependendo se é uma adição ou edição
                Text(if (habilidadeEditando == null) "Adicionar Habilidade" else "Atualizar Habilidade")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de habilidades utilizando LazyColumn para renderização eficiente
            LazyColumn {
                items(habilidades) { habilidade ->
                    // Componente de item de habilidade
                    HabilidadeItem(
                        habilidade = habilidade,
                        onDelete = { viewModel.deletarHabilidade(habilidade) }, // Callback para deletar
                        onEdit = { habilidadeEditada ->
                            // Preenche os campos para edição da habilidade
                            habilidadeEditando = habilidadeEditada
                            habilidadeNovoNome = habilidadeEditada.nome
                            habilidadeNovoTipo = habilidadeEditada.tipo
                            habilidadeNovaDescricao = habilidadeEditada.descricao
                            habilidadeNovoLevelRequirido = habilidadeEditada.levelRequirido.toString()
                            mensagemErro = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HabilidadeItem(
    habilidade: Habilidade, // Objeto contendo os dados da habilidade
    onDelete: () -> Unit, // Callback chamado ao confirmar a exclusão da habilidade
    onEdit: (Habilidade) -> Unit // Callback chamado ao clicar para editar a habilidade
) {
    // Variável que controla a exibição do diálogo de confirmação de exclusão
    var showDialog by remember { mutableStateOf(false) }

    // Linha principal que exibe os dados da habilidade e os ícones de ação
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Espaça os elementos horizontalmente
    ) {
        // Coluna que exibe as informações textuais da habilidade
        Column(
            modifier = Modifier.weight(1f) // Garante que a coluna ocupe o máximo de espaço disponível
        ) {
            // Exibe o nome da habilidade em estilo de título
            Text(
                text = habilidade.nome,
                style = MaterialTheme.typography.headlineSmall
            )
            // Exibe o tipo da habilidade
            Text(
                text = "Tipo: ${habilidade.tipo}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Exibe a descrição da habilidade
            Text(
                text = "Descrição: ${habilidade.descricao}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Exibe o nível requerido para a habilidade, com um pequeno espaçamento acima
            Text(
                text = "Level Requerido: ${habilidade.levelRequirido}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Linha que contém os botões de edição e exclusão
        Row {
            // Botão para editar a habilidade
            IconButton(onClick = { onEdit(habilidade) }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Habilidade") // Ícone de edição
            }

            // Botão para abrir o diálogo de confirmação de exclusão
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Deletar Habilidade") // Ícone de exclusão
            }
        }
    }

    // Diálogo de confirmação para exclusão da habilidade
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Fecha o diálogo ao clicar fora dele
            title = { Text("Confirmação") }, // Título do diálogo
            text = { Text("Você tem certeza que deseja deletar esta habilidade?") }, // Mensagem do diálogo
            confirmButton = {
                // Botão para confirmar a exclusão
                Button(
                    onClick = {
                        onDelete() // Chama o callback de exclusão
                        showDialog = false // Fecha o diálogo após a exclusão
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                // Botão para cancelar a exclusão
                Button(
                    onClick = { showDialog = false } // Apenas fecha o diálogo
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoriaScreen(
    personagemId: Int, // ID do personagem cujos detalhes serão exibidos e editados
    viewModel: HistoriaViewModel = hiltViewModel(), // ViewModel injetado com Hilt para lógica de negócios
    onNavigateBack: () -> Unit // Callback para navegação ao voltar
) {
    // Estado local para armazenar o texto da história do personagem
    var historia by remember { mutableStateOf("") }

    // Estado observado que mantém os dados do personagem
    val personagem by viewModel.personagem.collectAsState()

    // Efeito colateral disparado ao carregar a tela, carrega os dados do personagem pelo ID
    LaunchedEffect(personagemId) {
        viewModel.carregarPersonagem(personagemId)
    }

    // Efeito colateral para atualizar o estado da história localmente quando o personagem é carregado
    LaunchedEffect(personagem) {
        historia = personagem?.background ?: "" // Define o texto inicial como o background do personagem
    }

    // Componente de interface que fornece uma estrutura padrão com barra superior e corpo
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("História do Personagem") }, // Título na barra superior
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Ícone de navegação para voltar
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        // Coluna para organizar os elementos de layout verticalmente
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Respeita os paddings do Scaffold
                .padding(16.dp)
        ) {
            // Campo de texto para edição do background do personagem
            OutlinedTextField(
                value = historia,
                onValueChange = { historia = it },
                label = { Text("Background do Personagem") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para salvar a história e retornar à tela anterior
            Button(
                onClick = {
                    personagem?.let { // Verifica se o personagem não é nulo
                        viewModel.atualizarBackground( // Atualiza o background do personagem no ViewModel
                            it.copy(background = historia) // Cria uma cópia com o novo texto
                        )
                    }
                    onNavigateBack() // Navega de volta à tela anterior
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar História")
            }
        }
    }
}