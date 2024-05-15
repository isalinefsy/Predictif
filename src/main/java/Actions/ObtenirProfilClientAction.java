package Actions;

import Actions.Action;
import java.io.PrintWriter;
import static java.lang.Long.parseLong;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ifoissey
 */
public class ObtenirProfilClientAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de ObtenirProfilClientAction");
        HttpSession session = req.getSession(false);
        if (session == null) {
                System.out.println("[erreur] Pas de session disponible.");
        } else {
            // Utiliser la session

            Long id = (Long) session.getAttribute("idClient");

            if (id != null) {
                Client client = Service.rechercherClientParID(id);
                req.setAttribute("client", client);
            } else {
                System.out.println("[erreur] ID client non trouvé dans la session.");
                // Vous pouvez choisir d'envoyer une erreur ou de rediriger l'utilisateur.
            }
        }
    }
}
