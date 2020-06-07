/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import vue.Lafenetre;


/**
 *
 * @author NivineDiallo
 */
public class edtFenetre {

    String identifiant = "vide";
    String id_user = "vide";
    int typeCompte = 0;
    String nom_groupe = "vide";
    String id_groupe = "vide";
    
    //Constructeur avec paramètres prenant en compte les infos de l'utilisateur connecté
    public edtFenetre(String identifiant_SQL, String user, int typeCompteSQL, String NomGroupe, String IDGroupe)
    {
        identifiant = identifiant_SQL;
        id_user = user;
        typeCompte = typeCompteSQL;
        nom_groupe = NomGroupe;
        id_groupe = IDGroupe;
        
    }

    public edtFenetre() {
       
    }
    
    //Retourne le nom d'utilisateur "e-mail"
    public String getIdentifiant()
    {
        return identifiant;
    }
    
    
    //Retourne l'id_utilisateur
    public String getUserID()
    {
        return id_user;
    }
    
    //Retourne l'id_utilisateur
    public String getGroupID()
    {
        return id_groupe;
    }
    
    //Retourne le nom du groupe auquel appartient l'utilisateur
    public String getGroupName()
    {
        return nom_groupe;
    }
    
    
    //Permet d'afficher la fenêtre
    public void affichageFenetre()
    {
        //On donne un titre à la fenêtre incluant le nom de l'utilisateur ainsi que son groupe
        String Titre_fenetre = "Hyperplanning - " + getIdentifiant() + " - " + getGroupName();
       
         //On instancie
         Lafenetre fen;
         
         //On donne les dimensions par défaut, le titre ainsi que le type de compte de l'utilisateur
         fen = new Lafenetre(1400,600,Titre_fenetre, typeCompte, getGroupID());
            

        afficherEDT(fen);
       
         
         fen.setVisible(true);
    }

    
    //Permet d'afficher les cours associés au groupe de l'utilisateur connecté au premier lancement du programme
    public void afficherEDT(Lafenetre fenetre)
    {
         try{
          
            System.out.println("afficherEDT()"); 


            //Permet de charger le driver pour communiquer avec MySQL
            Class.forName("com.mysql.jdbc.Driver");

            //Permet de se connecter à la base de données
            Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");


            //Initialise une requete qui pourra être executée pour les requêtes SQL
            Statement getSQLGroupID = connexionSQL.createStatement();

            //Execute une requête SQL et enregistre les données
            ResultSet resultatgetSQLGroupID=getSQLGroupID.executeQuery("select Id_groupe from etudiant where Id_utilisateur ='" + getUserID() + "'");




            while(resultatgetSQLGroupID.next())
            {
                //Initialise une requete qui pourra être executée pour les requêtes SQL
                Statement getSQLSeanceID = connexionSQL.createStatement();

                //Execute une requête SQL et enregistre les données
                ResultSet resultatgetSQLSeanceID=getSQLSeanceID.executeQuery("select Id_seance from seance_groupe where Id_groupe ='" + resultatgetSQLGroupID.getString(1) + "'");

                while(resultatgetSQLSeanceID.next())
                {
                    //Initialise une requete qui pourra être executée pour les requêtes SQL
                    Statement requeteSQL = connexionSQL.createStatement();

                    //Execute une requête SQL et enregistre les données
                    ResultSet resultat_SQL_seance=requeteSQL.executeQuery("select * from seance where Id_seance = '" + resultatgetSQLSeanceID.getString(1) + "'");



                    //On parcours toutes les données SQL reçues
                    while(resultat_SQL_seance.next())
                    {

                        int DebutCours;
                        int FinCours = resultat_SQL_seance.getInt(5);
      
                        //Permet d'afficher une séance sur plusieurs lignes du tableau si un cours dure plus d'une heure
                        for(DebutCours = resultat_SQL_seance.getInt(4); DebutCours < FinCours; DebutCours++)
                        {
                            int n_ligne = 0;
          
                            int n_colonne = 0;
          
                            int id_cours = resultat_SQL_seance.getInt(7);
              
                            String jour = resultat_SQL_seance.getString(10);
     
          
                            //Selon l'heure de début (Heure_debut) recupérée depuis la table seance, on affecte un numéro de ligne à n_ligne afin de déterminer sur quelle ligne se situera le cours 
                            switch(DebutCours)
                            {
                                    case 8:
                                      n_ligne = 0;
                                      break;
                                    case 9:
                                      n_ligne = 1;
                                      break;
                                    case 10:
                                      n_ligne = 2;
                                      break;
                                    case 11:
                                      n_ligne = 3;
                                      break;
                                    case 12:
                                      n_ligne = 4;
                                      break;
                                    case 13:
                                      n_ligne = 5;
                                      break;
                                    case 14:
                                      n_ligne = 6;
                                      break;
                                    case 15:
                                      n_ligne = 7;
                                      break;
                            }
          
         
          
                            //Selon le jour recupérée depuis la table seance, on affecte un numéro de colonne à n_colonne afin de déterminer sur quelle colonne (jour) se situera le cours 
                            switch(jour)
                            {
                                    case "Lundi":
                                      n_colonne = 1;
                                      break;
                                    case "Mardi":
                                      n_colonne = 2;
                                      break;
                                    case "Mercredi":
                                      n_colonne = 3;
                                      break;
                                    case "Jeudi":
                                      n_colonne = 4;
                                      break;
                                    case "Vendredi":
                                      n_colonne = 5;
                                      break;
                                    case "Samedi":
                                      n_colonne = 6;
                                      break;
                            }
          
          
                            //On récupère le nom du cours ainsi que la salle à afficher à l'aide de requête SQL
          
                            //D'abord, on récupère le nom du cours
                            Statement requeteSQL_cours = connexionSQL.createStatement();
                            ResultSet resultat_SQL_NomCours = requeteSQL_cours.executeQuery("select Nom_cours from cours where Id_cours = '" + id_cours + "'");

          
                            while(resultat_SQL_NomCours.next())
                            {
              
                                //Ensuite, on récupère le nom de la salle
                                Statement requeteSQL_salle = connexionSQL.createStatement();
                                ResultSet resultat_SQL_NomSalle = requeteSQL_salle.executeQuery("select Nom_salle from salle where Id_salle = '" + resultat_SQL_seance.getString(9) + "'");
          
                                  while(resultat_SQL_NomSalle.next())
                                  {
                                     //On appelle donc une fonction qui permettra d'afficher les cours et les salles dans le tableau
                                     fenetre.updateTableau(n_ligne, n_colonne,resultat_SQL_NomCours.getString(1) + " : " + resultat_SQL_NomSalle.getString(1));
                                  } //Fin while NonSalle

                            } //Fin while NomCours
          
                        } //Fin for

                      } //Fin while SQL_seance
 
                  } //Fin while resultatgetSQLSeanceID
                
} //Fin while resultatgetSQLGroupID






            }catch(Exception e){ System.out.println(e);}
    }
        
 }


