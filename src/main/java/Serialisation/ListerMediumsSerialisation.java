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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;

/**
 *
 * @author ifoissey
 */
public class ListerMediumsSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("[trace] appel de ListeMediumsSerialisation : appliquer");
        List<Medium> listeMediums = (List<Medium>) req.getAttribute("mediums");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        JsonArray jsonListeMediums = new JsonArray();
        for (Medium medium : listeMediums) {
            JsonObject jsonMedium = new JsonObject();
            jsonMedium.addProperty("id", medium.getId());
            jsonMedium.addProperty("denomination", medium.getDenomination());
            jsonMedium.addProperty("genre", medium.getGenre());
            jsonMedium.addProperty("presentation", medium.getPresentation());
            jsonListeMediums.add(jsonMedium);
        }
        container.add("mediums", jsonListeMediums);
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
        System.out.println("[TRACE]Response = " + res);
    }
}
