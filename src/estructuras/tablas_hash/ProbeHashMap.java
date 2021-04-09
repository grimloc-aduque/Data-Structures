
package estructuras.tablas_hash;

import estructuras.colas.Entry;
import estructuras.listas.DoublyLinkedList;

/**
 * @author alejandro
 */

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    // Linear Probing

    protected MapEntry<K, V>[] table;
    private MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
    }

    public ProbeHashMap(int cap) {
        super(cap);
    }

    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    @Override
    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    protected boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    protected int findSlot(int h, K k) {
        int avail = -1;
        int i = h;        
        do {
            if (isAvailable(i)) {
                if (avail == -1)
                    avail = i;
                if (table[i] == null)
                    break;
            } else if (table[i].getKey().equals(k))
                return i;
            i = (i + 1) % capacity;
        } while (i != h);
        return -(avail + 1);
    }
    

    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)
            return null;
        return table[j].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0)
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v);
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)
            return null;
        V answer = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return answer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        DoublyLinkedList<Entry<K, V>> buffer = new DoublyLinkedList<>();
        for (int h = 0; h < capacity; h++) {
            if (!isAvailable(h))
                buffer.add(table[h]);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
