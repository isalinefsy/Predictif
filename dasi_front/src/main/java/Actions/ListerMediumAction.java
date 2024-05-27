package Actions;


import Actions.Action;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
public class ListerMediumAction extends Action {

    @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de ListerMediumAction");
        List <Medium> lesMediums = Service.obtenirListeMediums();
        if (!lesMediums.isEmpty()){
            req.setAttribute("mediums",lesMediums);
        }else{      
        }
    }
}
