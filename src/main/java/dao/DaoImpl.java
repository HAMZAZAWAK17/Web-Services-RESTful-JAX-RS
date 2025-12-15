package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Student;

/**
 * Implémentation du DAO pour la gestion des étudiants
 */
public class DaoImpl implements IDao {

    // Configuration de la base de données
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/DB_SDDI_ESTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "";

    /**
     * Obtenir une connexion à la base de données
     */
    private Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void addStudent(Student s) {
        String sql = "INSERT INTO STUDENTS(FIRST_NAME_STUDENT, LAST_NAME_STUDENT, DATE_BIRTH_STUDENT) VALUES(?,?,?)";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setDate(3, s.getBirthDate());
            ps.executeUpdate();

            System.out.println("✅ Étudiant ajouté avec succès: " + s.getFirstName());

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'ajout de l'étudiant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM STUDENTS WHERE ID_STUDENT = ?";
        Student student = null;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setIdStudent(rs.getInt("ID_STUDENT"));
                student.setFirstName(rs.getString("FIRST_NAME_STUDENT"));
                student.setLastName(rs.getString("LAST_NAME_STUDENT"));
                student.setBirthDate(rs.getDate("DATE_BIRTH_STUDENT"));
            }

            rs.close();

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération de l'étudiant: " + e.getMessage());
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public List<Student> getAllStudent() {
        String sql = "SELECT * FROM STUDENTS ORDER BY ID_STUDENT";
        List<Student> students = new ArrayList<>();

        System.out.println("🔍 [DaoImpl] Début de getAllStudent()");
        System.out.println("📝 [DaoImpl] SQL: " + sql);

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("✅ [DaoImpl] Connexion établie et requête exécutée");

            int count = 0;
            while (rs.next()) {
                count++;
                Student student = new Student();
                student.setIdStudent(rs.getInt("ID_STUDENT"));
                student.setFirstName(rs.getString("FIRST_NAME_STUDENT"));
                student.setLastName(rs.getString("LAST_NAME_STUDENT"));
                student.setBirthDate(rs.getDate("DATE_BIRTH_STUDENT"));
                students.add(student);

                if (count == 1) {
                    System.out.println("👤 [DaoImpl] Premier étudiant: " + student);
                }
            }

            System.out.println("✅ [DaoImpl] " + students.size() + " étudiant(s) récupéré(s)");

        } catch (Exception e) {
            System.err.println("❌ [DaoImpl] ERREUR lors de la récupération des étudiants:");
            System.err.println("   Message: " + e.getMessage());
            System.err.println("   Type: " + e.getClass().getName());
            e.printStackTrace();
        }

        System.out.println("🏁 [DaoImpl] Fin de getAllStudent() - Retour de " + students.size() + " étudiants");
        return students;
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM STUDENTS WHERE ID_STUDENT = ?";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Étudiant supprimé avec succès (ID: " + id + ")");
            } else {
                System.out.println("⚠️ Aucun étudiant trouvé avec l'ID: " + id);
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la suppression de l'étudiant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(int id, Student s) {
        String sql = "UPDATE STUDENTS SET FIRST_NAME_STUDENT = ?, LAST_NAME_STUDENT = ?, DATE_BIRTH_STUDENT = ? WHERE ID_STUDENT = ?";

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setDate(3, s.getBirthDate());
            ps.setInt(4, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Étudiant mis à jour avec succès (ID: " + id + ")");
            } else {
                System.out.println("⚠️ Aucun étudiant trouvé avec l'ID: " + id);
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour de l'étudiant: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
