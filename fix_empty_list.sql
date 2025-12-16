-- ========================================
-- SCRIPT DE VERIFICATION ET CORRECTION
-- Pour résoudre le problème de liste vide
-- ========================================

-- 1. Vérifier si la base existe
SHOW DATABASES LIKE 'DB_SDDI_ESTEM';

-- 2. Utiliser la base
USE DB_SDDI_ESTEM;

-- 3. Vérifier si la table existe
SHOW TABLES LIKE 'STUDENTS';

-- 4. Compter les étudiants actuels
SELECT COUNT(*) as total_etudiants FROM STUDENTS;

-- 5. Afficher tous les étudiants
SELECT * FROM STUDENTS ORDER BY ID_STUDENT;

-- ========================================
-- SI LA TABLE EST VIDE, EXECUTER CECI:
-- ========================================

-- Supprimer les données existantes (si nécessaire)
-- DELETE FROM STUDENTS;

-- Réinitialiser l'auto-increment
-- ALTER TABLE STUDENTS AUTO_INCREMENT = 1;

-- Insérer les données de test
INSERT INTO STUDENTS (FIRST_NAME_STUDENT, LAST_NAME_STUDENT, DATE_BIRTH_STUDENT) VALUES
('Hamza', 'BENALI', '2000-05-15'),
('Fatima', 'ZAHRA', '1999-08-26'),
('Mohammed', 'ALAMI', '2001-03-10'),
('Amina', 'IDRISSI', '2000-11-22'),
('Youssef', 'TAZI', '1999-12-05'),
('Sara', 'MANSOURI', '2000-08-18'),
('Omar', 'FASSI', '2001-01-25'),
('Leila', 'BENNANI', '1999-11-30');

-- Vérifier l'insertion
SELECT COUNT(*) as total_apres_insertion FROM STUDENTS;

-- Afficher tous les étudiants
SELECT 
    ID_STUDENT as ID,
    FIRST_NAME_STUDENT as Prénom,
    LAST_NAME_STUDENT as Nom,
    DATE_BIRTH_STUDENT as 'Date de Naissance'
FROM STUDENTS 
ORDER BY ID_STUDENT;

-- ========================================
-- RESULTAT ATTENDU: 8 étudiants
-- ========================================
