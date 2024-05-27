
package metier.modele;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author qmariat
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Medium {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String denomination;
    private String genre;
    private String presentation;
    private int nombreDeConsultation;

    public Medium() {
    }   

    public Medium(String denomination, String genre, String presentation) {
        this.denomination = denomination;
        this.genre = genre;
        this.presentation = presentation;
    }

    public Long getId() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getGenre() {
        return genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public int getNombreDeConsultation() {
        return nombreDeConsultation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void setNombreDeConsultation(int nombreDeConsultation) {
        this.nombreDeConsultation = nombreDeConsultation;
    }
    
    public void incrementerConsultation() {
        this.nombreDeConsultation = this.getNombreDeConsultation()+1;
    }
    
    //Redefinition de la méthode toString()
    @Override
    public String toString() {
        return "Medium: " + "id=" + id + ";denomination=" + denomination + " genre=" + genre +" nombre de consultation=" + nombreDeConsultation + '\n';
    }
    
    
}
