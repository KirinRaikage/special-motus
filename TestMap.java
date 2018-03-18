import java.util.*;

public class TestMap {
    public static <K,V extends Comparable <? super V>> Map<K,V> sortByValue(Map<K,V> map) {
        List<Map.Entry<K,V>> list=new ArrayList<>(map.entrySet());
       //list.sort( (o1, o2) -> o1.getValue().compareTo(o2.getValue()) );

        Map<K,V> result=new LinkedHashMap<>();
        for (Map.Entry<K,V> entry : list) {
            result.put(entry.getKey(),entry.getValue());
        }
        return result;
    }
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Mdr");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<Integer, Integer> listeScores = new TreeMap<Integer, Integer>();
        listeScores.put(1250, 5);
        listeScores.put(1256, 18);
        listeScores.put(783, 23);
        Integer cle=null;
        Integer valeur=null;
        Iterator i = listeScores.keySet().iterator();
        sortByValue(listeScores);
        while (i.hasNext()) {
            cle=(Integer)i.next();
            valeur=(Integer)listeScores.get(cle);
            System.out.println(valeur);
        }
        /*
        if (listeScores.containsKey(1251)) {
            System.out.println("Elle contient la clé !");
        } else {
            System.out.println("Ne contient rien");
        }
        */

    }
}

