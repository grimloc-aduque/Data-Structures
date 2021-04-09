
package estructuras.arboles_busqueda;

import estructuras.colas.Entry;

/**
 * @author alejandro
 */

public interface SortedMap<K,V> extends Map<K,V> {
   Entry<K,V> firstEntry();
   Entry<K,V> lastEntry();
   Entry<K,V> ceilingEntry(K key);
   Entry<K,V> floorEntry(K key);
   Entry<K,V> lowerEntry(K key);
   Entry<K,V> higherEntry(K key);
   Iterable <Entry<K,V>> subMap(K fromKey, K toKey);
}
