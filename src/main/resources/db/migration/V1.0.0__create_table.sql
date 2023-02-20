IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'appointmentNotes')
BEGIN
	EXEC('CREATE SCHEMA appointmentNotes')
END

-- apagando as tabelas caso ja existam
-- DROP TABLE appointmentNotes.horasApontadas;

-- DROP TABLE appointmentNotes.projetoAtuacao;

-- DROP TABLE appointmentNotes.usuario;

-- DROP TABLE appointmentNotes.perfilUsuario;

CREATE TABLE appointmentNotes.usuario (
	id BIGINT PRIMARY KEY,
	dataCriacao DATE,
	dataAtualizacao DATE,
	statusBD BIT,
	nome VARCHAR(255),
	email VARCHAR(255),
	password VARCHAR(255),
	googleId VARCHAR(255),
	googleToken VARCHAR(255)
);

-- criando tabelas do projeto
CREATE TABLE appointmentNotes.perfilUsuario (
	id BIGINT PRIMARY KEY,
	dataCriacao DATE,
	dataAtualizacao DATE,
	statusBD BIT,
	descricao VARCHAR(255),
	usuarioId BIGINT,
	FOREIGN KEY (usuarioId) REFERENCES appointmentNotes.usuario(id)
);

CREATE TABLE appointmentNotes.projetoAtuacao (
	id BIGINT PRIMARY KEY,
	dataCriacao DATE,
	dataAtualizacao DATE,
	statusBD BIT,
	nome VARCHAR(255),
	descricao VARCHAR(255),
	usuarioId BIGINT,
	FOREIGN KEY (usuarioId) REFERENCES appointmentNotes.usuario(id)
);

CREATE TABLE appointmentNotes.horasApontadas (
	id BIGINT PRIMARY KEY,
	dataCriacao DATE,
	dataAtualizacao DATE,
	statusBD BIT,
	horas INT,
	data DATE,
	usuarioId BIGINT,
	FOREIGN KEY (usuarioId) REFERENCES appointmentNotes.usuario(id)
);


