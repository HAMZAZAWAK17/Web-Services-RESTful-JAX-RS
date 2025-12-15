package model;

import java.sql.Date;

/**
 * Modèle représentant un étudiant
 */
public class Student {

    private int idStudent;
    private String firstNameStudent;
    private String lastNameStudent;
    private Date dateBirthStudent;

    /**
     * Constructeur par défaut
     */
    public Student() {
        super();
    }

    /**
     * Constructeur avec paramètres (sans ID)
     */
    public Student(String firstNameStudent, String lastNameStudent, Date dateBirthStudent) {
        super();
        this.firstNameStudent = firstNameStudent;
        this.lastNameStudent = lastNameStudent;
        this.dateBirthStudent = dateBirthStudent;
    }

    /**
     * Constructeur avec tous les paramètres
     */
    public Student(int idStudent, String firstNameStudent, String lastNameStudent, Date dateBirthStudent) {
        super();
        this.idStudent = idStudent;
        this.firstNameStudent = firstNameStudent;
        this.lastNameStudent = lastNameStudent;
        this.dateBirthStudent = dateBirthStudent;
    }

    // Getters et Setters

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstNameStudent() {
        return firstNameStudent;
    }

    public void setFirstNameStudent(String firstNameStudent) {
        this.firstNameStudent = firstNameStudent;
    }

    public String getLastNameStudent() {
        return lastNameStudent;
    }

    public void setLastNameStudent(String lastNameStudent) {
        this.lastNameStudent = lastNameStudent;
    }

    public Date getDateBirthStudent() {
        return dateBirthStudent;
    }

    public void setDateBirthStudent(Date dateBirthStudent) {
        this.dateBirthStudent = dateBirthStudent;
    }

    @Override
    public String toString() {
        return "Student [idStudent=" + idStudent +
                ", firstNameStudent=" + firstNameStudent +
                ", lastNameStudent=" + lastNameStudent +
                ", dateBirthStudent=" + dateBirthStudent + "]";
    }
}
