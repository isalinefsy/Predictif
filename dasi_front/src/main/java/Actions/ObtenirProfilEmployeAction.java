package Actions;

import Actions.Action;
import java.io.PrintWriter;
import static java.lang.Long.parseLong;
import java.util.Enumeration;
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
public class ObtenirProfilEmployeAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de ObtenirProfilEmployeAction");
        HttpSession session = req.getSession(false);
        if (session == null) {
                System.out.println("[erreur] Pas de session disponible.");
        } else {
            // Utiliser la session
            System.out.println("[trace] Attributs de la session:");
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                System.out.println(" - " + attributeName + ": " + attributeValue);
            }
            Long id = (Long) session.getAttribute("idEmploye");

            if (id != null) {
                Employe employe = Service.rechercherEmployeParID(id);
                req.setAttribute("employe", employe);
            } else {
                System.out.println("[erreur] ID employe non trouvé dans la session.");
                // Vous pouvez choisir d'envoyer une erreur ou de rediriger l'utilisateur.
            }
        }
    }
}
