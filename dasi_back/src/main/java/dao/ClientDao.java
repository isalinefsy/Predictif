package dao;

import metier.modele.Client;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Employe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qmariat
 */
/*
 * Cette classe permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur l'entité Client dans la base de données.
 */
public class ClientDao {
    
    /*
     * Cette méthode crée un nouveau client dans la base de données.
     */
    public void create(Client client){
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    /*
    * Cette méthode supprime un client de la base de données.
    */
    public void delete(Client client){
        JpaUtil.obtenirContextePersistance().remove(client);
    }
    
    /*
     * Cette méthode met à jour les informations d'un client dans la base de données.
    */
    public void update(Client client){
        JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    
    /*
     * Cette méthode recherche un client par son identifiant dans la base de données.
     * @param id L'identifiant du client à rechercher.
     * @return Le client correspondant à l'identifiant donné, ou null s'il n'est pas trouvé.
    */
    public Client findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    
    /*
     * Cette méthode recherche un client par son adresse e-mail dans la base de données.
     * @param email L'adresse e-mail du client à rechercher.
     * @return Le client correspondant à l'adresse e-mail donnée, ou null s'il n'est pas trouvé.
    */
    public Client findByMail(String email) {
        String s = "select c from Client c where c.mail = :email";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("email",email);
        
        Client c;
        try {
            c = (Client)query.getSingleResult();
        }
        catch (Exception d){
            d.printStackTrace();
            c = null;
        }
        return c;
    }
    
    /*
     * Cette méthode trouve tous les clients associés à un employé donné dans la base de données.
     * @param employe L'employé pour lequel trouver les clients.
     * @return Une liste des clients associés à l'employé donné.
    */
    public List<Client> trouverClientsParEmploye(Employe employe) {
        String s = "select cons.client from Consultation cons where cons.employe = :employe";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("employe",employe);
        
        List<Client> clients;
        try {
            clients = query.getResultList();
        }
        catch (Exception d){
            d.printStackTrace();
            clients = null;
        }
        
        return clients;
    }
     
    /*
    * Cette méthode vérifie si une adresse e-mail correspond à un client enregistré dans la base de données.
    * @param email L'adresse e-mail à vérifier.
    * @return true si l'adresse e-mail correspond à un client enregistré, false sinon.
    */
    public boolean estClient(String email) {
        String s = "select c from Client c where c.mail = :email";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("email",email);
        boolean retour = true;
        
        List<Client> client = null; //On utilise une liste afin de ne pas déclencher une exception si aucun résultat n'est trouvé
        try {
            client = query.getResultList();
        }
        catch (Exception d){
            d.printStackTrace();
            retour = false;
        }
        
        if(client.isEmpty()==true){
            retour=false;
        }

        return retour;
    }
    
    /*
     * Cette méthode trouve tous les clients enregistrés dans la base de données.
     * @return Une liste de tous les clients enregistrés, triée par ordre alphabétique du nom.
    */
    public List<Client> findAll() {
        String s = "select c from Client c order by c.nom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Client.class);
        return query.getResultList();
    }
    
}