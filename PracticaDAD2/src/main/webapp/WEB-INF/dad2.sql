-- 1. CREAMOS UNA BASE DE DATOS TOTALMENTE NUEVA 
CREATE DATABASE IF NOT EXISTS dad2_24420162G_48845233H;

-- 2. NOS SITUAMOS EN ELLA
USE dad2_24420162G_48845233H;

-- ==========================================
-- 1. USUARIOS DEL SISTEMA 
-- ==========================================
CREATE TABLE IF NOT EXISTS Users (
	username VARCHAR(50) NOT NULL,
	password VARCHAR(100) NOT NULL,
	`type` VARCHAR(20) NOT NULL, 
	PRIMARY KEY (username),
	CONSTRAINT chk_user_type CHECK (`type` IN ('ADMIN', 'STUDENT', 'TEACHER')) 
);

-- ==========================================
-- 2. TITULACIONES 
-- ==========================================
CREATE TABLE IF NOT EXISTS Titulations (
    id VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_titulation_nombre (nombre)
);

-- ==========================================
-- 3. ASIGNATURAS 
-- ==========================================
CREATE TABLE IF NOT EXISTS Subjects (
	id VARCHAR(20) NOT NULL,
	tit_id VARCHAR(20) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	creditos INT NOT NULL,
	prof_username VARCHAR(50) NULL, 
	PRIMARY KEY (id),
	FOREIGN KEY (tit_id) REFERENCES Titulations(id),
	CONSTRAINT fk_subject_teacher FOREIGN KEY (prof_username) REFERENCES Users(username) ON DELETE SET NULL
);

-- ==========================================
-- DATOS DE PRUEBA 
-- ==========================================
INSERT INTO Users (username, password, `type`) VALUES ('admin', 'admin', 'ADMIN');
INSERT INTO Users (username, password, `type`) VALUES ('user1', 'user1', 'STUDENT');
INSERT INTO Users (username, password, `type`) VALUES ('prof_juan', '1234', 'TEACHER');
INSERT INTO Users (username, password, `type`) VALUES ('prof_marta', 'abcd', 'TEACHER');

INSERT INTO Titulations (id, nombre) VALUES 
    ('INF', 'Ingenieria informatica'),
    ('TEL', 'Ingenieria de telecomunicaciones'),
    ('DER', 'Derecho'),
    ('MED', 'Medicina');

INSERT INTO Subjects (id, tit_id, nombre, creditos, prof_username) VALUES
	('DAD2', 'INF', 'Desarrollo de Aplicaciones Distribuidas 2', 4, NULL),
    ('MS', 'INF', 'Modelado de Software', 6, NULL),
    ('PW', 'INF', 'Programacion Web', 4, NULL),
    ('CSI', 'INF', 'Seguridad de la informacion', 4, NULL);