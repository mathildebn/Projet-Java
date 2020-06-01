
import java.sql.*;
import java.util.Scanner;
import java.util.*;

/**
 *
 * @author Thanh Kieu
 */


/*
Les lignes obligatoires pour le sql :

Pré rquis :
import java.sql.*;

1 - Charger le driver SQL pour Java : Class.forName("com.mysql.jdbc.Driver");
2 - Définir le serveur, la bdd et l'utilisateur : Connection connexionSQL = DriverManager.getConnection("jdbc:mysql://localhost/nom_bdd","root","");
3 - Pour exécuter une requête :
      Statement requeteSQL = connexionSQL.createStatement();
      ResultSet resultat_SQL=requeteSQL.executeQuery("votre requete ici");

4 - Exploiter les resultats d'une requete :
while(resultat_SQL.next()) //La fonction next parcours chaque ligne des resultats SQL
{
  System.out.println(resultat_SQL.getString(numero de colonne));

  ex : Si on veut afficher la valeur se trouvant à la première colonne, on écrira :
  System.out.println(resultat_SQL.getString(1));
}

*/



class MysqlCon{

public static void main(String args[]){
    
boolean connexion = false;

while(connexion == false)
{

  Scanner scannerIdentifiant = new Scanner(System.in);
  System.out.println("Veuillez saisir votre id :");
  String identifiantText = scannerIdentifiant.nextLine();


  Scanner scannerMDP = new Scanner(System.in);
  System.out.println("Veuillez saisir votre mot de passe :");
  String mdpText = scannerMDP.nextLine();

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
ResultSet resultat_SQL=requeteSQL.executeQuery("select * from utilisateurs");

//On parcours toutes les données SQL reçues
  while(resultat_SQL.next())
  {

    //Si la connexion n'a pas été validée & on compare nos entrées avec les données SQL
    if(connect_success == false)
    {

      //Si nos entrées correspondent aux données SQL (les identifiants correspondent bien)
      if(identifiantText.equals(resultat_SQL.getString(2)) && mdpText.equals(resultat_SQL.getString(3)))
      {
          System.out.println("Bravo!");
          connect_success = true;
          connexion=true;

          //Suite du code
      }

      else
      {
          if (resultat_SQL.next()==false)
              {
                  System.out.println("ERREUR");
                  erreur=true;
                  
              }
      }

    }


    }

    }catch(Exception e){ System.out.println(e);}
}

}
}
