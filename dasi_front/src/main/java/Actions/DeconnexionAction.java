package Actions;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

public class DeconnexionAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Enumeration<String> attributeNames = session.getAttributeNames();

            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();

                session.removeAttribute(attributeName);
            }

            session.invalidate();
            req.setAttribute("deconnexion", true);
        } else {
            System.err.println("[erreur] Pas de session en cours.");
            req.setAttribute("deconnexion", false);
        }
    }

}
