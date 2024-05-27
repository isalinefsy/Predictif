package Actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.service.Service;

/**
 * Classe d'action pour demander une prédiction.
 */
public class DemanderPredictionAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de executer de DemanderPredictionAction");

       

        Long idClient = null;
        String idClientParam = req.getParameter("idClient");
        String amourParam = req.getParameter("amour");
        String santeParam = req.getParameter("sante");
        String travailParam = req.getParameter("travail");

        // Vérifier et parser les paramètres
        try {
            if (idClientParam == null) {
                throw new IllegalArgumentException("ID client non trouvé en paramètre.");
            }
            if (idClientParam != null && !idClientParam.isEmpty()) {
                idClient = Long.parseLong(idClientParam);
            }
            if (amourParam == null || santeParam == null || travailParam == null) {
                throw new IllegalArgumentException("Paramètres de prédiction manquants.");
            }
            
            int amour = Integer.parseInt(amourParam);
            int sante = Integer.parseInt(santeParam);
            int travail = Integer.parseInt(travailParam);

            Client client = Service.rechercherClientParID(idClient);
            if (client == null) {
                throw new IllegalArgumentException("Client non trouvé pour l'ID : " + idClient);
            }
            List<String> lesPredictions = Service.demanderPrediction(amour, sante, travail, client);
            req.setAttribute("lesPredictions", lesPredictions);
        } catch (NumberFormatException e) {
            System.out.println("[erreur] Format de prédiction invalide : " + e.getMessage());
            req.setAttribute("error", "Format de prédiction invalide.");
        } catch (IllegalArgumentException e) {
            System.out.println("[erreur] " + e.getMessage());
            req.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            System.out.println("[erreur] Une erreur inattendue est survenue : " + e.getMessage());
            req.setAttribute("error", "Une erreur inattendue est survenue. Veuillez réessayer plus tard.");
        }
    }
}
