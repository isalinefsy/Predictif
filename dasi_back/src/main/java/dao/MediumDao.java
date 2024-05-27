/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Medium;

/**
 *
 * @author qmariat
 */

/**
 * Cette classe permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur l'entité Medium dans la base de données.
 */
public class MediumDao {
    
    /*
     * Cette méthode crée un nouveau medium dans la base de données.
     * @param medium Le medium à créer.
     */
    public void create(Medium medium){
        JpaUtil.obtenirContextePersistance().persist(medium);
    }
    
    /*
     * Cette méthode supprime un medium de la base de données.
     * @param medium Le medium à supprimer.
     */
    public void delete(Medium medium){
        JpaUtil.obtenirContextePersistance().remove(medium);
    }
    
    /*
     * Cette méthode met à jour les informations d'un medium dans la base de données.
     * @param medium Le medium à mettre à jour.
     */
    public void update(Medium medium){
        JpaUtil.obtenirContextePersistance().merge(medium);
    }
    
     /*
     * Cette méthode recherche un medium par son identifiant dans la base de données.
     * @param id L'identifiant du medium à rechercher.
     * @return Le medium correspondant à l'identifiant donné, ou null si aucun medium n'est trouvé.
     */
    public Medium findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
    
    
    /*
     * Cette méthode récupère tous les mediums de la base de données, ordonnés par leur dénomination.
     * @return Une liste de tous les mediums, ou null en cas d'erreur.
     */
    public List<Medium> findAll() {
        String s = "select m from Medium m order by m.denomination asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Medium.class);
        List<Medium> mediums;
        
        try {
            mediums = (List<Medium>)query.getResultList();
        }
        catch (Exception d){
            d.printStackTrace();
            mediums = null;
        }
        
        return mediums;
    }
    
    /*
     * Cette méthode récupère tous les mediums de la base de données, ordonnés par le nombre de consultations.
     * @return Une liste de tous les mediums, ou null en cas d'erreur.
     */
    public List<Medium> findAllByCons() {
        String s = "select m from Medium m order by m.nombreDeConsultation desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Medium.class);
        List<Medium> mediums;
        
        try {
            mediums = (List<Medium>)query.getResultList();
        }
        catch (Exception d){
            d.printStackTrace();
            mediums = null;
        }
        
        return mediums;
    }
}
