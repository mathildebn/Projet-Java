/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author NivineDiallo
 */
public class ModeleTableau extends AbstractTableModel{
    private String jours [] = {"    ","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
    private Object horaires [][]=
    {
        {"8h30-10h","","","","","",""},
        {"10h15-11h45","","","","","",""},
        {"12h-13h30","","","","","",""},
        {"13h45-15h15","","","","","",""},
        {"15h30-17h","","","","","",""},
        {"17h15-18h45","","","","","",""},
        {"19h-20h30","","","","","",""},
        {"20h45-22h15","","","","","",""}
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
            
}
