-- ========================================
-- Script SQL pour le Microservice Student
-- Base de données: DB_SDDI_ESTEM
-- Table: STUDENTS
-- ========================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS DB_SDDI_ESTEM 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Utiliser la base de données
USE DB_SDDI_ESTEM;

-- Supprimer la table si elle existe déjà
DROP TABLE IF EXISTS STUDENTS;

-- Créer la table STUDENTS
CREATE TABLE STUDENTS (
    ID_STUDENT INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME_STUDENT VARCHAR(100) NOT NULL,
    LAST_NAME_STUDENT VARCHAR(100) NOT NULL,
    DATE_BIRTH_STUDENT DATE NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insérer quelques données de test
INSERT INTO STUDENTS (FIRST_NAME_STUDENT, LAST_NAME_STUDENT, DATE_BIRTH_STUDENT) VALUES
('Hamza', 'BENALI', '2000-05-15'),
('Fatima', 'ZAHRA', '1999-08-26'),
('Mohammed', 'ALAMI', '2001-03-10'),
('Amina', 'IDRISSI', '2000-11-22'),
('Youssef', 'TAZI', '1999-12-05'),
('Sara', 'MANSOURI', '2000-08-18'),
('Omar', 'FASSI', '2001-01-25'),
('Leila', 'BENNANI', '1999-11-30');

-- Vérifier les données insérées
SELECT * FROM STUDENTS;

-- ========================================
-- Requêtes utiles
-- ========================================

-- Compter le nombre d'étudiants
-- SELECT COUNT(*) AS total_students FROM STUDENTS;

-- Trouver les étudiants nés après 2000
-- SELECT * FROM STUDENTS WHERE DATE_BIRTH_STUDENT > '2000-01-01';

-- Rechercher un étudiant par nom
-- SELECT * FROM STUDENTS WHERE LAST_NAME_STUDENT LIKE '%BENALI%';

-- Mettre à jour un étudiant
-- UPDATE STUDENTS SET FIRST_NAME_STUDENT = 'Nouveau Nom' WHERE ID_STUDENT = 1;

-- Supprimer un étudiant
-- DELETE FROM STUDENTS WHERE ID_STUDENT = 1;
