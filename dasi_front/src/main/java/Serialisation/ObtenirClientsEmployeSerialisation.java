package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Client;

/**
 *
 * @author ifoissey
 */
public class ObtenirClientsEmployeSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("[trace] appel de ObtenirClientsEmployeSerialisation : appliquer");
        List<Client> clients = (List<Client>) req.getAttribute("clients");

        // Utiliser un ensemble pour s'assurer que les clients sont uniques
        Set<Long> clientIds = new HashSet<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        JsonArray jsonListConsulations = new JsonArray();
        // un set pour vérifier que l'on a pas de doublons lors de l'obtention des clients 
        for (Client client : clients) {
            if (!clientIds.contains(client.getId())) {
                clientIds.add(client.getId());
                JsonObject jsonConsultation = new JsonObject();
                jsonConsultation.addProperty("idClient", client.getId());
                jsonConsultation.addProperty("nomClient", client.getNom());
                jsonConsultation.addProperty("prenomClient", client.getPrenom());
                jsonListConsulations.add(jsonConsultation);
            }
        }

        container.add("consultations", jsonListConsulations);
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
        System.out.println("[TRACE]Response = " + res);
    }
}
