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
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.ProfilAstral;

/**
 *
 * @author ifoissey
 */
public class ObtenirProfilEmployeSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("[trace] appel de ObtenirProfilEmployeSerialisation : appliquer");
        Employe employe = (Employe) req.getAttribute("employe");

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject jsonInfosEmploye = new JsonObject();
        jsonInfosEmploye.addProperty("id", employe.getId());
        jsonInfosEmploye.addProperty("nom", employe.getNom());
        jsonInfosEmploye.addProperty("prenom", employe.getPrenom());
        jsonInfosEmploye.addProperty("tel", employe.getNumeroTelephone());
        jsonInfosEmploye.addProperty("nbConsultation", employe.getNombreConsultation());
        jsonInfosEmploye.addProperty("mail", employe.getMail());

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(jsonInfosEmploye));
        out.close();
        System.out.println("[TRACE]Response = " + res);
    }
}
