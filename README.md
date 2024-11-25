# App-RPG-Character-Manager
Uma aplicação mobile para Android que permita aos usuários criar, gerenciar e organizar seus personagens de RPG de forma intuitiva e eficiente.

Escopo do Projeto:
- Tela Inicial - MainActivity
  - Visualização em lista de todos os personagens, permitindo a criação, exclusão, busca de personagens.
 
- Tela ao clicar na criação de personagem - NovoPersonagemActivity
   - Permite de criação do personagem. Pode salvar (retornando à lista) ou cancelar.

- Tela ao cliclar no personagem - DetalhesPersonagemActivity
  - Mostra as informações básicas e os atributos do personagem. Também mostra o gerenciamento de equipamentos e habilidades e o editor para o background do personagem.

- Tela ao clicar em editar personagem - EditarPersonagemActivity
   - Tela para edição das informações básicas e atributos, também permite a exclusão do personagem. Retorna para a tela de detalhes após salvar

- Tela ao clicar no gerenciamento de equipamentos - EquipamentosActivity
  - Gerenciamento específico de equipamentos, permitindo a criação, edição e exclusão deles. Mudanças são salvas voltando para detalhes
 
- Tela ao clicar no gerenciamento de habilidades - HabilidadesActivity
  - Gerenciamento específico de habilidades, permitindo a criação, edição e exclusão delas. Mudanças são salvas voltando para detalhes
 
- Tela ao clicar no gerenciamento da história do personagem - HistoriaActivity
  - Editor dedicado para o background do personagem. Mudanças são salvas voltando para detalhes

