/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author NivineDiallo
 */
public class ModeleTableau extends AbstractTableModel{
    private String jours [] = {"    ","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
    private Object horaires [][]=
    {
        {"8h-9h","","","","","",""},
        {"9h-10h","","","","","",""},
        {"10h-11h","","","","","",""},
        {"11h-12h","","","","","",""},
        {"12h-13h","","","","","",""},
        {"13h-14h","","","","","",""},
        {"14h-15h","","","","","",""},
        {"15h-16h","","","","","",""}
            
    };
    
  
    //Constructeur
    public ModeleTableau(){
        this.jours = jours;
    }
    
    @Override
    public int getColumnCount(){
        return jours.length;
    }
    
    @Override
    public int getRowCount(){
        return horaires.length;
    }

    @Override
    public String getColumnName(int columnIndex){
        return jours[columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       return horaires[rowIndex][columnIndex];
    }
    

    //Permet de modifier une valeur du tableau via les index
    public void setValueAt(int rowIndex, int columnIndex, String nv_valeur)
    {
        horaires[rowIndex][columnIndex] =  nv_valeur;
    }
    
    
    
    //Permet de vider le contenu de l'EDT affiché dans la fenêtre uniquement (ne supprime pas les cours de la BDD)
    public void clearTableau()
    {
                  
        for(int ligne = 0; ligne <= 7; ligne++)
        {
            for(int colonne = 1; colonne <= 6; colonne++)
            {
                this.setValueAt(ligne, colonne, "");            
            }
        }

     }
            
}
