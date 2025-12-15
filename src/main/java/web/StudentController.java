package web;

import java.sql.Date;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoImpl;
import dao.IDao;
import model.Student;

/**
 * Contrôleur REST pour la gestion des étudiants
 * Architecture MVC : Ce contrôleur utilise le DAO pour accéder aux données
 */
@Path("/students")
public class StudentController {

    private IDao dao = new DaoImpl();

    /**
     * GET /students - Récupérer tous les étudiants
     * 
     * @return Liste des étudiants en JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        try {
            System.out.println("🔍 [StudentController] Appel de getAllStudents()");
            List<Student> students = dao.getAllStudent();
            System.out.println("📊 [StudentController] Nombre d'étudiants récupérés: " + students.size());

            if (students.isEmpty()) {
                System.out.println("⚠️ [StudentController] ATTENTION: La liste est vide!");
            } else {
                System.out.println("✅ [StudentController] Premier étudiant: " + students.get(0));
            }

            return Response.ok(students).build();
        } catch (Exception e) {
            System.err.println("❌ [StudentController] Erreur: " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    /**
     * GET /students/debug-test - Endpoint de test
     * 
     * @return Un étudiant de test
     */
    @GET
    @Path("/debug-test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testEndpoint() {
        System.out.println("🧪 [StudentController] Test endpoint appelé");
        Student testStudent = new Student(999, "Test", "User", java.sql.Date.valueOf("2000-01-01"));
        System.out.println("📤 [StudentController] Retour de: " + testStudent);
        return Response.ok(testStudent).build();
    }

    /**
     * GET /students/{id} - Récupérer un étudiant par ID
     * 
     * @param id L'ID de l'étudiant
     * @return L'étudiant en JSON
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") int id) {
        try {
            Student student = dao.getStudentById(id);
            if (student != null && student.getIdStudent() > 0) {
                return Response.ok(student).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Étudiant non trouvé\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    /**
     * POST /students - Ajouter un nouvel étudiant
     * 
     * @param firstName Prénom
     * @param lastName  Nom
     * @param birthDate Date de naissance (format: yyyy-MM-dd)
     * @return Message de confirmation
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("birthDate") String birthDate) {

        try {
            Student student = new Student(firstName, lastName, Date.valueOf(birthDate));
            dao.addStudent(student);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Étudiant ajouté avec succès\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    /**
     * PUT /students/{id} - Mettre à jour un étudiant
     * 
     * @param id        L'ID de l'étudiant
     * @param firstName Nouveau prénom
     * @param lastName  Nouveau nom
     * @param birthDate Nouvelle date de naissance
     * @return Message de confirmation
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(
            @PathParam("id") int id,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("birthDate") String birthDate) {

        try {
            Student student = new Student(firstName, lastName, Date.valueOf(birthDate));
            dao.updateStudent(id, student);
            return Response.ok("{\"message\": \"Étudiant mis à jour avec succès\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    /**
     * DELETE /students/{id} - Supprimer un étudiant
     * 
     * @param id L'ID de l'étudiant à supprimer
     * @return Message de confirmation
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id) {
        try {
            dao.deleteStudent(id);
            return Response.ok("{\"message\": \"Étudiant supprimé avec succès\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

}
