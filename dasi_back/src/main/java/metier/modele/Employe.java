
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
public class Employe {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private Boolean statut;
    private String genre;
    private String motDePasse;
    private String mail;
    private int nombreConsultation;

    public Employe() {
    }
    
    
    public Employe(String nom, String prenom, String numeroTelephone, String genre, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.genre = genre;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public Boolean getStatut() {
        return statut;
    }

    public String getGenre() {
        return genre;
    }

    public int getNombreConsultation() {
        return nombreConsultation;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getMail() {
        return mail;
    }
    
    

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setNombreConsultation(int nombreConsultation) {
        this.nombreConsultation = nombreConsultation;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
    public void incrementerConsultation() {
        this.nombreConsultation = this.getNombreConsultation()+1;
    }
    
    
    
}
