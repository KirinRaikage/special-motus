import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TestMot {
    public static void main(String[] args) {

        //Mot mot=new Mot("azert");
        //System.out.println(mot.getCombinaison());
        //System.out.println(mot.lettreDansCombi(mot.getCombinaison(),'e'));
        //System.out.println(mot.lettreDansCombi(mot.getCombinaison(), 'i'));
        final long tempsActuel =System.currentTimeMillis();
        //System.out.println(tempsActuel);
        Scanner sc=new Scanner(System.in);
        System.out.println("Veuillez saisir un mot : ");
        String saisie=sc.nextLine();
        final long duree= ((System.currentTimeMillis()-tempsActuel)/1000)/60; //Durée convertie en minutes
        System.out.println("Il s'est écoulé " + duree + " m entre la fin de la saisie.");
        //System.out.println(mot.nbMalPlace("oiret"));1


    }
}
