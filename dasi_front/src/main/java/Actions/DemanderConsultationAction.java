/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;
import static org.joda.time.format.ISODateTimeFormat.date;

/**
 *
 * @author ifoissey
 */
public class DemanderConsultationAction extends Action {
     public void executer(HttpServletRequest req){
        // Récupérer la session sans en créer une nouvelle
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("idClient") == null) {
            System.err.println("Aucune session active ou idClient manquant");
            req.setAttribute("unConnected", true);
            return; // Arrêter l'exécution si aucune session valide
        }

        // Obtenir l'ID du client à partir de la session
        Long idClient = (Long) session.getAttribute("idClient");

        // Récupérer l'ID du médium à partir de la requête
        String idMediumStr = req.getParameter("idMedium");
        if (idMediumStr == null) {
            System.err.println("ID Medium manquant");
            return; // Arrêter l'exécution si l'ID du médium n'est pas fourni
        }

        try {
            Long idMedium = Long.parseLong(idMediumStr);
            Client client = Service.rechercherClientParID(idClient);
            Medium medium = Service.rechercherMediumParID(idMedium);
            if (client == null || medium == null) {
                System.err.println("Client ou Medium introuvable");
                return; // Arrêter l'exécution si le client ou le médium n'est pas trouvé
            }

            Consultation consultation = Service.demanderConsultation(medium, client);
            req.setAttribute("consultation", consultation);
            req.setAttribute("unConnected", false);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format pour l'ID du medium: " + idMediumStr);
            e.printStackTrace();
        }
       
    }
}
