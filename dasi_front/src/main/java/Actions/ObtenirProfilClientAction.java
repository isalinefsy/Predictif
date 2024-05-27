package Actions;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.service.Service;

/**
 * Classe d'action pour obtenir le profil d'un client.
 */
public class ObtenirProfilClientAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de executer de ObtenirProfilClientAction");

        HttpSession session = req.getSession(false);

        if (session == null) {
            System.out.println("[erreur] Pas de session disponible.");
        } else {
            System.out.println("[trace] Attributs de la session:");
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                System.out.println(" - " + attributeName + ": " + attributeValue);
            }
            Long id = null;

            // Vérifier si un idClient est passé en paramètre
            String idClientParam = req.getParameter("idClient");
            if (idClientParam != null && !idClientParam.isEmpty()) {
                try {
                    id = Long.parseLong(idClientParam);
                } catch (NumberFormatException e) {
                    System.out.println("[erreur] Format de ID client invalide : " + idClientParam);
                }
            }

            if (id == null) {
                // Si aucun idClient en paramètre, récupérer l'ID du client depuis la session
                id = (Long) session.getAttribute("idClient");
                if (id == null) {
                    System.out.println("[erreur] ID client non trouvé dans la session ou en paramètre.");
                } else {
                    // Rechercher le client par ID depuis la session
                    Client client = Service.rechercherClientParID(id);
                    if (client != null) {
                        req.setAttribute("client", client);
                    } else {
                        System.out.println("[erreur] Client non trouvé pour l'ID : " + id);
                    }
                }
            } else {
                // Rechercher le client par ID depuis le paramètre
                Client client = Service.rechercherClientParID(id);
                if (client != null) {
                    req.setAttribute("client", client);
                } else {
                    System.out.println("[erreur] Client non trouvé pour l'ID : " + id);
                }
            }
        }
    }
}
