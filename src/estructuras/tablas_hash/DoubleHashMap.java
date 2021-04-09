
package estructuras.tablas_hash;

/**
 * @author alejandro
 */

public class DoubleHashMap<K, V> extends ProbeHashMap<K, V>{
    
    
    @Override
    protected int findSlot(int h, K k){
        int avail = -1;
        int j = 0;
        int d = doubleHash(k);
        int i = h;
        do {
            if (isAvailable(i)) {
                if (avail == -1)
                    avail = i;
                if (table[i] == null)
                    break;
            } else if (table[i].getKey().equals(k))
                return i;
            j ++;
            i = (h + j*d) % capacity;            
        } while (i != h);
        return -(avail + 1);
    }
    
    private int doubleHash(K k){
        return 7 - Math.abs(k.hashCode())%7;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
