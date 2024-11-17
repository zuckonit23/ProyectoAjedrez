-- Creación de las tablas

CREATE TABLE usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    tipoUsuario ENUM('Profesor', 'Alumno') NOT NULL
);

CREATE TABLE profesores (
    idProfesor INT PRIMARY KEY,
    nombreCompleto VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(100) NOT NULL,
    experiencia INT NOT NULL,
    FOREIGN KEY (idProfesor) REFERENCES usuarios(idUsuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE alumnos (
    idAlumno INT PRIMARY KEY,
    nombreCompleto VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    eloFIDE INT,
    eloVirtual INT,
    fechaIngreso DATE NOT NULL,
    aprobado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (idAlumno) REFERENCES usuarios(idUsuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE torneos (
    idTorneo INT AUTO_INCREMENT PRIMARY KEY,
    idAlumno INT,
    nombre VARCHAR(100) NOT NULL,
    lugar VARCHAR(100) NOT NULL,
    fechaInicio DATE,
    fechaFin DATE,
    ritmoJuego ENUM('rapid', 'blitz', 'classic'),
    victorias INT,
    derrotas INT,
    empates INT,
    FOREIGN KEY (idAlumno) REFERENCES alumnos(idAlumno)
);

-- Datos de prueba
INSERT INTO usuarios (username, password, tipoUsuario) VALUES ('profesor1', 'pass123', 'Profesor');
INSERT INTO profesores (idProfesor, nombreCompleto, correoElectronico, experiencia) VALUES (1, 'Juan Pérez', 'juan.perez@example.com', 10);

INSERT INTO usuarios (username, password, tipoUsuario) VALUES ('alumno1', 'alumno123', 'Alumno');
INSERT INTO alumnos (idAlumno, nombreCompleto, edad, eloFIDE, eloVirtual, fechaIngreso, aprobado) VALUES (2, 'Carlos Sánchez', 20, 1500, 1600, '2023-09-01', TRUE);
