package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe de sérialisation pour la demande de prédiction.
 */
public class DemanderPredictionSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("[trace] appel de DemanderPredictionSerialisation : appliquer");

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();

        // Vérifier s'il y a une erreur dans la requête
        String error = (String) req.getAttribute("error");
        if (error != null) {
            container.addProperty("error", error);
        } else {
            // Sinon, sérialiser les prédictions
            List<String> listePrediction = (List<String>) req.getAttribute("lesPredictions");
            if (listePrediction != null && listePrediction.size() == 3) {
                JsonArray jsonListePrediction = new JsonArray();

                JsonObject jsonAmour = new JsonObject();
                jsonAmour.addProperty("amour", listePrediction.get(0));
                jsonListePrediction.add(jsonAmour);

                JsonObject jsonSante = new JsonObject();
                jsonSante.addProperty("sante", listePrediction.get(1));
                jsonListePrediction.add(jsonSante);

                JsonObject jsonTravail = new JsonObject();
                jsonTravail.addProperty("travail", listePrediction.get(2));
                jsonListePrediction.add(jsonTravail);


                container.add("predictions", jsonListePrediction);
            } else {
                container.addProperty("error", "Erreur lors de la récupération des prédictions.");
            }
        }

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
        System.out.println("[TRACE] Response = " + res);
    }
}
