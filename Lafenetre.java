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
import java.util.Scanner;



/**
 *
 * @author NivineDiallo
 */
public class Lafenetre extends JFrame implements ActionListener {
    
    ModeleTableau grilles = new ModeleTableau();
    JButton BtnAjouterValeur = new JButton("Ajouter une valeur");
    JTable table = new JTable(grilles);
    JPanel fond = new JPanel();
    
    //constructeur
    public Lafenetre(){
        setSize (1200,850); //donnne al taille hauteur + largeur de la fenetre
        setTitle ("Titre"); //donne le titre de la fenêtre
        setVisible(true);
    }
    
    public Lafenetre(int longueur, int largeur, String titre){
        setSize (longueur,largeur); //donnne al taille hauteur + largeur de la fenetre
        setTitle (titre); //donne le titre de la fenêtre
        setVisible(true);
        
        //Fond
        //JPanel fond = new JPanel();
        fond.setPreferredSize(new Dimension(1200,850));
        setContentPane(fond);
        fond.setBackground(Color.LIGHT_GRAY);
        fond.setVisible(true);
        
        
        //Grilles
        //ModeleTableau grilles = new ModeleTableau();
        //JTable table = new JTable(grilles);
        table.setRowHeight(32);
        

 table.getColumnModel().getColumn(0).setPreferredWidth(150);
        this.getContentPane().add(new JScrollPane(table));
        //this.setVisible(true);
        
        //JButton BtnAjouterValeur = new JButton("Ajouter une valeur");
        BtnAjouterValeur.addActionListener(this);
        fond.add(BtnAjouterValeur);
        
        
        
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
                
                  
                this.grilles.setValueAt(n_ligne, n_colonne, nouvelle_valeur);
                System.out.println(this.grilles.getValueAt(n_ligne, n_colonne));
                
                //Permet de réactualiser l'affichage de la fenêtre
                fond.updateUI();
            }
            
}
