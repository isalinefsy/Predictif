package Actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
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
public class AuthentifierUtilisateurAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de AuthentifierUtilisateurAction");
        HttpSession session = req.getSession(true);
       
        String mail = req.getParameter("login");
        String mdp = req.getParameter("password");
        boolean estClient = Service.estClient(mail);
        Client client = null;
        Employe emp=null;
        if (!estClient){
            emp = Service.authentifierEmploye(mail, mdp);
            req.setAttribute("employe",emp);
            session.setAttribute("idEmploye", emp.getId());
            session.removeAttribute("idClient");
        }else{
            client = Service.authentifierClient(mail, mdp); 
        req.setAttribute("client",client);
        session.setAttribute("idClient", client.getId());
        session.removeAttribute("idEmploye");
        }
    }
}
