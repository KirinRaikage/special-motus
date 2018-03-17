public class TestMot {
    public static void main(String[] args) {
        Mot mot=new Mot("azert");
        System.out.println(mot.getCombinaison());
        //System.out.println(mot.lettreDansCombi(mot.getCombinaison(),'e'));
        //System.out.println(mot.lettreDansCombi(mot.getCombinaison(), 'i'));
        System.out.println(mot.nbMalPlace("oiret"));

    }
}
