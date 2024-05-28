## Sistema de Cadastros

Este projeto consiste de um sistema de cadastro simples, onde o usuário preenche um formulário através do terminal, com perguntas que estão gravadas em arquivo *txt*, e é cadastrado no sistema com um *txt* com suas informações.

## Como executar
- Java na versão 8 ou superior (pois usa classes de tal versão)
- Uma IDE ou um editor de texto (Vs Code por exemplo)

### Funcionalidades
- Cadastrar usuários: através do terminal são respondidas as perguntas no *formulario.txt*, tais respostas são armazenadas em outro *txt* com o nome do usuário.
- Listar usuários: todos os usuários cadastrados são mostrados no temrinal.
- Pesquisar usuários por nome: Digitando um nome ou parte de um são mostrados usuários cadastrados cujo nome correspondem com a pesquisa.
- Cadastrar nova pergunta: É possível adicionar novas perguntas ao *formulario.txt*
- Deletar pergunta: É possível deletar uma pergunta do formulário desde que esta não seja uma das 4 perguntas originais, pois não podem ser removidas.

## Próximos passos
- Implementação de um banco de dados para substituir os arquivos *txt*
- Interface