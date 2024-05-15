package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeconnexionSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[trace] appel de DeconnexionSerialisation : appliquer");
        JsonObject container = new JsonObject();
        
        // Vérifier si la session a été invalidée avec succès
        boolean deconnexion = (boolean) request.getAttribute("deconnexion");
        if (deconnexion) {
            container.addProperty("success", true);
            container.addProperty("message", "Déconnexion réussie. Vous allez être redirigé.");
        } else {
            container.addProperty("success", false);
            container.addProperty("message", "Aucune session active. Déconnexion non nécessaire.");
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        out.println(gson.toJson(container));
        out.close();
    }
}
