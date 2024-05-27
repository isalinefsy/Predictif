/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;

/**
 *
 * @author qmariat
 */

/*
 * Cette classe permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur l'entité Consultation dans la base de données.
*/
public class ConsultationDao {

    
    /*
     * Cette méthode crée une nouvelle consultation dans la base de données.
     * @param consultation La consultation à créer.
    */
    public void create(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

     /*
     * Cette méthode supprime une consultation de la base de données.
     * @param consultation La consultation à supprimer.
     */
    public void delete(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().remove(consultation);
    }

    /*
     * Cette méthode met à jour les informations d'une consultation dans la base de données.
     * @param consultation La consultation à mettre à jour.
    */
    public void update(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().merge(consultation);
    }
    
    
    /*
     * Cette méthode recherche une consultation par son identifiant dans la base de données.
     * @param id L'identifiant de la consultation à rechercher.
     * @return La consultation correspondant à l'identifiant donné, ou null si elle n'est pas trouvée.
    */
    public Consultation findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }

    /*
     * Cette méthode vérifie s'il existe une demande de consultation en attente pour un employé donné.
     * @param employe L'employé pour lequel vérifier la demande de consultation.
     * @return La demande de consultation en attente pour l'employé donné, ou null si aucune demande n'est en attente.
     */
    public Consultation verifierDemandeConsultation(Employe employe) {
        String etat = "En attente";
        String s = "select c from Consultation c where c.etat=:etat and c.employe =:employe";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        query.setParameter("employe", employe);
        query.setParameter("etat", etat);

        Consultation consultation = null;
        try {
            consultation = (Consultation) query.getSingleResult();
        } catch (Exception d) {
            d.printStackTrace();
        }

        return consultation;
    }
    
    
    /*
     * Cette méthode vérifie s'il existe une demande de consultation en attente ou en cours pour un client donné.
     * @param client Le client pour lequel vérifier la demande de consultation.
     * @return true si une demande de consultation est en cours ou en attente pour le client, false sinon.
     */
    public boolean verifierDemandeConsultation(Client client) {
        String etat = "En attente";
        String etatbis = "En cours";
        String s = "select c from Consultation c where (c.etat=:etat or c.etat=:etatbis) and c.client =:client";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        query.setParameter("client", client);
        query.setParameter("etat", etat);
        query.setParameter("etatbis", etatbis);
        boolean encours = true;
        List<Consultation> consultation = null;

        consultation = query.getResultList();

        if (consultation.isEmpty()) {
            encours = false;
        }
        return encours;
    }
    
    
    /*
     * Cette méthode trouve toutes les consultations associées à un employé donné dans la base de données.
     * @param employe L'employé pour lequel trouver les consultations.
     * @return Une liste des consultations associées à l'employé donné, triée par date de demande décroissante.
     */
    public List<Consultation> findAll(Employe employe) {
        String s = "select c from Consultation c where c.employe=:employe order by c.dateDemande desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Consultation.class);
        query.setParameter("employe", employe);
        
        return query.getResultList();
    }
    
    
    /*
     * Cette méthode trouve toutes les consultations associées à un médium donné dans la base de données.
     * @param medium Le médium pour lequel trouver les consultations.
     * @return Une liste des consultations associées au médium donné, triée par date de demande décroissante.
     */
    public List<Consultation> findAll(Medium medium) {
        String s = "select c from Consultation c where c.medium=:medium order by c.dateDemande desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Consultation.class);
        query.setParameter("medium", medium);
        
        return query.getResultList();
    }
    

}
