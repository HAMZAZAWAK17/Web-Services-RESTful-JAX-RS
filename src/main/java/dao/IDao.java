package dao;

import java.util.List;
import model.Student;

/**
 * Interface DAO pour la gestion des étudiants
 */
public interface IDao {

    /**
     * Ajouter un étudiant
     * 
     * @param s L'étudiant à ajouter
     */
    public void addStudent(Student s);

    /**
     * Récupérer un étudiant par son ID
     * 
     * @param id L'ID de l'étudiant
     * @return L'étudiant trouvé ou null
     */
    public Student getStudentById(int id);

    /**
     * Récupérer tous les étudiants
     * 
     * @return Liste de tous les étudiants
     */
    public List<Student> getAllStudent();

    /**
     * Supprimer un étudiant
     * 
     * @param id L'ID de l'étudiant à supprimer
     */
    public void deleteStudent(int id);

    /**
     * Mettre à jour un étudiant
     * 
     * @param id L'ID de l'étudiant à mettre à jour
     * @param s  Les nouvelles données de l'étudiant
     */
    public void updateStudent(int id, Student s);
}
