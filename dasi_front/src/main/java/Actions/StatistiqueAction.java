/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author ifoissey
 */
public class StatistiqueAction extends Action {
     @Override
    public void executer(HttpServletRequest req) {
        System.out.println("[trace] appel de exectuer de StatistiquesAction");
        List <Medium> lesMediums = Service.obtenirListeMediumsTriParCons();
        if (!lesMediums.isEmpty()){
            req.setAttribute("mediums",lesMediums);
        }else{      
        }
    }
}
