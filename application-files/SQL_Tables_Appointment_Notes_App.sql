-- criando o schema caso nao exista

-- Criando o SCHEMA caso ainda nao exista.
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'appointmentNotes')
BEGIN
	EXEC('CREATE SCHEMA appointmentNotes')
END

-- apagando as tabelas caso ja existam
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'appointmentNotes.horasApontadas') AND type in (N'U'))
DROP TABLE appointmentNotes.horasApontadas;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'appointmentNotes.projetoAtuacao') AND type in (N'U'))
DROP TABLE appointmentNotes.projetoAtuacao;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'appointmentNotes.usuario') AND type in (N'U'))
DROP TABLE appointmentNotes.usuario;

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'appointmentNotes.perfilUsuario') AND type in (N'U'))
DROP TABLE appointmentNotes.perfilUsuario;

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


select * from appointmentNotes.usuario u ;


