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
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            background: #f5f5f5;" +
                "            padding: 20px;" +
                "        }" +
                "        .container {" +
                "            max-width: 900px;" +
                "            margin: 0 auto;" +
                "            background: white;" +
                "            padding: 30px;" +
                "            border: 1px solid #ddd;" +
                "        }" +
                "        h1 {" +
                "            color: #333;" +
                "            font-size: 24px;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        h2 {" +
                "            color: #555;" +
                "            font-size: 18px;" +
                "            margin-top: 30px;" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        table {" +
                "            width: 100%;" +
                "            border-collapse: collapse;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        th, td {" +
                "            border: 1px solid #ddd;" +
                "            padding: 10px;" +
                "            text-align: left;" +
                "        }" +
                "        th {" +
                "            background: #f0f0f0;" +
                "        }" +
                "        .form-group {" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        label {" +
                "            display: block;" +
                "            margin-bottom: 5px;" +
                "            color: #333;" +
                "        }" +
                "        input {" +
                "            width: 100%;" +
                "            padding: 8px;" +
                "            border: 1px solid #ddd;" +
                "            box-sizing: border-box;" +
                "        }" +
                "        .btn {" +
                "            padding: 10px 20px;" +
                "            background: #007bff;" +
                "            color: white;" +
                "            border: none;" +
                "            cursor: pointer;" +
                "            margin-right: 5px;" +
                "        }" +
                "        .btn:hover {" +
                "            background: #0056b3;" +
                "        }" +
                "        .btn-delete {" +
                "            background: #dc3545;" +
                "        }" +
                "        .btn-delete:hover {" +
                "            background: #c82333;" +
                "        }" +
                "        .btn-edit {" +
                "            background: #28a745;" +
                "        }" +
                "        .btn-edit:hover {" +
                "            background: #218838;" +
                "        }" +
                "        .btn-back {" +
                "            background: #6c757d;" +
                "            display: inline-block;" +
                "            text-decoration: none;" +
                "            margin-top: 20px;" +
                "        }" +
                "        .btn-back:hover {" +
                "            background: #5a6268;" +
                "        }" +
                "        .message {" +
                "            padding: 10px;" +
                "            margin-bottom: 15px;" +
                "            border-radius: 3px;" +
                "        }" +
                "        .success {" +
                "            background: #d4edda;" +
                "            color: #155724;" +
                "            border: 1px solid #c3e6cb;" +
                "        }" +
                "        .error {" +
                "            background: #f8d7da;" +
                "            color: #721c24;" +
                "            border: 1px solid #f5c6cb;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1>Gestion des Étudiants</h1>" +
                "        <div id='message'></div>" +
                "" +
                "        <h2>Liste des Étudiants</h2>" +
                "        <table id='studentsTable'>" +
                "            <thead>" +
                "                <tr>" +
                "                    <th>ID</th>" +
                "                    <th>Prénom</th>" +
                "                    <th>Nom</th>" +
                "                    <th>Date de Naissance</th>" +
                "                    <th>Actions</th>" +
                "                </tr>" +
                "            </thead>" +
                "            <tbody id='studentsBody'>" +
                "            </tbody>" +
                "        </table>" +
                "" +
                "        <h2 id='formTitle'>Ajouter un Étudiant</h2>" +
                "        <form id='studentForm'>" +
                "            <input type='hidden' id='studentId' value=''>" +
                "            <div class='form-group'>" +
                "                <label>Prénom:</label>" +
                "                <input type='text' id='firstName' required>" +
                "            </div>" +
                "            <div class='form-group'>" +
                "                <label>Nom:</label>" +
                "                <input type='text' id='lastName' required>" +
                "            </div>" +
                "            <div class='form-group'>" +
                "                <label>Date de Naissance:</label>" +
                "                <input type='date' id='birthDate' required>" +
                "            </div>" +
                "            <button type='submit' class='btn'>Enregistrer</button>" +
                "            <button type='button' class='btn btn-back' onclick='resetForm()'>Annuler</button>" +
                "        </form>" +
                "" +
                "        <a href='/' class='btn btn-back'>← Retour à l'accueil</a>" +
                "    </div>" +
                "" +
                "    <script>" +
                "        function loadStudents() {" +
                "            fetch('/students')" +
                "                .then(response => response.json())" +
                "                .then(students => {" +
                "                    const tbody = document.getElementById('studentsBody');" +
                "                    tbody.innerHTML = '';" +
                "                    students.forEach(student => {" +
                "                        const tr = document.createElement('tr');" +
                "                        tr.innerHTML = `" +
                "                            <td>${student.idStudent}</td>" +
                "                            <td>${student.firstName}</td>" +
                "                            <td>${student.lastName}</td>" +
                "                            <td>${student.birthDate}</td>" +
                "                            <td>" +
                "                                <button class='btn btn-edit' onclick='editStudent(${student.idStudent})'>Modifier</button>"
                +
                "                                <button class='btn btn-delete' onclick='deleteStudent(${student.idStudent})'>Supprimer</button>"
                +
                "                            </td>" +
                "                        `;" +
                "                        tbody.appendChild(tr);" +
                "                    });" +
                "                })" +
                "                .catch(error => showMessage('Erreur lors du chargement: ' + error, 'error'));" +
                "        }" +
                "" +
                "        function showMessage(msg, type) {" +
                "            const messageDiv = document.getElementById('message');" +
                "            messageDiv.className = 'message ' + type;" +
                "            messageDiv.textContent = msg;" +
                "            setTimeout(() => messageDiv.innerHTML = '', 3000);" +
                "        }" +
                "" +
                "        function resetForm() {" +
                "            document.getElementById('studentForm').reset();" +
                "            document.getElementById('studentId').value = '';" +
                "            document.getElementById('formTitle').textContent = 'Ajouter un Étudiant';" +
                "        }" +
                "" +
                "        function editStudent(id) {" +
                "            fetch('/students/' + id)" +
                "                .then(response => response.json())" +
                "                .then(student => {" +
                "                    document.getElementById('studentId').value = student.idStudent;" +
                "                    document.getElementById('firstName').value = student.firstName;" +
                "                    document.getElementById('lastName').value = student.lastName;" +
                "                    document.getElementById('birthDate').value = student.birthDate;" +
                "                    document.getElementById('formTitle').textContent = 'Modifier un Étudiant';" +
                "                    window.scrollTo(0, document.getElementById('studentForm').offsetTop);" +
                "                })" +
                "                .catch(error => showMessage('Erreur: ' + error, 'error'));" +
                "        }" +
                "" +
                "        function deleteStudent(id) {" +
                "            if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')) {" +
                "                fetch('/students/' + id, { method: 'DELETE' })" +
                "                    .then(response => response.json())" +
                "                    .then(data => {" +
                "                        showMessage('Étudiant supprimé avec succès', 'success');" +
                "                        loadStudents();" +
                "                    })" +
                "                    .catch(error => showMessage('Erreur: ' + error, 'error'));" +
                "            }" +
                "        }" +
                "" +
                "        document.getElementById('studentForm').addEventListener('submit', function(e) {" +
                "            e.preventDefault();" +
                "            const id = document.getElementById('studentId').value;" +
                "            const formData = new URLSearchParams();" +
                "            formData.append('firstName', document.getElementById('firstName').value);" +
                "            formData.append('lastName', document.getElementById('lastName').value);" +
                "            formData.append('birthDate', document.getElementById('birthDate').value);" +
                "" +
                "            const url = id ? '/students/' + id : '/students';" +
                "            const method = id ? 'PUT' : 'POST';" +
                "" +
                "            fetch(url, {" +
                "                method: method," +
                "                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }," +
                "                body: formData" +
                "            })" +
                "            .then(response => response.json())" +
                "            .then(data => {" +
                "                showMessage(id ? 'Étudiant modifié avec succès' : 'Étudiant ajouté avec succès', 'success');"
                +
                "                resetForm();" +
                "                loadStudents();" +
                "            })" +
                "            .catch(error => showMessage('Erreur: ' + error, 'error'));" +
                "        });" +
                "" +
                "        loadStudents();" +
                "    </script>" +
                "</body>" +
                "</html>";
    }
}
