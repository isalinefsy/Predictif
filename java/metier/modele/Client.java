package metier.modele;




import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qmariat
 */

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    
    @OneToMany(mappedBy="client", fetch = FetchType.EAGER)
    private List<Consultation> historiqueConsultation = new ArrayList<>();
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    private String motDePasse;
    private String adressePostale;
    private Double latitude;
    private Double longitude;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    private String numeroTelephone;
    
    @Embedded
    private ProfilAstral profilAstral;
    
    
    
       
    //Constructeur

    public Client() {
    }
    
    public Client(String nom, String prenom, String mail, String adressePostale, String motDePasse, Date dateNaissance, String numeroTelephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adressePostale = adressePostale;
        this.motDePasse = motDePasse;
        this.dateNaissance =  dateNaissance;
        this.numeroTelephone = numeroTelephone;
    }
    //Getters/Setters

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public ProfilAstral getProfilAstral() {
        return profilAstral;
    }

    public List<Consultation> getHistoriqueConsultation() {
        return historiqueConsultation;
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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
    }

    public void setHistoriqueConsultation(List<Consultation> historiqueConsultation) {
        this.historiqueConsultation = historiqueConsultation;
    }
    
    

    //Redefinition de la méthode toString()
    @Override
    public String toString() {
        return "Client: " + "id=" + id + ";nom=" + nom + ";prenom=" + prenom + ";mail=" + mail + ";adressePostale=" + adressePostale + ";latitude=" + latitude + ";longitude=" + longitude + '}';
    }
    
}
