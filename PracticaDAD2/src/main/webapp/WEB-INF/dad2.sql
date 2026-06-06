-- =========================
-- CREAR BASE DE DATOS
-- =========================
CREATE DATABASE IF NOT EXISTS dad2_24420162G_48845233H;

USE dad2_24420162G_48845233H;

-- =========================
-- USUARIOS DEL SISTEMA
-- =========================
CREATE TABLE IF NOT EXISTS Users (
	username VARCHAR(50) NOT NULL,
	password VARCHAR(100) NOT NULL,
	type VARCHAR(20) NOT NULL,
	PRIMARY KEY (username),
	CONSTRAINT chk_user_type CHECK (type IN ('ADMIN', 'STUDENT'))
);

-- =========================
-- TITULACIONES
-- =========================
CREATE TABLE IF NOT EXISTS Titulations (
    id VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_titulation_nombre (nombre)
);

-- =========================
-- ASIGNATURAS
-- =========================
CREATE TABLE IF NOT EXISTS Subjects (
	id VARCHAR(20) NOT NULL,
	tit_id VARCHAR(20) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	creditos INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (tit_id) REFERENCES Titulations(id)
);


-- Datos opcionales de prueba
INSERT INTO Users (username, password, type)
VALUES ('admin', 'admin', 'ADMIN');
INSERT INTO Users (username, password, type)
VALUES ('user1', 'user1', 'STUDENT');

INSERT INTO Titulations (id, nombre)
VALUES 
    ('INF', 'Ingnieria informatica'),
    ('TEL', 'Ingenieria de telecomunicaciones'),
    ('DER', 'Derecho'),
    ('MED', 'Medicina');

INSERT INTO Subjects (id, tit_id, nombre, creditos)
VALUES
	('DAD2', 'INF', 'Desarrollo de Aplicaciones Distribuidas 2'),
    ('MS', 'INF', 'Modelado de Software'),
    ('PW', 'INF', 'Programacion Web'),
    ('CSI', 'INF', 'Seguridad de la informacion');

    
    
    
    
    