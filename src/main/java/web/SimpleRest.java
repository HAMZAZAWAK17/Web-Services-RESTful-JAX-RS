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
                "    <title>Hi Page</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            display: flex;" +
                "            justify-content: center;" +
                "            align-items: center;" +
                "            height: 100vh;" +
                "            margin: 0;" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);" +
                "        }" +
                "        .container {" +
                "            text-align: center;" +
                "            background: white;" +
                "            padding: 50px;" +
                "            border-radius: 20px;" +
                "            box-shadow: 0 10px 40px rgba(0,0,0,0.2);" +
                "        }" +
                "        h1 {" +
                "            color: #667eea;" +
                "            font-size: 72px;" +
                "            margin: 0;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1>👋 Hiiii!</h1>" +
                "    </div>" +
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
                "    <title>Bonjour Page</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            display: flex;" +
                "            justify-content: center;" +
                "            align-items: center;" +
                "            height: 100vh;" +
                "            margin: 0;" +
                "            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);" +
                "        }" +
                "        .container {" +
                "            text-align: center;" +
                "            background: white;" +
                "            padding: 50px;" +
                "            border-radius: 20px;" +
                "            box-shadow: 0 10px 40px rgba(0,0,0,0.2);" +
                "        }" +
                "        h1 {" +
                "            color: #f5576c;" +
                "            font-size: 72px;" +
                "            margin: 0;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1>🇫🇷 Bonjour!</h1>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
