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
public class ObtenirClientsEmployeAction extends Action {
     public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de ObtenirClientsEmployeAction");
        HttpSession session = req.getSession(false);
        if (session == null) {
                System.out.println("[erreur] Pas de session disponible.");
        } else {
            // Utiliser la session

            Long id = (Long) session.getAttribute("idEmploye");

            if (id != null) {
                Employe employe = Service.rechercherEmployeParID(id);
                List<Client> clients = Service.obtenirListeClientsParEmploye(employe);
                req.setAttribute("clients", clients);
            } else {
                System.out.println("[erreur] ID Employe non trouvé dans la session.");
                // Vous pouvez choisir d'envoyer une erreur ou de rediriger l'utilisateur.
            }
        }
    }
}
