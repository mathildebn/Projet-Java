/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thanh Kieu
 */

package vue;


//import vue.FenetreConnexion;
//import controleur.edtFenetre;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import modele.ModeleTableau;

//Nouveaux imports
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

//Imports pour camembert
import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.*; 
import org.jfree.data.general.DefaultPieDataset;

public class Lafenetre extends JFrame implements ActionListener {
    
    ModeleTableau grilles = new ModeleTableau();
    JTable table = new JTable(grilles);
    JPanel fond = new JPanel();
    
    
    //Création de la barre de menu
    JMenuBar mb = new JMenuBar();
    JMenu menuCompte = new JMenu("Mon compte");
    
    
    //Création des items à mettre dans les menus
    
    JMenuItem deconnexionItem= new JMenuItem ("Déconnexion");

    
    //Déclaration des objets du menu ajouter
        JPanel conteneur_combobox = new JPanel();
        JLabel Info_creer_nvCours =  new JLabel("Choisir parmis les options suivantes pour créer une nouvelle séance");
        JButton BtnAjouterValeur = new JButton("Ajouter le cours");
        JButton BtnSupprimerTout = new JButton("Supprimer les cours");
        JButton BtnCamembert = new JButton("Afficher statistiques");
        JComboBox menu_heure_debut;
        JComboBox menu_heure_fin;
        JComboBox menu_jour;
        JComboBox menu_cours;
        JComboBox menu_salle;
        JComboBox menu_groupe;
        JComboBox EDT_Salle;
        
        Object[] liste_heure_debut = new Object[]{"8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h"};
        
        Object[] liste_heure_fin = new Object[]{"9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h"};
 
        Object[] liste_jour = new Object[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
 
        Object[] liste_cours = new Object[]{"Cours"};
 
        Object[] liste_salle = new Object[]{"Salle"};
                                        
        Object[] liste_groupe = new Object[]{"Groupe"};
        
        Object[] liste_EDT_Salle = new Object[]{"Disponibilité des salles"};
 
            
                               
                               
          String valeur_comboBox_heureDebut;
          String valeur_comboBox_heureFin;
          String valeur_comboBox_jour;
          
          String valeur_comboBox_cours;
          String valeur_comboBox_salle;
          String valeur_comboBox_groupe;
          
          String valeur_comboBox_coursIndex;
          String valeur_comboBox_salleIndex;
          String valeur_comboBox_groupeIndex;
          
          String IDGroupe;
        
    //Fin des déclarations
          
                                    
                               
//Constructeur par défaut
public Lafenetre(){
        setSize (1000,850); //donnne la taille hauteur + largeur de la fenetre
        setTitle ("Titre"); //donne le titre de la fenêtre
        setVisible(true);
    }
    
    
    
//Constructeur avec paramètres   
public Lafenetre(int longueur, int largeur, String titre, int typeCompte, String ID_duGroupe){
    
        IDGroupe = ID_duGroupe;
    
        //donnne la taille hauteur + largeur de la fenetre
        setSize (longueur,largeur);
        
        //donne le titre de la fenêtre
        setTitle (titre);

        //On affiche la fenêtre
        setVisible(true);
        
        //Paramétrage du JPanel "fond"
        fond.setPreferredSize(new Dimension(1200,850));
        setContentPane(fond);
        fond.setBackground(Color.LIGHT_GRAY);
       
        //On ajoute dans la barre des menus, les différents menus
        mb.add(menuCompte);
        

        //On associe un évènement au clic sur l'item de deconnexion
        deconnexionItem.addActionListener(this);

        //On ajooute les items dans leurs menus respectifs
        menuCompte.add(deconnexionItem);
        
        
        setJMenuBar(mb);  
        
        
        //Grilles
        //Définis la hauteur d'une ligne en pixel
        table.setRowHeight(118);
        
        //Définis la largeur par défaut de la première colonne du tableau (horaires)
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
    
        //Définis la largeur par défaut des autres colonnes du tableau    
        for(int i = 1; i <= 6; i++)
        {
            table.getColumnModel().getColumn(i).setPreferredWidth(150); 
        }
      
                                        
            
                        
        //Associe un layout au panel qui permettra d'imposer la position des objets dans la fenêtre
        fond.setLayout(new BorderLayout());

        //On force le tableau à suivre la taille de la fenêtre
        fond.add(new JScrollPane(table));

        //On appelle cette fonction pour savoir si on affiche le menu d'ajout de cours selon le type de compte (Droit)
        afficherMenuAjouter(typeCompte);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
    
    
    
//Se déclenche quand il y a une intéraction avec un objet dynamique
@Override
public void actionPerformed(ActionEvent evenement)
{
            //Récupère le nom de l'objet enclenché
            Object ObjetSource = evenement.getSource();
            
            //Si c'est le bouton d'ajout de valeur, on appelle la fonction ModifierValeurTableau
            if(ObjetSource == BtnAjouterValeur)
            {
                ModifierValeurTableau();
            }
            
            //Si on sélectionne un autre groupe
            if(ObjetSource == menu_groupe)
            {
                afficherEDTGroupe(0);
            }
            
            //Si on sélectionne un autre EDT de Salle
            if(ObjetSource == EDT_Salle)
            {
                afficherEDTSalle();
            }
            
            //Si on clique sur l'item de deconnexion
            if(ObjetSource == deconnexionItem)
            {
                System.out.println("Deconnexion...");
                this.dispose();
                FenetreConnexion FenetreConnexion = new FenetreConnexion();
                FenetreConnexion.setVisible(true);
            }
            
            //Si on clique sur le bouton de suppression des cours
            if(ObjetSource == BtnSupprimerTout)
            {
                System.out.println("Suppression des cours du groupe actuel...");
                
                //On appelle cette fonction pour supprimer toutes les seances associés au groupe sélectionné dans le combobox menu_groupe
                clearTable_Seance_Groupe(valeur_comboBox_groupeIndex);
                
                //On réactualise l'affichage
                fond.updateUI();

            }
            
            //Si on clique sur le bouton d'affichage des statistiques des cours
            if(ObjetSource == BtnCamembert)
            {
                System.out.println("Affichage stats");
                afficherCamembert();
            }
            
            
}


//Affiche l'emploi du temps de salle séléctionné dans le combobox EDT_Salle            
public void afficherEDTSalle()
{
            String valeur_comboBox_edtsalleIndex = String.valueOf(EDT_Salle.getSelectedIndex());



  
    try{
          
          //On vide le tableau d'abord, car sinon les deux emplois du temps fusionneront dans l'affichage
          this.grilles.clearTableau();
          
          //On réactualise l'affichage
          fond.updateUI();
          

        //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");

       
        //On récupère l'index du groupe sélectionné
        valeur_comboBox_edtsalleIndex = String.valueOf(EDT_Salle.getSelectedIndex());
        
        //On associe cet index à un id pour savoir de quel groupe nous voulons supprimer les cours
        String ID_salle = valeur_comboBox_edtsalleIndex;

        System.out.println(ID_salle);
        
        
                    //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLSeanceID = connexionSQL.createStatement();

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLSeanceID=getSQLSeanceID.executeQuery("select * from seance where id_salle ='" + ID_salle + "'");
        

        
        while(resultatgetSQLSeanceID.next())
        {
            
            
          
    
              //Initialise une requete qui pourra être executée pour les requêtes SQL
              Statement requeteSQL = connexionSQL.createStatement();


                    int DebutCours;
                    int FinCours = resultatgetSQLSeanceID.getInt(5);
      
      //Permet d'afficher une séance sur plusieurs lignes du tableau si un cours dure plus d'une heure
      for(DebutCours = resultatgetSQLSeanceID.getInt(4); DebutCours < FinCours; DebutCours++)
      {
          int n_ligne = 0;
          
          int n_colonne = 0;

              
          String jour = resultatgetSQLSeanceID.getString(10);
     
          
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
          Statement requeteSQL_salle = connexionSQL.createStatement();
          ResultSet resultat_SQL_NomSalle = requeteSQL_salle.executeQuery("select Nom_salle from salle where Id_salle = '" + ID_salle + "'");
          
          while(resultat_SQL_NomSalle.next())
          {
                        //D'abord, on récupère le nom du cours
          Statement requeteSQL_cours = connexionSQL.createStatement();
          ResultSet resultat_SQL_NomCours = requeteSQL_cours.executeQuery("select Nom_cours from cours where Id_cours = '" + resultatgetSQLSeanceID.getString(7) + "'");

          while(resultat_SQL_NomCours.next())
          {
                  //On appelle donc une fonction qui permettra d'afficher les cours et les salles dans le tableau
                 this.updateTableau(n_ligne, n_colonne,resultat_SQL_NomCours.getString(1) + " : " + resultat_SQL_NomSalle.getString(1));
                  
          }
          
          }
          

          
      } //Fin for

     //Fin while SQL_seance
  

} //Fin while resultatgetSQLSeanceID
       
        

    if(valeur_comboBox_edtsalleIndex.equals("0"))
            {
                afficherEDTGroupe(1);
            }
    

    }catch(Exception e){ System.out.println(e);}
}
//Affiche l'emploi du temps du groupe séléctionné dans le combobox menu_groupe            
public void afficherEDTGroupe(int EDT_Etudiant)
{
    
 
        
      try{
          
          
           String id_groupe;
        
        //Permet de savoir l'affichage de l'EDT se fait depuis un compte etudiant ou admin (1 : Etudiant, sinon : Admin)
        if(EDT_Etudiant == 1)
        {
            id_groupe = IDGroupe;
        }
        else
        {
            valeur_comboBox_groupeIndex = String.valueOf(menu_groupe.getSelectedIndex());
            
            //On associe cet index à un id pour savoir de quel groupe nous voulons supprimer les cours
            id_groupe = valeur_comboBox_groupeIndex;
            System.out.println(id_groupe);
        }
        
          
          //On vide le tableau d'abord, car sinon les deux emplois du temps fusionneront dans l'affichage
          this.grilles.clearTableau();
          
          //On réactualise l'affichage
          fond.updateUI();
          

        //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");

       
        //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLSeanceID = connexionSQL.createStatement();

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLSeanceID=getSQLSeanceID.executeQuery("select Id_seance from seance_groupe where Id_groupe ='" + id_groupe + "'");
        
        
  
        

        
        while(resultatgetSQLSeanceID.next())
        {
    
              //Initialise une requete qui pourra être executée pour les requêtes SQL
              Statement requeteSQL = connexionSQL.createStatement();

              //Execute une requête SQL et enregistre toutes les infos d'une séance selon l'identifiant de la séance
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
          
          int id_salle = resultat_SQL_seance.getInt(9);
          
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
              ResultSet resultat_SQL_NomSalle = requeteSQL_salle.executeQuery("select Nom_salle from salle where Id_salle = '" + id_salle + "'");
          
                while(resultat_SQL_NomSalle.next())
                {
                   //On appelle donc une fonction qui permettra d'afficher les cours et les salles dans le tableau
                   this.updateTableau(n_ligne, n_colonne,resultat_SQL_NomCours.getString(1) + " : " + resultat_SQL_NomSalle.getString(1));
                } //Fin while NonSalle

          } //Fin while NomCours
          
      } //Fin for

    } //Fin while SQL_seance
  

} //Fin while resultatgetSQLSeanceID


    }catch(Exception e){ System.out.println(e);}
}

//Permet d'afficher le camembert
public void afficherCamembert()
{
    //Fenetre de dialogue
    JDialog cam = new JDialog();
    cam.setTitle("Statistiques");
    cam.setSize(600,600);
    //Camembert
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    
//Depuis la BDD, on récupère tous les noms des cours existants
    try
    {
         //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");
        
        
        
         //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQL_ListeCours = connexionSQL.createStatement();
      

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQL_ListeCours=getSQL_ListeCours.executeQuery("select Nom_cours from cours");
        
        //Pour chaque cours trouvé, on les ajoute au camembert
        while(resultatgetSQL_ListeCours.next())
        {
                String NomCours = resultatgetSQL_ListeCours.getString(1);
                
                pieDataset.setValue(NomCours, new Integer(getNbreCours(NomCours))); 
        }
        
      
    } catch(Exception e) { }
    
    
    JFreeChart camembert = ChartFactory.createPieChart("Statistiques", 
    pieDataset, true, true, true); 
    ChartPanel stat = new ChartPanel(camembert); 
    cam.getContentPane().add(stat);
    cam.setVisible(true);
}


//Permet d'afficher ou non le menu permettant d'ajouter un cours
public void afficherMenuAjouter(int typeCompte)
    {
        
        
    if(typeCompte == 1)
        
    {        
        BtnCamembert.addActionListener(this);
       EDT_Salle = new JComboBox(liste_EDT_Salle);
        
        EDT_Salle.addActionListener(this);
      
                
                
                        //menu_groupe = new JComboBox(liste_groupe);
        
        //menu_groupe.addActionListener(this);
        
        

        
                try
                {
                        //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");
        
                        //On prépare la requête permettant d'enregistrer les noms des salles existantes dans la table salles
        Statement requeteSQL_salle = connexionSQL.createStatement();
        ResultSet resultat_SQL_salle = requeteSQL_salle.executeQuery("select Nom_salle from salle");
        
        
                
        //On prépare la requête permettant d'enregistrer les noms des groupes existants dans la table groupes
        Statement requeteSQL_groupe = connexionSQL.createStatement();
        ResultSet resultat_SQL_groupe = requeteSQL_groupe.executeQuery("select Nom_groupe from groupe");
        
        
        
 
        
                
                        //Tant qu'il existe des lignes dans la table salle, on ajoute leurs noms dans le combobox menu_salles
        while(resultat_SQL_salle.next())
        {
                         //menu_salle.addItem(resultat_SQL_salle.getString(1));

             EDT_Salle.addItem(resultat_SQL_salle.getString(1));
        }
        
        
   
                        
        
                }catch(Exception e){}
                
                
                        //conteneur_combobox.add(EDT_Salle);
                                             

                        conteneur_combobox.add(BtnCamembert);
                        conteneur_combobox.add(EDT_Salle);

                        fond.add(conteneur_combobox, BorderLayout.NORTH); 

    }
     
    //Si le compte a des droits d'administrateurs
    if(typeCompte == 2)
    {
        
        BtnAjouterValeur.addActionListener(this);
        BtnSupprimerTout.addActionListener(this);
        BtnCamembert.addActionListener(this);

             
        menu_heure_debut = new JComboBox(liste_heure_debut);
        menu_heure_fin = new JComboBox(liste_heure_fin);
        menu_jour = new JComboBox(liste_jour);
        menu_cours = new JComboBox(liste_cours);
        menu_salle = new JComboBox(liste_salle);
        menu_groupe = new JComboBox(liste_groupe);
        EDT_Salle = new JComboBox(liste_EDT_Salle);
        
        menu_groupe.addActionListener(this);
        EDT_Salle.addActionListener(this);
        
            //Ajoute les valeurs dans les menus
            try{

        //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");
        
        //On prépare la requête permettant d'enregistrer les noms des cours existants dans la table cours
        Statement requeteSQL_cours = connexionSQL.createStatement();
        ResultSet resultat_SQL_cours = requeteSQL_cours.executeQuery("select Nom_cours from cours");

        //On prépare la requête permettant d'enregistrer les noms des salles existantes dans la table salles
        Statement requeteSQL_salle = connexionSQL.createStatement();
        ResultSet resultat_SQL_salle = requeteSQL_salle.executeQuery("select Nom_salle from salle");

        //On prépare la requête permettant d'enregistrer les noms des groupes existants dans la table groupes
        Statement requeteSQL_groupe = connexionSQL.createStatement();
        ResultSet resultat_SQL_groupe = requeteSQL_groupe.executeQuery("select Nom_groupe from groupe");



        //Tant qu'il existe des lignes dans la table cours, on ajoute leurs noms dans le combobox menu_cours
         while(resultat_SQL_cours.next())
         {
              menu_cours.addItem(resultat_SQL_cours.getString(1));
         }
  
  
  
        //Tant qu'il existe des lignes dans la table salle, on ajoute leurs noms dans le combobox menu_salles
        while(resultat_SQL_salle.next())
        {
             menu_salle.addItem(resultat_SQL_salle.getString(1));
             EDT_Salle.addItem(resultat_SQL_salle.getString(1));
        }
  
  
       //Tant qu'il existe des lignes dans la table groupe, on ajoute leurs noms dans le combobox menu_groupes
        while(resultat_SQL_groupe.next())
        {
            //On ne souhaite pas afficher le groupe admin dans le menu déroulant car non concerné par les cours
            if(resultat_SQL_groupe.getString(1).equals("Administrateurs"))
            {
                break;
            }
            else
            {
                menu_groupe.addItem(resultat_SQL_groupe.getString(1));
            }
      
        }
  
  

                }catch(Exception e){ System.out.println(e);}
            
            
        //On ajoute les comboxBox d'ajout de cours dans un panel
        conteneur_combobox.add(Info_creer_nvCours);
        conteneur_combobox.add(menu_heure_debut);
        conteneur_combobox.add(menu_heure_fin);
        conteneur_combobox.add(menu_jour);
        conteneur_combobox.add(menu_cours);
        conteneur_combobox.add(menu_salle);
        conteneur_combobox.add(menu_groupe); 
        conteneur_combobox.add(BtnAjouterValeur);
        conteneur_combobox.add(BtnSupprimerTout);
        conteneur_combobox.add(BtnCamembert);
        conteneur_combobox.add(EDT_Salle);
                                      
        
        //On force le panel contenant les combobox à rester en haut de la fenêtre
        fond.add(conteneur_combobox, BorderLayout.NORTH);        
        


    } //Fin if typeCompte == 2
    
    
    
}
     



//Vérifie si la séance à ajouter existe déjà
public boolean CheckSiSeanceExiste(int ligneDebut, int ligneFin, int colonneJour)
{

    //Le retour qui permettra d'indiquer si un cours existe
     boolean existe = false;

     
     //On regarde chaque ligne du tableau concerné par les horaires que nous avons choisi
     for(int i = ligneDebut; i <= ligneFin; i++)
     {
         //S'il y a au moins une cellule d'occupée, on dit que les horaires que nous avons choisi ne sont pas disponibles car il y a déjà une séance
         if(this.grilles.getValueAt(i, colonneJour) != "")
         {
             existe = true;
         }
         
     }
    
    
    
    return existe;
}

     
                   


            
//Met à jour l'affichage du tableau lorsqu'on modifie la table seance
public void updateTableau(int n_ligne, int n_colonne, String nouvelle_valeur)
            {
                
                //On affiche les cours dans les bonnes cases à partir des données reçues
                this.grilles.setValueAt(n_ligne, n_colonne, nouvelle_valeur);
                
                //Réactualise l'affichage de la fenêtre
                fond.updateUI();
            }
            
 




//Méthode faisant appel à une fonction communiquant avec la base SQL
//Fonction appelée par actionPerformed lorsqu'on clique sur "Ajouter un cours"
public void ModifierValeurTableau()
            {
                
          //On déclare des variables qui indiqueront où faut-il modifier la table seance 
          int n_ligne_debut = 0;
          int n_ligne_fin = 0;
          
          int heure_debut = 0;
          int heure_fin = 0;
          
          int n_colonne = 0;


           //On récupère les noms sélectionnés dans les combobox
           valeur_comboBox_heureDebut = String.valueOf(menu_heure_debut.getSelectedItem());
           valeur_comboBox_heureFin = String.valueOf(menu_heure_fin.getSelectedItem());
           valeur_comboBox_jour = String.valueOf(menu_jour.getSelectedItem());
           valeur_comboBox_cours = String.valueOf(menu_cours.getSelectedItem());
           valeur_comboBox_salle = String.valueOf(menu_salle.getSelectedItem());
          
           //On récupère les index des items sélectionnés
           valeur_comboBox_coursIndex = String.valueOf(menu_cours.getSelectedIndex());
           valeur_comboBox_salleIndex = String.valueOf(menu_salle.getSelectedIndex());
           valeur_comboBox_groupeIndex = String.valueOf(menu_groupe.getSelectedIndex());

          
          switch(valeur_comboBox_heureDebut)
          {
                  case "8h":
                        n_ligne_debut = 0;
                        heure_debut = 8;
                        break;
                  
                  case "9h":
                        n_ligne_debut = 1;
                        heure_debut = 9;
                        break;
                  case "10h":
                        n_ligne_debut = 2;
                        heure_debut = 10;
                        break;
                  case "11h":
                        n_ligne_debut = 3;
                        heure_debut = 11;
                        break;
                  case "12h":
                        n_ligne_debut = 4;
                        heure_debut = 12;
                        break;
                  case "13h":
                        n_ligne_debut = 5;
                        heure_debut = 13;
                        break;
                  case "14h":
                        n_ligne_debut = 6;
                        heure_debut = 14;
                        break;
                  case "15h":
                        n_ligne_debut = 7;
                        heure_debut = 15;
                        break;
          }
          
          switch(valeur_comboBox_heureFin)
          {
                  case "9h":
                        n_ligne_fin = 0;
                        heure_fin = 9;
                        break;
                  case "10h":
                        n_ligne_fin = 1;
                        heure_fin = 10;
                        break;
                  case "11h":
                        n_ligne_fin = 2;
                        heure_fin = 11;
                        break;
                  case "12h":
                        n_ligne_fin = 3;
                        heure_fin = 12;
                        break;
                  case "13h":
                        n_ligne_fin = 4;
                        heure_fin = 13;
                        break;
                  case "14h":
                        n_ligne_fin = 5;
                        heure_fin = 14;
                        break;
                  case "15h":
                        n_ligne_fin = 6;
                        heure_fin = 15;
                        break;
                  case "16h":
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
          
          //On vérifie maintenant s'il existe déjà un cours ou non dans le créneau que nous avons choisi avec les commbobox
          boolean SeanceExistante = CheckSiSeanceExiste(n_ligne_debut, n_ligne_fin, n_colonne);
          System.out.println("Seance deja existante dans les horaires choisis : " + SeanceExistante);
                
          //On appelle ensuite la fonction permettant d'ajouter une nouvelle séance ou d'en modifier une selon le retour de SeanceEistante
          majSQL(SeanceExistante, heure_debut, heure_fin, valeur_comboBox_coursIndex, valeur_comboBox_salleIndex, valeur_comboBox_jour, n_ligne_debut, n_colonne);
                
                
                
            }
       
 
//Permet de récupérer le nombre de cours d'une certaine matière
public int getNbreCours(String nom_cours)
{
    int nbre_cours = 0;
    System.out.println("IDGroupe = " + IDGroupe);
    
    //Si c'est un utilisateur admin, on affiche les statistiques de tous les cours de tous les groupes
    if(IDGroupe.equals("3"))
    {
    try
    {
         //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");
        
        
        
         //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLCoursID = connexionSQL.createStatement();
      

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLCoursID=getSQLCoursID.executeQuery("select Id_Cours from cours where Nom_cours = '" + nom_cours + "'");
        
        
        while(resultatgetSQLCoursID.next())
        {
            String ID_Cours = resultatgetSQLCoursID.getString(1);
            
         //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLCours = connexionSQL.createStatement();

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLCours=getSQLCours.executeQuery("select Id_Cours from seance where Id_cours = '" + ID_Cours + "'");
        
   
         while(resultatgetSQLCours.next())
        {
            nbre_cours = nbre_cours + 1;
            
        }
         
         
         
        }
        

       
        
        
    }catch(Exception e){ System.out.println(e);}
    
    }
   
    else
    {
            try
    {
         //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");
        
        
        
         //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLCoursID = connexionSQL.createStatement();
      

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLCoursID=getSQLCoursID.executeQuery("select Id_Cours from cours where Nom_cours = '" + nom_cours + "'");
        
        
        while(resultatgetSQLCoursID.next())
        {
            String ID_Cours = resultatgetSQLCoursID.getString(1);
            
         //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLCours = connexionSQL.createStatement();

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLCours=getSQLCours.executeQuery("select Id_seance from seance where Id_cours = '" + ID_Cours + "'");
        
   
         while(resultatgetSQLCours.next())
        {
           
            
        //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLCours_t = connexionSQL.createStatement();

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLCours_t=getSQLCours_t.executeQuery("select Id_seance from seance_groupe where Id_groupe = '" + IDGroupe + "'");
        
        while(resultatgetSQLCours_t.next())
        {
                    //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement getSQLCours_tt = connexionSQL.createStatement();

        //Execute une requête SQL pour récupérer les ID des séances associés à un ID de groupe
        ResultSet resultatgetSQLCours_tt=getSQLCours_tt.executeQuery("select * from seance where Id_cours = '" + ID_Cours + "' and Id_seance = '" + resultatgetSQLCours_t.getString(1) + "'");
        
        while(resultatgetSQLCours_tt.next())
        {
            String heureDebut = resultatgetSQLCours_tt.getString(4);
            String heureFin = resultatgetSQLCours_tt.getString(5);
            
           int intheureDebut = Integer.parseInt(heureDebut);
           int intheureFin = Integer.parseInt(heureFin);

            nbre_cours = nbre_cours + (intheureFin - intheureDebut);
            
            System.out.println("Calculs pour : " + nom_cours);
                        System.out.println("Fin : " + intheureDebut);
System.out.println("Debut : " + intheureFin);
            
             System.out.println("Difference : " + (intheureFin - intheureDebut));
              System.out.println("");

        }
        
        
        }
            
        }
         
         
         
        }
        

       
        
        
    }catch(Exception e){ System.out.println(e);}
    }
    
    
   
    return nbre_cours;
}

            
//Les méthodes qui intéragissent avec la base SQL
            
//Permet d'ajouter ou de modifier les seances dans la table SQL
public void majSQL(boolean SeanceExiste, int heure_debut, int heure_fin, String id_cours, String id_salle, String jour, int ligne, int colonne)
{
    
    
try{
    
        //On définis les pré-requis pour les communications SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");

        //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement requeteSQL = connexionSQL.createStatement();

        
        //Si aucune séance n'existe dans le créneau que nous avons choisi
        if(SeanceExiste == false)
        {
            
            String maRequete = "insert into seance(Heure_debut, Heure_fin, Id_cours, id_salle, Jour) values('" + heure_debut + "', '" + heure_fin + "', '" + id_cours + "', '" + id_salle + "', '" + jour +"')";
            
            //Permet d'executer la requête : int au lieu de ResultSet car ResultSet sert seulement pour les commandes "SELECT"
            int resultat_SQL=requeteSQL.executeUpdate(maRequete);
    
            //Si le cours dure plus d'une heure (occupe plus d'une case), on parcours toutes les lignes voulus pour y afficher le cours sur plusieurs case
            for(int i = heure_debut; heure_debut < heure_fin; heure_debut++)
            {
          
                switch(heure_debut)
                {
                  case 8:
                    ligne = 0;
                    break;
                  case 9:
                    ligne = 1;
                    break;
                  case 10:
                    ligne = 2;
                    break;
                  case 11:
                    ligne = 3;
                    break;
                  case 12:
                    ligne = 4;
                    break;
                   case 13:
                    ligne = 5;
                    break;
                  case 14:
                    ligne = 6;
                    break;
                  case 15:
                    ligne = 7;
                    break;
                }
              
                //On met à jour l'affichage du tableau
                updateTableau(ligne, colonne, valeur_comboBox_cours + " : " + valeur_comboBox_salle);
              
                //On récupère l'id de la dernière séance crée, donc celle que nous venons de créer
                Statement requeteSQL_ID = connexionSQL.createStatement();
              
                //Requête permettant de récupérer l'id de la séance que nous venons de créer
                String requeteGetSeanceID = "SELECT id_seance FROM seance ORDER BY id_seance DESC LIMIT 0, 1";
              
                //On enregistre cet id de seance afin de l'associer à un id de groupe
                ResultSet rs_SeanceID = requeteSQL_ID.executeQuery(requeteGetSeanceID);

                while(rs_SeanceID.next())
                {
                   //On prépare les communications SQL
                   Statement requeteSQL_IDGroupe_seance = connexionSQL.createStatement();
                   
                   //On prépare la requête ajoutant dans la table seance_groupe, un id_seance avec un id_groupe
                   maRequete = "insert into seance_groupe values('" + rs_SeanceID.getString(1) + "', '" + valeur_comboBox_groupeIndex + "')";
                   
                   //On execute la requete
                   int rs_Groupe_et_seance = requeteSQL_IDGroupe_seance.executeUpdate(maRequete);
                }
              
            } //Fin for
      
}


//Si le creneau est déjà occupée, nous pouvons le modifer
else
{
    
    
    
}



    }catch(Exception e){ System.out.println(e);}
}
   


//Supprime de la table seance_groupe les cours associés au groupe choisi dans le combobox
public void clearTable_Seance_Groupe(String id_groupe)
{

    try {
    
        //On charge les pré-requis pour la communication SQL
        Class.forName("com.mysql.jdbc.Driver");
        Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/projetjava","root","");

        //Initialise une requete qui pourra être executée pour les requêtes SQL
        Statement requeteSQL = connexionSQL.createStatement();

        
        //On cherche d'abord à supprimer les séances de la table seance avant de supprimer les associations id_seance/id_groupe dans la table seance_groupe
        String maRequete = "select Id_seance from seance_groupe where id_groupe = '" + id_groupe + "'";
        ResultSet execute = requeteSQL.executeQuery(maRequete);
        
        while(execute.next())
        {
                    Statement requeteSQL_suppr_seance = connexionSQL.createStatement();
                    String maRequete_suppr_seance = "delete from seance where Id_seance = '" + execute.getString(1) + "'";
                    int execute_suppr_seance = requeteSQL_suppr_seance.executeUpdate(maRequete_suppr_seance);
        }
        
        
        //On prépare la requête qui effacera les id de séances associés à un id de groupe
        maRequete = "delete from seance_groupe where Id_groupe = '" + id_groupe + "'";
    
        //On execute la requête
        int executeSQL = requeteSQL.executeUpdate(maRequete);

          //On vide le tableau d'abord, car sinon les deux emplois du temps fusionneront dans l'affichage
          this.grilles.clearTableau();

    } catch(Exception e) { System.out.println(e); }

}


}



