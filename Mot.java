import java.util.Random;

public class Mot {
    private String combinaison;

    // Constantes
    private static final int longueur = 5;
    // Liste caract�res
    private static final char[] listeCaracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Mot(String combinaison) {
        this.combinaison = combinaison;
    }

    public Mot() {
        this.chaineAleatoire();
    }

    public String getCombinaison() {
        return combinaison;
    }

    public void setCombinaison(String combinaison) {
        this.combinaison = combinaison;
    }

    public void chaineAleatoire() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longueur; i++) {
            char c = listeCaracteres[r.nextInt(listeCaracteres.length)];
            sb.append(c);
        }
        this.combinaison = sb.toString();
    }
    public boolean lettreDansCombi(String combiClient, char lettre) {
        return combiClient.indexOf(lettre) != -1;
    }


    public int nbBienPlace(String combiClient) {
        int nbLettreBienPlace = 0;
        for (int i = 0; i < combinaison.length(); i++) {
            if (combiClient.charAt(i) == combinaison.charAt(i)) {
                nbLettreBienPlace++;
            }
        }
        return nbLettreBienPlace;
    }

    public int nbMalPlace(String combiClient) {
        int nbLettreMalPlace = 0;
        for (int i = 0; i < combinaison.length(); i++) {

            if (lettreDansCombi(combinaison,combiClient.charAt(i))) {
                if (combiClient.charAt(i) != combinaison.charAt(i) ) {
                    nbLettreMalPlace++;
                }
            }

        }

        return nbLettreMalPlace;
    }

}
