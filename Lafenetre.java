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

/**
 *
 * @author NivineDiallo
 */
public class Lafenetre extends JFrame {
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
        JPanel fond = new JPanel();
        fond.setPreferredSize(new Dimension(1200,850));
        setContentPane(fond);
        fond.setBackground(Color.LIGHT_GRAY);
        fond.setVisible(true);
        
        //Grilles
        //ModeleTableau grilles = new ModeleTableau();
        ModeleTableau grilles = new ModeleTableau();
        JTable table = new JTable(grilles);
        table.setRowHeight(32);

 table.getColumnModel().getColumn(0).setPreferredWidth(150);
        this.getContentPane().add(new JScrollPane(table));
        //this.setVisible(true);
    }
}
