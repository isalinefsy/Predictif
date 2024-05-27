/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Medium medium;
    
    @ManyToOne
    private Employe employe;
    
    private String etat;
    private String commentaire;
    
    @Temporal(TemporalType.DATE)
    private Date dateDemande;

    public Consultation() {
    }

    public Consultation(Client client, Medium medium, Employe employe, Date dateDemande) {
        this.client = client;
        this.medium = medium;
        this.employe = employe;
        this.dateDemande = dateDemande;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Medium getMedium() {
        return medium;
    }

    public Employe getEmploye() {
        return employe;
    }

    public String getEtat() {
        return etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }
    
    
    //Redefinition de la méthode toString()
    @Override
    public String toString() {
        return "Consultation: " + "id=" + id + ";client=" + client + ";medium=" + medium + ";employe=" + employe + ";date de la demande=" + dateDemande + '}';
    }
    
}
