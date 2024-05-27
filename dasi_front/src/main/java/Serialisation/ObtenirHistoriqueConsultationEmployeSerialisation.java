/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Consultation;

/**
 *
 * @author ifoissey
 */
public class ObtenirHistoriqueConsultationEmployeSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("[trace] appel de ObtenirHistoriqueConsultationEmployeSerialisation : appliquer");
        List<Consultation> historiqueConsultation = (List<Consultation>) req.getAttribute("consultations");

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        JsonArray jsonListConsulations = new JsonArray();
        for (Consultation consultation : historiqueConsultation) {
            JsonObject jsonConsultation = new JsonObject();
            jsonConsultation.addProperty("medium", consultation.getMedium().getDenomination());
            jsonConsultation.addProperty("idClient", consultation.getClient().getId());
            jsonConsultation.addProperty("nomClient", consultation.getClient().getNom());
            jsonConsultation.addProperty("prenomClient", consultation.getClient().getPrenom());
            jsonConsultation.addProperty("idConsultation", consultation.getId());           
            jsonConsultation.addProperty("etat", consultation.getEtat());
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'à' HH:mm:ss");
        Date dateConsultation = consultation.getDateDemande();
        String dateDemandeAsString = dateFormat.format(dateConsultation);
        jsonConsultation.addProperty("dateDemande", dateDemandeAsString);
            jsonListConsulations.add(jsonConsultation);
        }
        container.add("consultations", jsonListConsulations);

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
        System.out.println("[TRACE]Response = " + res);
    }

}
