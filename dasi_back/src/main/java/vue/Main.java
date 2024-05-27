package vue;

import utile.Saisie;
import metier.service.Service;
import dao.JpaUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import utile.AstroNetApi;
import static utile.Saisie.lireInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author qmariat
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, IOException {
        // TODO code application logic here

        JpaUtil.creerFabriquePersistance();

        /*
        Client a = new Client("Hugo","Victor","vhugo@paris.fr","Paris");
        a.setMotDePasse("gigi");
        
        Client b = new Client("Yourcenar","Marguerite","myourcenar@gmail.com","Toulouse");
        Client c = new Client("Zola","Emile","ezola@gmail.com","Lyon");
        c.setMotDePasse("zozo");
        Client d = new Client("Wirane","Hamza","vhugo@paris.fr","Londres");
         */
 /*
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
         */
 /*
        Service.inscrireClient(a);
        Service.inscrireClient(b);
        Service.inscrireClient(c);
        Service.inscrireClient(d);
         */
 /*
        Client e = Service.authentifierClient("ezola@gmail.com","zozo");
        if (e==null) {
            System.out.println("Aucun client correspondant");
        }
        else {
            System.out.println(e);
        }
        
        
        long l = 2;
        Long x=l;
        Client v = Service.rechercherClientParID(x);
        if (v==null) {
            System.out.println("Aucun client correspondant");
        }
        else {
            System.out.println(v);
        }
        

        int i;
        List<Client> clients = Service.consulterListeClients();
        if (clients == null) {
            System.out.println("Il n'y a aucun client");
        }
        else {
            for (i=0; i<clients.size() ; i++)
            {
                Client client = clients.get(i);
                System.out.println("Client #" + client.getId() + ": " + client.getNom() + " "+ client.getPrenom() +" ["+client.getLatitude()+", "+client.getLongitude()+"]");
            }
        }
        
        //inscription, authentification, recherche de clients
        
        Saisie.pause();
        String nom = Saisie.lireChaine("saisir nom");
        String prenom = Saisie.lireChaine("saisir prenom");
        String mail = Saisie.lireChaine("saisir mail");
        String ville = Saisie.lireChaine("saisir ville");
        Client e = new Client(nom,prenom,mail,ville);
        Service.inscrireClient(e);
        
        String mdp = Saisie.lireChaine("saisir mdp");
        e.setMotDePasse(mdp);
        
        String mailbis = Saisie.lireChaine("test authentification, saisir mail");
        String mdpbis = Saisie.lireChaine("saisir mdp");
        Client f = Service.authentifierClient(mailbis,mdpbis);
        if (f==null) {
            System.out.println("Aucun client correspondant");
        }
        else {
            System.out.println(f);
        }
         */
 
        
        //Scénario de test 1
        Service.initialisation();

        //Client bien formé
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNaissance = simpleDateFormat.parse("10/12/1995");
        Client a = new Client("Hugo", "Victor", "vhugo@paris.fr", "Paris", "toto", dateNaissance, "0625252525");

        Service.inscrireClient(a);
        Saisie.pause();

        //Client avec erreur
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateNaissance = simpleDateFormat.parse("14/08/1992");
        Client b = new Client("Fauxnom", "Victor", "vfauxnom@paris.fr", "Tooloose", "tata", dateNaissance, "0625252525");

        Service.inscrireClient(b);
        Saisie.pause();

        //Affichage de la liste des clients
        System.out.println(Service.obtenirListeClients());

        Saisie.pause();

        //Authentification du client qui fonctionne
        Client c = Service.authentifierClient("vhugo@paris.fr", "toto");
        if (c == null) {
            System.out.println("Aucun client correspondant");
        } else {
            System.out.println("Authentification client réussi");
        }

        Saisie.pause();

        //Authentification du client qui a échoué
        Client d = Service.authentifierClient("vfauxnom@paris.fr", "tata");
        if (d == null) {
            System.out.println("Aucun client correspondant");
        } else {
            System.out.println("Authentification client réussi");
        }

        Saisie.pause();

        //Affichage des médiums
        List<Medium> mediums = Service.obtenirListeMediums();

        System.out.println(mediums);

        int index = Saisie.lireInteger("Index du medium choisi");

        //Demander une consultation
        Consultation consultation = Service.demanderConsultation(mediums.get(index), c);

        Saisie.pause();

        //En demander une autre directement
        Consultation consultationbis = Service.demanderConsultation(mediums.get(index), c);
        Saisie.pause();

        //Se connecter en tant qu'employé
        String mail = Saisie.lireChaine("saisir mail");
        String mdp = Saisie.lireChaine("saisir mdp");

        Employe employe = Service.authentifierEmploye(mail, mdp);
        while (employe == null) {
            if (employe == null) {
                System.out.println("Aucun employe correspondant");
                mail = Saisie.lireChaine("saisir mail");
                mdp = Saisie.lireChaine("saisir mdp");

                employe = Service.authentifierEmploye(mail, mdp);
            } else {
                System.out.println("Authentification employe réussi");
                
            }
        }

        Saisie.pause();
        //Test des services estClient, estEmploye
        System.out.println(Service.estClient("vhugo@paris.fr"));
        System.out.println(Service.estEmploye("vhugo@paris.fr"));

        System.out.println(Service.estClient(mail));
        System.out.println(Service.estEmploye(mail));

        System.out.println(Service.estClient("vfauxnom@paris.fr"));
        System.out.println(Service.estEmploye("vfauxnom@paris.fr"));

        Saisie.pause();

        Consultation consultation_emp = Service.verifierDemandeConsultation(employe);
        if (consultation_emp == null) {
            System.out.println("Vous n'avez pas de demande de consultation en attente");
        } else {
            System.out.println("Vous avez une demande de consultation en attente");
            System.out.println(consultation_emp);
        }

        Saisie.pause();

        if (consultation_emp != null) {
            System.out.println("Accepter quand vous êtes prêt");
            Saisie.pause();
            Service.accepterConsultation(consultation_emp);

            System.out.println("Cliquer pour obtenir de l'aide");
            Saisie.pause();

            List<Integer> valeurs = new ArrayList();
            valeurs.add(1);
            valeurs.add(2);
            valeurs.add(3);
            valeurs.add(4);
            // Prédictions: niveaux entre 1 (mauvais) et 4 (excellent)
            int niveauAmour = lireInteger("niveau amour", valeurs);
            int niveauSante = lireInteger("niveau sante", valeurs);
            int niveauTravail = lireInteger("niveau travail", valeurs);

            List<String> predictions = Service.demanderPrediction(niveauAmour, niveauSante, niveauTravail, c);
            String predictionAmour = predictions.get(0);
            String predictionSante = predictions.get(1);
            String predictionTravail = predictions.get(2);

            System.out.println("~<[ Prédictions ]>~");
            System.out.println("[ Amour ] " + predictionAmour);
            System.out.println("[ Santé ] " + predictionSante);
            System.out.println("[Travail] " + predictionTravail);

            System.out.println("Cliquer pour terminer la consultation");
            Saisie.pause();
            String commentaire = Saisie.lireChaine("Saisir commentaire");
            Service.validerConsultation(consultation_emp,commentaire);
        }

        Saisie.pause();
        //Service Obtenir liste médiums triés par nombre de consultation
        System.out.println(Service.obtenirListeMediumsTriParCons());

        Saisie.pause();

        //Service obtenir liste client consulté par employé
        System.out.println(Service.obtenirListeClientsParEmploye(employe));

        Saisie.pause();
        //Historique des consultations du client dernièrement consulté
        //[ERROR] Peut-être plutôt getEmploye ?
        List<Consultation> historique = consultation.getClient().getHistoriqueConsultation();
        for (Consultation consultation1 : historique) {
            System.out.println(consultation1);
        }
 
        Saisie.pause();

        //Service d'historique
        System.out.println(Service.obtenirHistoriqueConsultation(employe));
        

        JpaUtil.fermerFabriquePersistance();
        
        
        /*
        //Scénario 2 : Plus d'employé disponible
        
        //Scénario de test 
        Service.initialisation();

        //Client bien formé
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNaissance = simpleDateFormat.parse("10/12/1995");
        Client a = new Client("Hugo", "Victor", "vhugo@paris.fr", "Paris", "toto", dateNaissance, "0625252525");
        Client b = new Client("Pierre", "Antoine", "apierre@paris.fr", "Paris", "toto", dateNaissance, "0625252525");


        Service.inscrireClient(a);
        Service.inscrireClient(b);

        Client f = Service.authentifierClient("vhugo@paris.fr", "toto");
        Client g = Service.authentifierClient("apierre@paris.fr", "toto");

        Saisie.pause();
       
        //Affichage des médiums
        List<Medium> mediums = Service.obtenirListeMediums();

        System.out.println(mediums);

        //Demander une consultation
        Consultation consultation = Service.demanderConsultation(mediums.get(1), f);
        Saisie.pause();
        Consultation consultationa = Service.demanderConsultation(mediums.get(1), g);
        Saisie.pause();
        if (consultationa == null) {
            System.out.println("consultation est bien null");
        }
       
        Saisie.pause();
        
        Service.accepterConsultation(consultation);
        String commentaire = Saisie.lireChaine("Saisir commentaire");
        Service.validerConsultation(consultation,commentaire);
        List<Consultation> historique = consultation.getClient().getHistoriqueConsultation();
        for (Consultation consultation1 : historique) {
            System.out.println(consultation1);
        }
        
        
        //Verifier que c'est bien l'employé avec le moins de consultation qui est choisi
        Consultation consultationb = Service.demanderConsultation(mediums.get(2), f);
        Saisie.pause();
        Service.accepterConsultation(consultationb);
        commentaire = Saisie.lireChaine("Saisir commentaire");
        Service.validerConsultation(consultationb,commentaire);
        Saisie.pause();
        
        consultationb = Service.demanderConsultation(mediums.get(2), f);
        Saisie.pause();
        Service.accepterConsultation(consultationb);
        commentaire = Saisie.lireChaine("Saisir commentaire");
        Service.validerConsultation(consultationb,commentaire);
        Saisie.pause();
        
        consultationb = Service.demanderConsultation(mediums.get(2), f);
        Saisie.pause();
        Service.accepterConsultation(consultationb);
        commentaire = Saisie.lireChaine("Saisir commentaire");
        Service.validerConsultation(consultationb,commentaire);
        Saisie.pause();
        
        historique = consultation.getClient().getHistoriqueConsultation();
        for (Consultation consultation1 : historique) {
            System.out.println(consultation1);
        }
        
        JpaUtil.fermerFabriquePersistance();
        */
    }

}
