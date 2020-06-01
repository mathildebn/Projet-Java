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
    public static void main(String args[]){
        
     
}
    
    public static void affichageFenetre()
    {
         Lafenetre fen;
         fen = new Lafenetre(600,600,"TEST");
         
         fen.setVisible(true);
            
        System.out.println("AffichageFenetre()");
        
        LiaisonSQL(fen);
    }
    
    
    public static void LiaisonSQL(Lafenetre fenetre)
    {
         try{
          
          System.out.println("LiaisonSQL()"); 

//Permet de savoir si la connexion a été réussie
boolean connect_success = false;
boolean erreur = false;

//Permet de charger le driver pour communiquer avec MySQL
Class.forName("com.mysql.jdbc.Driver");

//Permet de se connecter à la base de données
Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");

//Initialise une requete qui pourra être executée pour les requêtes SQL
Statement requeteSQL = connexionSQL.createStatement();

//Execute une requête SQL et enregistre les données
ResultSet resultat_SQL_seance=requeteSQL.executeQuery("select * from seance");


//On parcours toutes les données SQL reçues
  while(resultat_SQL_seance.next())
  {

      int DebutCours;
      int FinCours = resultat_SQL_seance.getInt(5);
      
      for(DebutCours = resultat_SQL_seance.getInt(4); DebutCours < FinCours; DebutCours++)
      {
          int n_ligne = 0;
          
          int n_colonne = 0;
          
          int id_cours = resultat_SQL_seance.getInt(7);
          
          int id_salle = resultat_SQL_seance.getInt(9);
          
          String jour = resultat_SQL_seance.getString(10);
          
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
          
          Statement requeteSQL_cours = connexionSQL.createStatement();
          ResultSet resultat_SQL_NomCours = requeteSQL_cours.executeQuery("select Nom_cours from cours where Id_cours = '" + id_cours + "'");

          while(resultat_SQL_NomCours.next())
          {
              
                                  Statement requeteSQL_salle = connexionSQL.createStatement();
                                  ResultSet resultat_SQL_NomSalle = requeteSQL_salle.executeQuery("select Nom_salle from salle where Id_salle = '" + id_cours + "'");
          
                                  while(resultat_SQL_NomSalle.next())
                                  {
                                    fenetre.updateTableau(n_ligne, n_colonne,resultat_SQL_NomCours.getString(1) + " : " + resultat_SQL_NomSalle.getString(1));
                                  }

          }
          
      }
      
    


    }


    }catch(Exception e){ System.out.println(e);}
    }
        
    }


