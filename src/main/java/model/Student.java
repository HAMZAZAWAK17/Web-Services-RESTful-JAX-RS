package model;

import java.sql.Date;

/**
 * Modèle représentant un étudiant
 */
public class Student {

    private int idStudent;
    private String firstName;
    private String lastName;
    private Date birthDate;

    /**
     * Constructeur par défaut
     */
    public Student() {
        super();
    }

    /**
     * Constructeur avec paramètres (sans ID)
     */
    public Student(String firstName, String lastName, Date birthDate) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Constructeur avec tous les paramètres
     */
    public Student(int idStudent, String firstName, String lastName, Date birthDate) {
        super();
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    // Getters et Setters

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Student [idStudent=" + idStudent +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", birthDate=" + birthDate + "]";
    }
}
