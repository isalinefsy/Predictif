 package metier.service;


import utile.Message;
import utile.GeoNetApi;
import dao.ClientDao;
import dao.JpaUtil;
import metier.modele.Client;
import com.google.maps.model.LatLng;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.MediumDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import metier.modele.Spirite;
import utile.AstroNetApi;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qmariat
 */

/**
 * Cette classe service permet de gérer les fonctionnalités de notre site de voyance.
 */
public class Service
{
    // Premier service pour initialiser nos employés et nos médiums 
    public static Boolean initialisation()
    {
        Boolean inscription = true;
        
         // Création des employés
        Employe emp = new Employe("Dupont","Jean","0718181818","H","jdupont@predict.if","toto");
        Employe empbis = new Employe("Wirane","Hamza","0719191919","H","hwirane@predict.if","toto");
        Employe empbisbis = new Employe("Mariat","Quentin","0720202020","H","qmariat@predict.if","toto");
        Employe empbisbisbis = new Employe("Tourtel","Camille","0721212121","F","ctourtel@predict.if","toto");
        emp.setStatut(false); empbis.setStatut(false); empbisbis.setStatut(false); empbisbisbis.setStatut(false);
        
        // Création des médiums
        Spirite spirit = new Spirite("Gwenaelle","F","Specialiste des grandes conversations au-dela de TOUTES les frontières","Boule de cristal");
        Spirite spiritbis = new Spirite("Professeur Tran","H","Votre avenir est devant vous : regardons-le ensemble !","Marc de café, boule de cristal, oreilles de lapin");
        
        Astrologue astro = new Astrologue("Serena","F","Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé","Ecole Normale Supérieure d'Astrologie (ENS-Astro)","2006");
        
        Cartomancien carto = new Cartomancien("Mme Irma","F","Comprenez votre entourage grace à mes cartes ! Résultats rapides.");
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            MediumDao mediumdao = new MediumDao();
            mediumdao.create(spirit);
            mediumdao.create(spiritbis);
            mediumdao.create(astro);
            mediumdao.create(carto);
            
            EmployeDao employedao = new EmployeDao();
            
            employedao.create(emp);
            employedao.create(empbis);
            employedao.create(empbisbis);
            employedao.create(empbisbisbis);
            
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            inscription = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return inscription;
    }
    
    // Service pour inscrire un client
    public static Boolean inscrireClient(Client client)
    {
        Boolean inscription = true;
        LatLng coord;
        AstroNetApi astroApi = new AstroNetApi();
       
        
        try {
        // Récupération des coordonnées géographiques et des informations pour le profil astral du client
        coord = GeoNetApi.getLatLng(client.getAdressePostale());
        List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDateNaissance());
        String signeZodiaque = profil.get(0);
        String signeChinois = profil.get(1);
        String couleur = profil.get(2);
        String animal = profil.get(3);
        
        // Création du profil astral du client
        ProfilAstral profilastral = new ProfilAstral(signeZodiaque, signeChinois, couleur, animal);
        
        client.setProfilAstral(profilastral);
        
        // Ouverture de la transaction avec la base de données
        JpaUtil.creerContextePersistance();
        JpaUtil.ouvrirTransaction();
        
        // Vérification de la validité des coordonnées géographiques
        if (coord == null)
        {
            throw new Exception ("adresse invalide");
        }
        client.setLatitude(coord.lat);
        client.setLongitude(coord.lng);
        
        new ClientDao().create(client);
        // Validation de la transaction
        JpaUtil.validerTransaction();
        
        } catch (Exception e) {
            inscription = false;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        // Envoi de mail de confirmation ou d'échec d'inscription en fonction de la valeur de la variable d'inscription
        if (inscription == true)
        {
            System.out.println("Inscription réussie pour "+client.getNom()+" "+client.getPrenom()+" <"+client.getMail()+">");
            Message.envoyerMail("contact@predict.if", client.getMail(), "Bienvenue chez PREDICT'IF", "Bonjour " + client.getPrenom()+", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums.");
        }
        else {
            System.out.println("Inscription échouée pour "+client.getNom()+" "+client.getPrenom()+" <"+client.getMail()+">");
            Message.envoyerMail("contact@predict.if", client.getMail(), "Echec de l'inscription chez PREDICT'IF", "Bonjour " +client.getPrenom()+", votre inscription au service PREDICT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.");
        }
        
        return inscription;
    }
    
    
    // Service pour authentifier un client
    public static Client authentifierClient(String mail, String motDePasse)
    {
        JpaUtil.creerContextePersistance();
        ClientDao clientdao = new ClientDao();
        // Recherche du client par son adresse mail
        Client client = clientdao.findByMail(mail);

         // Vérification de l'authentification du client
        if ((client == null)||(!client.getMotDePasse().equals(motDePasse))) {
            client = null;
        }
        JpaUtil.fermerContextePersistance();
        return client;
    }
    
     // Service pour authentifier un employé
    public static Employe authentifierEmploye(String mail, String motDePasse)
    {
        JpaUtil.creerContextePersistance();
        EmployeDao employedao = new EmployeDao();
        // Recherche de l'employé par son adresse mail
        Employe employe = employedao.findByMail(mail);
        
        // Vérification de l'authentification de l'employé
        if ((employe == null)||(!employe.getMotDePasse().equals(motDePasse))) {
            employe = null;
        }
        JpaUtil.fermerContextePersistance();
        return employe;
    }
    
     // Service pour rechercher un client par son ID
    public static Client rechercherClientParID(Long id) {
        JpaUtil.creerContextePersistance();
        ClientDao clientdao = new ClientDao();
        // Recherche du client par son ID
        Client client = clientdao.findById(id);
        System.out.println(client.getHistoriqueConsultation().size());
        JpaUtil.fermerContextePersistance();
        return client;
    }
    
    // Service pour rechercher un employé par son ID
    public static Employe rechercherEmployeParID(Long id) {
        JpaUtil.creerContextePersistance();
        EmployeDao employedao = new EmployeDao();
        Employe employe = employedao.findById(id); // Recherche de l'employé par son ID
        
        JpaUtil.fermerContextePersistance();
        return employe;
    }
    
    // Service pour rechercher une consultation par son ID
    public static Consultation rechercherConsultationParID(Long id) {
        JpaUtil.creerContextePersistance();
        ConsultationDao consultationdao = new ConsultationDao();
        Consultation consultation = consultationdao.findById(id);// Recherche de la consultation par son ID

        JpaUtil.fermerContextePersistance();
        return consultation;
    }
    
    // Service pour rechercher un médium par son ID
    public static Medium rechercherMediumParID(Long id) {
        JpaUtil.creerContextePersistance();
        MediumDao mediumdao = new MediumDao();
        Medium medium = mediumdao.findById(id); // Recherche du médium par son ID
        
        JpaUtil.fermerContextePersistance();
        return medium;
    }
    
    // Service pour demander une consultation
    public static Consultation demanderConsultation(Medium medium, Client client){   
        
        Consultation consultation = null;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            //Verifier que le client n'ait pas déja une demande en attente ou en cours
            ConsultationDao consdao = new ConsultationDao();
            if (consdao.verifierDemandeConsultation(client) == true) {
                throw new Exception("Le client a déjà une demande en attente ou en cours");
            }
                
            
            //Trouver un employe libre
            EmployeDao empdao = new EmployeDao();
            Employe employe = empdao.findEmpLibre(medium.getGenre());
            employe.setStatut(true); //L'employe est maintenant considéré comme "occupé"
            empdao.update(employe);
            
            // Si aucun employé n'est disponible, une exception est levée
            if (employe == null)
            {
                throw new Exception("Aucun employe libre");
            }
            
            // Création de la consultation avec les informations fournies
            Date date = new Date();
            consultation = new Consultation(client,medium,employe,date);
            consultation.setEtat("En attente");
            
            consdao.create(consultation);
            
            client.getHistoriqueConsultation().add(consultation);
            
            ClientDao clidao = new ClientDao();
            consdao.update(consultation);
            clidao.update(client);
            
            JpaUtil.validerTransaction();
            
            // Envoi d'une notification à l'employé concerné
            String message = "Bonjour " + employe.getPrenom() + ". " + "Consultation requise pour " + client.getPrenom() + " " + client.getNom() + ". " +"Médium à incarner : "+medium.getDenomination();
            Message.envoyerNotification(employe.getNumeroTelephone(), message);
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
       
        return consultation;
    }
    
     // Service pour accepter une consultation
    public static void accepterConsultation(Consultation consultation) {
        // Changement de l'état de la consultation à "En cours"
        consultation.setEtat("En cours");
        // Changement du statut de l'employé à "occupé"
        consultation.getEmploye().setStatut(true);
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            ConsultationDao consdao = new ConsultationDao();
            consdao.update(consultation); // Mise à jour de la consultation dans la base de données
            // Envoi d'une notification au client pour confirmer la consultation
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
            SimpleDateFormat formatterbis = new SimpleDateFormat("HH:mm");
            String jourmois = formatter.format(consultation.getDateDemande());
            String heuremin = formatterbis.format(consultation.getDateDemande());
       
            JpaUtil.validerTransaction();
            String message = "Bonjour " + consultation.getClient().getPrenom() +". " +"J'ai bien reçu votre demande de consultation du " + jourmois + " à " + heuremin +". Vous pouvez dès à présent me contacter au " +consultation.getEmploye().getNumeroTelephone()+". A tout de suite ! Médiumiquement vôtre, " +consultation.getMedium().getDenomination()+".";
            Message.envoyerNotification(consultation.getClient().getNumeroTelephone(), message);
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
    // Service pour valider une consultation
    public static void validerConsultation(Consultation consultation, String commentaire) {
        Employe employe;
        employe = consultation.getEmploye();
        Medium medium;
        medium = consultation.getMedium();
        consultation.setEtat("Terminée");// Changement de l'état de la consultation à "Terminée"
        employe.setStatut(false); //L'employe est maintenant libre
        // Saisie d'un commentaire sur le déroulement de la consultation
        consultation.setCommentaire(commentaire);
        // Incrémentation du nombre de consultations pour l'employé et le médium
        employe.incrementerConsultation();
        medium.incrementerConsultation();
        Client client = consultation.getClient();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            ConsultationDao consdao = new ConsultationDao();
            EmployeDao empdao = new EmployeDao();
            ClientDao clidao = new ClientDao();
            empdao.update(employe);
            MediumDao meddao = new MediumDao();
            meddao.update(medium);
            consdao.update(consultation);
            clidao.update(client);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
    }
    
    // Service pour vérifier si un employé a une demande de consultation en attente
    public static Consultation verifierDemandeConsultation(Employe emp) {
        JpaUtil.creerContextePersistance();
        ConsultationDao consdao = new ConsultationDao();
        Consultation consultation = consdao.verifierDemandeConsultation(emp);
        JpaUtil.fermerContextePersistance();
        
        return consultation;
    }
            
    /*
    * Ce service obtient la liste de tous les médiums triée par ordre alphabétique.
    * @return Une liste des médiums disponibles.
    */
    public static List<Medium> obtenirListeMediums() {
        JpaUtil.creerContextePersistance();

        MediumDao mediumdao = new MediumDao();
        List<Medium> mediums = mediumdao.findAll();
        
        if (mediums.isEmpty())
        {
            System.out.println("Il n'y a aucun medium");
        }
        JpaUtil.fermerContextePersistance();
        return mediums;
    }
    
    
    /*
    * Ce service obtient la liste des médiums triés par nombre de consultations.
    * @return Une liste des médiums triés par nombre de consultations.
    */
    public static List<Medium> obtenirListeMediumsTriParCons() {
        JpaUtil.creerContextePersistance();

        MediumDao mediumdao = new MediumDao();
        List<Medium> mediums = mediumdao.findAllByCons();
        
        if (mediums.isEmpty())
        {
            System.out.println("Il n'y a aucun medium");
        }
        JpaUtil.fermerContextePersistance();
        return mediums;
    }
    
    /*
    * Ce service obtient la liste de tous les clients.
    * @return Une liste des clients enregistrés.
    */
    public static List<Client> obtenirListeClients() {
        JpaUtil.creerContextePersistance();

        ClientDao clientdao = new ClientDao();
        List<Client> clients = clientdao.findAll();
        
        if (clients.isEmpty())
        {
            System.out.println("Il n'y a aucun client");
        }
        JpaUtil.fermerContextePersistance();
        return clients;
    }
    
    /*
    * Ce service obtient la liste des clients associés à un employé spécifique.
    * @param employe L'employé pour lequel obtenir la liste des clients.
    * @return Une liste des clients associés à l'employé donné.
    */
    public static List<Client> obtenirListeClientsParEmploye(Employe employe) {
        JpaUtil.creerContextePersistance();

        ClientDao clientdao = new ClientDao();
        List<Client> clients = clientdao.trouverClientsParEmploye(employe);
        
        if (clients.isEmpty())
        {
            System.out.println("L'employé n'a encore eu aucun client!");
        }
        JpaUtil.fermerContextePersistance();
        return clients;
    }
    
    /*
    * Ce service obtient l'historique des consultations pour un employé donné.
    * @param employe L'employé pour lequel obtenir l'historique des consultations.
    * @return Une liste des consultations associées à l'employé donné.
    */
    public static List<Consultation> obtenirHistoriqueConsultation(Employe employe){
        JpaUtil.creerContextePersistance();

        ConsultationDao consultationdao = new ConsultationDao();
        List<Consultation> consultations = consultationdao.findAll(employe);
        
        if (consultations.isEmpty())
        {
            System.out.println("Il n'y a encore aucune consultation");
        }
        JpaUtil.fermerContextePersistance();
        return consultations;
    }
    
    /*
    * Ce service vérifie si une adresse e-mail appartient à un client enregistré.
    * @param adresseMail L'adresse e-mail à vérifier.
    * @return true si l'adresse e-mail correspond à un client enregistré, false sinon.
    */
    public static boolean estClient(String adresseMail){
        boolean resultat = true;
        JpaUtil.creerContextePersistance();

        ClientDao clientdao = new ClientDao();
        resultat = clientdao.estClient(adresseMail);
        
        if (resultat==false)
        {
            System.out.println("Ce n'est pas un client connu");
        }
        JpaUtil.fermerContextePersistance();
        
        return resultat;     
    }
    
    /*
    * Ce service vérifie si une adresse e-mail appartient à un employé enregistré.
    * @param adresseMail L'adresse e-mail à vérifier.
    * @return true si l'adresse e-mail correspond à un employé enregistré, false sinon.
    */
    public static boolean estEmploye(String adresseMail){
        boolean resultat = true;
        JpaUtil.creerContextePersistance();

        EmployeDao employedao = new EmployeDao();
        resultat = employedao.estEmploye(adresseMail);
        
        if (resultat==false)
        {
            System.out.println("Ce n'est pas un employe connu");
        }
        JpaUtil.fermerContextePersistance();
        
        return resultat;     
    }
    
    /*
    * Ce service demande une prédiction astrologique pour un client donné.
    * @param amour Niveau d'amour du client.
    * @param sante Niveau de santé du client.
    * @param travail Niveau de travail du client.
    * @param client Le client pour lequel demander la prédiction.
    * @return Une liste de prédictions astrologiques.
    */
    public static List<String> demanderPrediction(int amour, int sante, int travail, Client client)
    {
        List<String> predictions = null;
        try
        {
        AstroNetApi astroApi = new AstroNetApi();
        
        ProfilAstral profil = client.getProfilAstral();
        String couleur = profil.getCouleurPorteBonheur();
        String animal = profil.getAnimalTotem();
        
        predictions = astroApi.getPredictions(couleur, animal, amour, sante, travail);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return predictions;
    }
}
