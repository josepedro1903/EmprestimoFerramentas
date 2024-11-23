DROP DATABASE IF EXISTS gerenciador_emprestimos;

CREATE DATABASE IF NOT EXISTS gerenciador_emprestimos;

USE gerenciador_emprestimos;

DROP TABLE IF EXISTS amigos;
DROP TABLE IF EXISTS ferramentas;
DROP TABLE IF EXISTS emprestimos;
DROP TABLE IF EXISTS emprestimos_ferramentas;


CREATE TABLE IF NOT EXISTS amigos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS ferramentas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    marca VARCHAR(100),
    custo_aquisicao DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amigo_id INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    FOREIGN KEY (amigo_id) REFERENCES amigos(id)
);

CREATE TABLE IF NOT EXISTS emprestimos_ferramentas (
    emprestimo_id INT NOT NULL,
    ferramenta_id INT NOT NULL,
    PRIMARY KEY (emprestimo_id, ferramenta_id),
    FOREIGN KEY (emprestimo_id) REFERENCES emprestimos(id) ON DELETE CASCADE,
    FOREIGN KEY (ferramenta_id) REFERENCES ferramentas(id) ON DELETE CASCADE
);
