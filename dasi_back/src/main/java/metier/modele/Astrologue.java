/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author qmariat
 */

@Entity
public class Astrologue extends Medium {
     
    private String Formation;
    private String Promotion;

    public Astrologue(String denomination, String genre, String presentation,String Formation, String Promotion) {
        super(denomination, genre, presentation);
        this.Formation = Formation;
        this.Promotion = Promotion;
    }

    public Astrologue() {
    }

    public String getFormation() {
        return Formation;
    }

    public String getPromotion() {
        return Promotion;
    }

    public void setFormation(String Formation) {
        this.Formation = Formation;
    }

    public void setPromotion(String Promotion) {
        this.Promotion = Promotion;
    }
    
    
    
    
}
