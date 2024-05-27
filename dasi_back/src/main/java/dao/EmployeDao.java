/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Employe;

/**
 *
 * @author qmariat
 */


/*
 * Cette classe permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur l'entité Employe dans la base de données.
 */
public class EmployeDao {
    
    /*
     * Cette méthode crée un nouvel employé dans la base de données.
     * @param employe L'employé à créer.
     */
    public void create(Employe employe){
        JpaUtil.obtenirContextePersistance().persist(employe);
    }
    
    /*
     * Cette méthode supprime un employé de la base de données.
     * @param employe L'employé à supprimer.
    */
    public void delete(Employe employe){
        JpaUtil.obtenirContextePersistance().remove(employe);
    }
    
    
    /*
     * Cette méthode met à jour les informations d'un employé dans la base de données.
     * @param employe L'employé à mettre à jour.
     */
    public void update(Employe employe){
        JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    
    /*
     * Cette méthode recherche un employé par son identifiant dans la base de données.
     * @param id L'identifiant de l'employé à rechercher.
     * @return L'employé correspondant à l'identifiant donné, ou null si aucun employé n'est trouvé.
     */
    public Employe findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }
    
    
    /*
     * Cette méthode recherche un employé libre par son genre dans la base de données.
     * Un employé libre est défini comme un employé dont le statut est false.
     * @param genre Le genre de l'employé à rechercher (Homme ou Femme).
     * @return L'employé libre du genre spécifié, ou null s'il n'y en a aucun disponible.
     */
    public Employe findEmpLibre(String genre) {
        String s = "select e from Employe e where e.genre = :genre and e.statut = false order by e.nombreConsultation asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("genre",genre);
        query.setMaxResults(1); // Limiter les résultats à un seul
        
        
        Employe e;
        try {
            e = (Employe)query.getSingleResult();
        }
        catch (Exception d){
            d.printStackTrace();
            System.out.println("Il n'y a plus d'employé libre!");
            e = null;
        }
        
        return e;
    }
    
    
    /*
     * Cette méthode recherche un employé par son adresse e-mail dans la base de données.
     * @param email L'adresse e-mail de l'employé à rechercher.
     * @return L'employé correspondant à l'adresse e-mail donnée, ou null si aucun employé n'est trouvé.
     */
    public Employe findByMail(String email) {
        String s = "select e from Employe e where e.mail = :email";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("email",email);
        
        Employe employe;
        try {
            employe = (Employe)query.getSingleResult();
        }
        catch (Exception d){
            d.printStackTrace();
            employe = null;
        }
        
        System.out.println("EmployeDao: findByMail : "+employe.getPrenom());
        
        return employe;
    }
    
    /*
     * Cette méthode vérifie si un e-mail donné correspond à un employé existant dans la base de données.
     * @param email L'adresse e-mail à vérifier.
     * @return true si l'e-mail correspond à un employé, false sinon.
     */
    public boolean estEmploye(String email) {
        String s = "select e from Employe e where e.mail = :email";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("email",email);
        boolean retour = true;
        
        List<Employe> employe = null; //On utilise une liste afin de ne pas déclencher une exception si aucun résultat n'est trouvé
        try {
            employe = query.getResultList();
        }
        catch (Exception d){
            d.printStackTrace();
            retour = false;
        }
        
        if(employe.isEmpty()==true){
            retour=false;
        }

        return retour;
    }
    
}
