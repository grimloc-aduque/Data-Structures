
package estructuras;
import estructuras.arboles.LinkedBinaryTree;
import estructuras.colas.Entry;
import estructuras.arboles_busqueda.TreeMap;

/**
 *
 * @author alejandro
 */

public class Estructuras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\nPaquete Estructuras\n");
        testMapas();
        
    }

    public static void testMapas(){
        TreeMap<Integer, Character> tree = new TreeMap<>();
        tree.put(0, 'a');
        tree.put(1, 'b');
        tree.put(3, 'd');
        tree.put(2, 'c');
        tree.put(4, 'e');
        
        
        System.out.printf("Get 0: %s\n", tree.get(0));
        System.out.printf("Remove 3: %s\n", tree.remove(3));
        System.out.printf("Put 0,z: %s\n", tree.put(0,'z'));
        System.out.printf("Put 3,x: %s\n", tree.put(3,'x'));
        System.out.printf("Remove 1: %s\n", tree.remove(1));
        
        System.out.printf("Last Entry: %s\n", tree.lastEntry().getValue());
        System.out.printf("Floor Entry: %s\n", tree.floorEntry(3).getValue());
        System.out.printf("Lower Entry: %s\n", tree.lowerEntry(3).getValue());
        
        
        System.out.println("\nAll entrySet");
        for(Entry<Integer, Character> e: tree.entrySet())
            System.out.printf("Key: %d \tValue: %s\n", e.getKey(), e.getValue());

        
        System.out.println("\nSubmap in range  [1, 4)");
        for(Entry<Integer, Character> e: tree.subMap(1, 4))
            System.out.printf("Key: %d \tValue: %s\n", e.getKey(), e.getValue());
        
    }
    
}
