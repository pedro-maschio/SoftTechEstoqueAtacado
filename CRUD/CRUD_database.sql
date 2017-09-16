create database CRUD;
use CRUD;

CREATE TABLE produto (
	id INTEGER UNIQUE AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100),
    preco FLOAT,
    quantidade INTEGER
);


