package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Test de connexion à la base de données
 */
public class TestConnection {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TEST DE CONNEXION À LA BASE");
        System.out.println("========================================\n");

        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String URL = "jdbc:mysql://localhost:3306/DB_SDDI_ESTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String USER = "root";
        String PASSWORD = "";

        try {
            // 1. Charger le driver
            System.out.println("1️⃣ Chargement du driver MySQL...");
            Class.forName(DRIVER);
            System.out.println("   ✅ Driver chargé avec succès\n");

            // 2. Établir la connexion
            System.out.println("2️⃣ Connexion à la base de données...");
            System.out.println("   URL: " + URL);
            System.out.println("   User: " + USER);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("   ✅ Connexion établie avec succès\n");

            // 3. Vérifier la table STUDENTS
            System.out.println("3️⃣ Vérification de la table STUDENTS...");
            Statement stmt = connection.createStatement();

            // Compter les étudiants
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) as total FROM STUDENTS");
            if (rsCount.next()) {
                int total = rsCount.getInt("total");
                System.out.println("   📊 Nombre total d'étudiants: " + total);
            }
            rsCount.close();

            // Afficher les colonnes
            System.out.println("\n4️⃣ Structure de la table:");
            ResultSet rsColumns = connection.getMetaData().getColumns(null, null, "STUDENTS", null);
            while (rsColumns.next()) {
                String columnName = rsColumns.getString("COLUMN_NAME");
                String columnType = rsColumns.getString("TYPE_NAME");
                System.out.println("   - " + columnName + " (" + columnType + ")");
            }
            rsColumns.close();

            // Afficher quelques étudiants
            System.out.println("\n5️⃣ Premiers étudiants:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS LIMIT 3");
            while (rs.next()) {
                System.out.println("   ID: " + rs.getInt("ID_STUDENT") +
                        " | Prénom: " + rs.getString("FIRST_NAME_STUDENT") +
                        " | Nom: " + rs.getString("LAST_NAME_STUDENT") +
                        " | Date: " + rs.getDate("DATE_BIRTH_STUDENT"));
            }
            rs.close();

            // 6. Fermer la connexion
            stmt.close();
            connection.close();
            System.out.println("\n✅ Test terminé avec succès!");

        } catch (Exception e) {
            System.err.println("\n❌ ERREUR:");
            System.err.println("   Message: " + e.getMessage());
            System.err.println("   Type: " + e.getClass().getName());
            e.printStackTrace();
        }

        System.out.println("\n========================================");
    }
}
