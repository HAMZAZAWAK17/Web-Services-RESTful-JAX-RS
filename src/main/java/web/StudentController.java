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
            List<Student> students = dao.getAllStudent();
            return Response.ok(students).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
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

    /**
     * GET /students/html - Page HTML pour gérer les étudiants
     * 
     * @return Page HTML
     */
    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    public String getStudentsPage() {
        return "<!DOCTYPE html>" +
                "<html lang='fr'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>Gestion des Étudiants</title>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body {" +
                "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);" +
                "            min-height: 100vh;" +
                "            padding: 20px;" +
                "        }" +
                "        .container {" +
                "            max-width: 1200px;" +
                "            margin: 0 auto;" +
                "            background: white;" +
                "            padding: 40px;" +
                "            border-radius: 20px;" +
                "            box-shadow: 0 20px 60px rgba(0,0,0,0.3);" +
                "        }" +
                "        h1 {" +
                "            color: #667eea;" +
                "            margin-bottom: 30px;" +
                "            text-align: center;" +
                "        }" +
                "        .api-info {" +
                "            background: #f5f7fa;" +
                "            padding: 20px;" +
                "            border-radius: 10px;" +
                "            margin-bottom: 30px;" +
                "        }" +
                "        .endpoint {" +
                "            margin: 15px 0;" +
                "            padding: 15px;" +
                "            background: white;" +
                "            border-left: 4px solid #667eea;" +
                "            border-radius: 5px;" +
                "        }" +
                "        .method {" +
                "            display: inline-block;" +
                "            padding: 5px 10px;" +
                "            border-radius: 5px;" +
                "            color: white;" +
                "            font-weight: bold;" +
                "            margin-right: 10px;" +
                "        }" +
                "        .get { background: #28a745; }" +
                "        .post { background: #007bff; }" +
                "        .put { background: #ffc107; }" +
                "        .delete { background: #dc3545; }" +
                "        code {" +
                "            background: #f4f4f4;" +
                "            padding: 2px 6px;" +
                "            border-radius: 3px;" +
                "            font-family: 'Courier New', monospace;" +
                "        }" +
                "        .btn {" +
                "            display: inline-block;" +
                "            padding: 10px 20px;" +
                "            background: #667eea;" +
                "            color: white;" +
                "            text-decoration: none;" +
                "            border-radius: 5px;" +
                "            margin-top: 20px;" +
                "        }" +
                "        .btn:hover { background: #764ba2; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1>📚 API Gestion des Étudiants</h1>" +
                "        <div class='api-info'>" +
                "            <h2>Endpoints Disponibles</h2>" +
                "            <div class='endpoint'>" +
                "                <span class='method get'>GET</span>" +
                "                <code>/students</code>" +
                "                <p>Récupérer tous les étudiants</p>" +
                "            </div>" +
                "            <div class='endpoint'>" +
                "                <span class='method get'>GET</span>" +
                "                <code>/students/{id}</code>" +
                "                <p>Récupérer un étudiant par ID</p>" +
                "            </div>" +
                "            <div class='endpoint'>" +
                "                <span class='method post'>POST</span>" +
                "                <code>/students</code>" +
                "                <p>Ajouter un nouvel étudiant (firstName, lastName, birthDate)</p>" +
                "            </div>" +
                "            <div class='endpoint'>" +
                "                <span class='method put'>PUT</span>" +
                "                <code>/students/{id}</code>" +
                "                <p>Mettre à jour un étudiant</p>" +
                "            </div>" +
                "            <div class='endpoint'>" +
                "                <span class='method delete'>DELETE</span>" +
                "                <code>/students/{id}</code>" +
                "                <p>Supprimer un étudiant</p>" +
                "            </div>" +
                "        </div>" +
                "        <a href='/' class='btn'>← Retour à l'accueil</a>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
