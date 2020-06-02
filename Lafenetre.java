/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import modele.ModeleTableau;

//Nouveaux imports
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JComboBox;
import java.util.ArrayList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 *
 * @author NivineDiallo & Thanh Kieu
 */

public class Lafenetre extends JFrame implements ActionListener {
    
    ModeleTableau grilles = new ModeleTableau();
    JButton BtnAjouterValeur = new JButton("Ajouter le cours");
    JTable table = new JTable(grilles);
    JPanel fond = new JPanel();
    //JFrame frame = new JFrame("Hyperplanning");
    
    JMenuBar mb = new JMenuBar();
    JMenu menuCours = new JMenu("Cours");
    JMenu menuSalle = new JMenu("Salles");
    JMenuItem i1= new JMenuItem ("Emploi du temps");
    JMenuItem i2= new JMenuItem ("Récapitulatif des cours");
    JMenuItem i3= new JMenuItem ("Salles libres");
    
    
    
    
    
        JComboBox menu_heure_debut;
        JComboBox menu_heure_fin;
        JComboBox menu_jour;
        JComboBox menu_cours;
        JComboBox menu_salle;
        
        Object[] liste_heure_debut = new Object[]{"8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h"};
        
                Object[] liste_heure_fin = new Object[]{"9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h"};
 
                        Object[] liste_jour = new Object[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
 
                                Object[] liste_cours = new Object[]{""};
 
                                        Object[] liste_salle = new Object[]{""};
 
            
                               
                               
                               
                               
                               
                               
                               
    //constructeur
    public Lafenetre(){
        setSize (1000,850); //donnne la taille hauteur + largeur de la fenetre
        setTitle ("Titre"); //donne le titre de la fenêtre
        setVisible(true);
    }
    
    
    
    
    
    
    public Lafenetre(int longueur, int largeur, String titre){
        setSize (longueur,largeur); //donnne la taille hauteur + largeur de la fenetre
        setTitle (titre); //donne le titre de la fenêtre
        setVisible(true);
        
        //Fond
        //JPanel fond = new JPanel();
        fond.setPreferredSize(new Dimension(1200,850));
        setContentPane(fond);
        fond.setBackground(Color.LIGHT_GRAY);
       
        mb.add(menuCours);
        mb.add(menuSalle);
        menuCours.add(i1);
        menuCours.add(i2);
        menuSalle.add(i3);
        setJMenuBar(mb);  
        
        
        //Grilles
        //ModeleTableau grilles = new ModeleTableau();
        //JTable table = new JTable(grilles);
        table.setRowHeight(32);
        

 table.getColumnModel().getColumn(0).setPreferredWidth(70);
    
 for(int i = 1; i <= 6; i++)
 {
     table.getColumnModel().getColumn(i).setPreferredWidth(150); 
 }

        this.setVisible(true);
        
        //JButton BtnAjouterValeur = new JButton("Ajouter une valeur");
        BtnAjouterValeur.addActionListener(this);
        
        
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );

                
        menu_heure_debut = new JComboBox(liste_heure_debut);
                menu_heure_fin = new JComboBox(liste_heure_fin);
                        menu_jour = new JComboBox(liste_jour);
                                menu_cours = new JComboBox();
                                        menu_salle = new JComboBox();
                                
                                        
                                
            //Ajoute les valeurs dans les menus
            try{

//Permet de savoir si la connexion a été réussie
boolean connect_success = false;
boolean erreur = false;

//Permet de charger le driver pour communiquer avec MySQL
Class.forName("com.mysql.jdbc.Driver");

//Permet de se connecter à la base de données
Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");

//Initialise une requete qui pourra être executée pour les requêtes SQL
Statement requeteSQL_cours = connexionSQL.createStatement();

//Execute une requête SQL et enregistre les données
ResultSet resultat_SQL_cours = requeteSQL_cours.executeQuery("select Nom_cours from cours");

//Initialise une requete qui pourra être executée pour les requêtes SQL
Statement requeteSQL_salle = connexionSQL.createStatement();

//Execute une requête SQL et enregistre les données
ResultSet resultat_SQL_salle = requeteSQL_salle.executeQuery("select Nom_salle from salle");


//On parcours toutes les données SQL reçues
  while(resultat_SQL_cours.next())
  {
      menu_cours.addItem(resultat_SQL_cours.getString(1));
    }
  
  //On parcours toutes les données SQL reçues
  while(resultat_SQL_salle.next())
  {
     menu_salle.addItem(resultat_SQL_salle.getString(1));
    }

    }catch(Exception e){ System.out.println(e);}
            
            
fond.add(menu_heure_debut);
fond.add(menu_heure_fin);
fond.add(menu_jour);
fond.add(menu_cours);
fond.add(menu_salle);

        fond.add(BtnAjouterValeur);

        fond.add(scrollPane);
        
        
        
    }
    
    
    
    
    
    
   
            //Se déclenche quand il y a une intéraction avec un objet dynamique
            public void actionPerformed(ActionEvent evenement)
        {
            //Récupère le nom de l'objet enclenché
            Object ObjetSource = evenement.getSource();
            
            //Si c'est le bouton d'ajout de valeur, on appelle la fonction ModifierValeurTableau
            if(ObjetSource == BtnAjouterValeur)
            {
                ModifierValeurTableau();
            }
            
            
        }
            
            //Fonction appelée par actionPerformed
            public void ModifierValeurTableau()
            {
                /*
                  //Depuis la console, on demande d'entrer les infos pour ajouter la valeur
                  Scanner scannerLigne = new Scanner(System.in);
                  System.out.println("Veuillez saisir le numero de ligne :");
                  int n_ligne = scannerLigne.nextInt();


                  Scanner scannerColonne = new Scanner(System.in);
                  System.out.println("Veuillez saisir le numero de colonne :");
                  int n_colonne = scannerColonne.nextInt();
                  
                  
                  Scanner scannerValeur = new Scanner(System.in);
                  System.out.println("Veuillez saisir le texte à ajouter :");
                  String nouvelle_valeur = scannerValeur.nextLine();
                
                  */
                
                
                
          String valeur_comboBox_horaires1 = String.valueOf(menu_heure_debut.getSelectedItem());
          String valeur_comboBox_horaires2 = String.valueOf(menu_heure_fin.getSelectedItem());
          String valeur_comboBox_jour = String.valueOf(menu_jour.getSelectedItem());
          
          int n_ligne = 0;
          int heure_debut;
          int heure_fin;
          
          int n_colonne = 0;
          
          switch(valeur_comboBox_horaires1)
          {
                                case "8h":
                  n_ligne = 0;
                                     heure_debut = 8;
                  //heure_fin = 9;
                  break;
                                case "9h":
                   n_ligne = 1;
                                     heure_debut = 9;
                  //heure_fin = 10;
                  break;
                                case "10h":
                   n_ligne = 2;
                                     heure_debut = 10;
                  //heure_fin = 11;
                  break;
                                case "11h":
                   n_ligne = 3;
                                     heure_debut = 11;
                  //heure_fin = 12;
                  break;
                                case "12h":
                   n_ligne = 4;
                                     heure_debut = 12;
                  //heure_fin = 13;
                  break;
                                case "13h":
                   n_ligne = 5;
                                     heure_debut = 13;
                  //heure_fin = 14;
                  break;
                                case "14h":
                   n_ligne = 6;
                                     heure_debut = 14;
                  //heure_fin = 15;
                  break;
                                case "15h":
                   n_ligne = 7;
                                     heure_debut = 15;
                  //heure_fin = 16;
                  break;
          }
          
          switch(valeur_comboBox_horaires2)
          {
                                case "9h":
                  n_ligne = 0;
                   //                  heure_debut = 8;
                  heure_fin = 9;
                  break;
                                case "10h":
                   n_ligne = 1;
                   //                  heure_debut = 9;
                  heure_fin = 10;
                  break;
                                case "11h":
                   n_ligne = 2;
                   //                  heure_debut = 10;
                  heure_fin = 11;
                  break;
                                case "12h":
                   n_ligne = 3;
                  //                   heure_debut = 11;
                  heure_fin = 12;
                  break;
                                case "13h":
                   n_ligne = 4;
                  //                   heure_debut = 12;
                  heure_fin = 13;
                  break;
                                case "14h":
                   n_ligne = 5;
                  //                   heure_debut = 13;
                  heure_fin = 14;
                  break;
                                case "15h":
                   n_ligne = 6;
                  //                   heure_debut = 14;
                  heure_fin = 15;
                  break;
                                case "16h":
                   n_ligne = 7;
                  //                   heure_debut = 15;
                  heure_fin = 16;
                  break;
          }
          
          switch(valeur_comboBox_jour)
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
                
                //this.grilles.setValueAt(n_ligne, n_colonne, nouvelle_valeur);
                System.out.println(n_ligne + " & " + n_colonne);
                
                //Permet de réactualiser l'affichage de la fenêtre
                fond.updateUI();
            }
            
            public void updateTableau(int n_ligne, int n_colonne, String nouvelle_valeur)
            {
                  
                this.grilles.setValueAt(n_ligne, n_colonne, nouvelle_valeur);
                System.out.println(this.grilles.getValueAt(n_ligne, n_colonne));
                
                //Permet de réactualiser l'affichage de la fenêtre
                fond.updateUI();
            }
            
            
public void updateSQL(int heure_debut, int heure_fin, String jour)
{
try{

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
ResultSet resultat_SQL=requeteSQL.executeQuery("insert into seance values('1', '1', )");

//On parcourt toutes les données SQL reçues
  while(resultat_SQL.next())
  {


    }

    }catch(Exception e){ System.out.println(e);}
}
            
}

