/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;
import javax.persistence.Entity;

/**
 *
 * @author qmariat
 */

@Entity
public class Spirite extends Medium {
     
    private String support;

    public Spirite(String denomination, String genre, String presentation,String support) {
        super(denomination, genre, presentation);
        this.support = support;
    }

    public Spirite() {
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
    
    
}
