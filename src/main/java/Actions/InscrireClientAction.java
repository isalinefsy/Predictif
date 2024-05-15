/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Service;

/**
 *
 * @author ifoissey
 */
public class InscrireClientAction extends Action {
     @Override
     public void executer(HttpServletRequest req){
        System.out.println("[trace] appel de exectuer de InscrireClientAction");
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String mail = req.getParameter("mail");
        String mdp = req.getParameter("password");
        String laDate = req.getParameter("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = null;
         try {
             dateNaissance = dateFormat.parse(laDate);
         } catch (ParseException ex) {
             Logger.getLogger(InscrireClientAction.class.getName()).log(Level.SEVERE, null, ex);
         }
        String adresse = req.getParameter("adresse");
        String tel = req.getParameter("tel");
        
     
        Client client = new Client(nom, prenom, mail, adresse, mdp, dateNaissance, tel);
          
        boolean inscrit = Service.inscrireClient(client);
      
        if (inscrit==true){
           req.setAttribute("client",client);
        }
    }
}
