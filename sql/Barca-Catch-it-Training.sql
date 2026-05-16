CREATE DATABASE IF NOT EXISTS barca_catch_it;
USE barca_catch_it;

DROP TABLE IF EXISTS ranking;

CREATE TABLE ranking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    puntuacion INT,
    tiempo_segundos INT,
    vidas_finales INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM ranking;