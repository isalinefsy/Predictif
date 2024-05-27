/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Consultation;

/**
 *
 * @author ifoissey
 */
public class ValiderConsultationSerialisation extends Serialisation {
     @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
      System.out.println("[trace] appel de ValiderConsultationSerialisation : appliquer");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        boolean unConnected = (boolean) request.getAttribute("unConnected");
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        if (unConnected) {
            container.addProperty("unConnected", true);
        } else {
            container.addProperty("unConnected", false);
        }
        if (consultation != null) {
            container.addProperty("statut", true);
        }  else {
            container.addProperty("statut", false);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(container));
        out.close();
        System.out.println("[TRACE]Response = " + response);
    
    }
}
