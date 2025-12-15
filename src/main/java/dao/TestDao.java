package dao;

import java.sql.Date;
import java.time.LocalDate;
import model.Student;

/**
 * Classe de test pour le DAO
 */
public class TestDao {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TEST DU DAO - Gestion des Étudiants");
        System.out.println("========================================\n");

        DaoImpl dao = new DaoImpl();

        // Test 1: Récupérer tous les étudiants
        System.out.println("📋 Test 1: Récupération de tous les étudiants");
        System.out.println("----------------------------------------------");
        dao.getAllStudent().forEach(student -> {
            System.out.println(student);
        });
        System.out.println();

        // Test 2: Ajouter un nouvel étudiant
        System.out.println("➕ Test 2: Ajout d'un nouvel étudiant");
        System.out.println("----------------------------------------------");
        Student newStudent = new Student("TSOKA", "TSOKA", Date.valueOf(LocalDate.of(1999, 8, 26)));
        dao.addStudent(newStudent);
        System.out.println();

        // Test 3: Récupérer un étudiant par ID
        System.out.println("🔍 Test 3: Récupération d'un étudiant par ID (ID=1)");
        System.out.println("----------------------------------------------");
        Student student = dao.getStudentById(1);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Aucun étudiant trouvé avec l'ID 1");
        }
        System.out.println();

        // Test 4: Mettre à jour un étudiant
        System.out.println("✏️ Test 4: Mise à jour d'un étudiant (ID=1)");
        System.out.println("----------------------------------------------");
        if (student != null) {
            student.setFirstNameStudent("UPDATED_FIRST_NAME");
            student.setLastNameStudent("UPDATED_LAST_NAME");
            dao.updateStudent(1, student);
        }
        System.out.println();

        // Test 5: Afficher tous les étudiants après mise à jour
        System.out.println("📋 Test 5: Liste après mise à jour");
        System.out.println("----------------------------------------------");
        dao.getAllStudent().forEach(s -> {
            System.out.println(s);
        });
        System.out.println();

        // Test 6: Supprimer un étudiant (décommentez si vous voulez tester)
        /*
         * System.out.println("🗑️ Test 6: Suppression d'un étudiant (ID=1)");
         * System.out.println("----------------------------------------------");
         * dao.deleteStudent(1);
         * System.out.println();
         * 
         * // Test 7: Afficher tous les étudiants après suppression
         * System.out.println("📋 Test 7: Liste après suppression");
         * System.out.println("----------------------------------------------");
         * dao.getAllStudent().forEach(s -> {
         * System.out.println(s);
         * });
         */

        System.out.println("\n========================================");
        System.out.println("   FIN DES TESTS");
        System.out.println("========================================");
    }
}
