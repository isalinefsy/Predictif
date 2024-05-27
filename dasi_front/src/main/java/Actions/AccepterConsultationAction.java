/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.service.Service;

/**
 *
 * @author ifoissey
 */
public class AccepterConsultationAction extends Action {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de AccepterConsultationAction");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("idEmploye") == null) {
            System.err.println("Aucune session active ou idEmploye manquant");
            req.setAttribute("unConnected", true);
            return; // Arrêter l'exécution si aucune session valide
        }
        Long idConsultation = null;
        String idConsultationParam = req.getParameter("idConsultation");
         if (idConsultationParam != null && !idConsultationParam.isEmpty()) {
                try {
                    idConsultation = Long.parseLong(idConsultationParam);
                } catch (NumberFormatException e) {
                    System.out.println("[erreur] Format de ID Consultation invalide : " + idConsultationParam);
                }
            }
        Consultation cons = null;
        if(idConsultation != null) {
        cons = Service.rechercherConsultationParID(idConsultation);
        Service.accepterConsultation(cons);
        }
        req.setAttribute("unConnected", false);
        req.setAttribute("consultation",cons);
        

    }
}
