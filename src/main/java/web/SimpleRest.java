package web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service REST simple avec deux endpoints
 */
@Path("/")
public class SimpleRest {

    /**
     * Endpoint /hi - Affiche "Hi!!!!"
     * 
     * @return Message Hi
     */
    @GET
    @Path("/hi")
    @Produces(MediaType.TEXT_HTML)
    public String sayHi() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <title>Hi</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            background: white;" +
                "            padding: 20px;" +
                "        }" +
                "        h1 {" +
                "            color: #333;" +
                "        }" +
                "        a {" +
                "            display: inline-block;" +
                "            padding: 10px 20px;" +
                "            background: #007bff;" +
                "            color: white;" +
                "            text-decoration: none;" +
                "            margin-top: 20px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>Hi</h1>" +
                "    <a href='/'>Retour</a>" +
                "</body>" +
                "</html>";
    }

    /**
     * Endpoint /bonjour - Affiche "Bonjour!!!"
     * 
     * @return Message Bonjour
     */
    @GET
    @Path("/bonjour")
    @Produces(MediaType.TEXT_HTML)
    public String sayBonjour() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <title>Bonjour</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            background: white;" +
                "            padding: 20px;" +
                "        }" +
                "        h1 {" +
                "            color: #333;" +
                "        }" +
                "        a {" +
                "            display: inline-block;" +
                "            padding: 10px 20px;" +
                "            background: #007bff;" +
                "            color: white;" +
                "            text-decoration: none;" +
                "            margin-top: 20px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>Bonjour</h1>" +
                "    <a href='/'>Retour</a>" +
                "</body>" +
                "</html>";
    }
}
