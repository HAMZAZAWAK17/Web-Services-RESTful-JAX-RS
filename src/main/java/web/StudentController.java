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
            System.out.println("📝 [StudentController] POST /students appelé");
            System.out.println("   firstName: " + firstName);
            System.out.println("   lastName: " + lastName);
            System.out.println("   birthDate: " + birthDate);

            Student student = new Student(firstName, lastName, Date.valueOf(birthDate));
            dao.addStudent(student);

            System.out.println("✅ [StudentController] Étudiant ajouté avec succès");
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Étudiant ajouté avec succès\"}").build();
        } catch (Exception e) {
            System.err.println("❌ [StudentController] Erreur POST: " + e.getMessage());
            e.printStackTrace();
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
     * GET /students/html - Page HTML basique pour gérer les étudiants
     */
    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    public String getStudentsPage() {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Gestion des Étudiants</title></head><body>" +
                "<h1>Gestion des Étudiants</h1><p><a href='../index.html'>Retour</a></p><div id='message'></div>" +
                "<h2>Liste des Étudiants</h2><table border='1'><thead><tr><th>ID</th><th>Prénom</th><th>Nom</th><th>Date</th><th>Actions</th></tr></thead><tbody id='studentsBody'></tbody></table>"
                +
                "<h2 id='formTitle'>Ajouter un Étudiant</h2><form id='studentForm'><input type='hidden' id='studentId'>"
                +
                "<p><label>Prénom:</label><br><input type='text' id='firstName' required></p>" +
                "<p><label>Nom:</label><br><input type='text' id='lastName' required></p>" +
                "<p><label>Date de Naissance:</label><br><input type='date' id='birthDate' required></p>" +
                "<p><button type='submit'>Enregistrer</button> <button type='button' onclick='resetForm()'>Annuler</button></p></form>"
                +
                "<script>" +
                "function loadStudents(){fetch('../students').then(r=>r.json()).then(students=>{const tbody=document.getElementById('studentsBody');tbody.innerHTML='';students.forEach(s=>{const tr=document.createElement('tr');tr.innerHTML=`<td>${s.idStudent}</td><td>${s.firstName}</td><td>${s.lastName}</td><td>${s.birthDate}</td><td><button onclick='editStudent(${s.idStudent})'>Modifier</button> <button onclick='deleteStudent(${s.idStudent})'>Supprimer</button></td>`;tbody.appendChild(tr);});}).catch(e=>showMessage('Erreur: '+e));}"
                +
                "function showMessage(msg){document.getElementById('message').textContent=msg;setTimeout(()=>document.getElementById('message').textContent='',3000);}"
                +
                "function resetForm(){document.getElementById('studentForm').reset();document.getElementById('studentId').value='';document.getElementById('formTitle').textContent='Ajouter un Étudiant';}"
                +
                "function editStudent(id){fetch('../students/'+id).then(r=>r.json()).then(s=>{document.getElementById('studentId').value=s.idStudent;document.getElementById('firstName').value=s.firstName;document.getElementById('lastName').value=s.lastName;document.getElementById('birthDate').value=s.birthDate;document.getElementById('formTitle').textContent='Modifier un Étudiant';}).catch(e=>showMessage('Erreur: '+e));}"
                +
                "function deleteStudent(id){if(confirm('Supprimer?')){fetch('../students/'+id,{method:'DELETE'}).then(r=>r.json()).then(()=>{showMessage('Supprimé');loadStudents();}).catch(e=>showMessage('Erreur: '+e));}}"
                +
                "document.getElementById('studentForm').addEventListener('submit',function(e){e.preventDefault();const id=document.getElementById('studentId').value;const formData=new URLSearchParams();formData.append('firstName',document.getElementById('firstName').value);formData.append('lastName',document.getElementById('lastName').value);formData.append('birthDate',document.getElementById('birthDate').value);const url=id?'../students/'+id:'../students';const method=id?'PUT':'POST';fetch(url,{method:method,headers:{'Content-Type':'application/x-www-form-urlencoded'},body:formData}).then(r=>r.json()).then(()=>{showMessage(id?'Modifié':'Ajouté');resetForm();loadStudents();}).catch(e=>showMessage('Erreur: '+e));});"
                +
                "loadStudents();" +
                "</script></body></html>";
    }
}
