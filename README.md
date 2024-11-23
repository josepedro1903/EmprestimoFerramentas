# EmprestimoFerramentas
Este projeto tem por objetivo criar um sistema de empréstimo de ferramentas. O sistema utilizará um banco de dados para registrar diversas informações sobre o uso das ferramentas.

### Alunos colaboradores:
  - José Pedro Kuhnen Sens (RA 162210357) - josepedro1903
  - José Vitor Teófilo da Silva (RA 162210589) - JoseVitor2510 ou josevitorteofilosl@gmail.com
  - Lucas Oliveira de Sousa (RA 162220642) - LucasSousa96

### Professores:
  - Osmar de Oliveira Braz Junior
  - José Padilha Chrispim Neto

## Requisitos funcionais:

 - **RF001 - Cadastro e Gerenciamento de Amigos:**
   - O sistema deve conter funcionalidade do cadastro e gerenciamento de amigos, bem como Adição, edição, exclusão e listagem das informações.
   - Deverá conter  Id, Nome e telefone. 

 - **RF002 - Cadastro e Gerenciamento de Ferramentas:**
   - O sistema deve conter a funcionalidade  do cadastro e gerenciamento das ferramentas, bem como,  Adição, edição, exclusão e listagem das informações.
   - Deverá conter Id, Nome , Marca e CustoAquisição.
     
 - **RF003 - Cadastro Gerenciamento de Empréstimos:**
   - O sistema deve conter a funcionalidade de gerenciamento dos empréstimos , bem como Adição, Devolução e  listagem de emprestimos.
   - Devera conter Id, Data do empréstimo e de devolução.
 
 - **RF004 - Relatórios:** 
   - O sistema deve conter a funcionalidade de criar relatórios referente aos:
     - Emprestimos Ativos.
     - Status das ferramentas emprestadas.
     - Emprestimos Realizado e finalizados.
     - Ranking dos amigos que fizeram mais empréstimos.

## Requisitos não funcionais:
- **RNF001 - Sistema local:**
    - O deve Rodar localmente, ou seja na máquina do usuario. O armazenamento do banco de dados será local, utilizando mysql.

- **RNF002 - Simplicidade no Uso:** 
    - O sistema deverá ser facil de ser usado. Sem precisar de conhecimento técnico.

- **RNF003 - Desempenho:** 
    - O sistema deverá ser leve para a maquina do usuário sem comprometer o desempenho mesmo com muitos registros locais.


## Login Banco de Dados

  **Login:** root
  
  **Password:** admin

  **Caminho:** [src/main/java/util/Conexao.java](src/main/java/util/Conexao.java)
  
