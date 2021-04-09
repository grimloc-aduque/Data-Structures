
package estructuras.arboles_busqueda;

import java.util.Comparator;

import estructuras.arboles.BalanceableBinaryTree;
import estructuras.arboles.Position;
import estructuras.colas.Entry;
import estructuras.listas.DoublyLinkedList;

/**
 * @author alejandro
 */

public class TreeMap<K,V> extends AbstractSortedMap<K,V> {

    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    public TreeMap(){
        super();
        tree.addRoot(null);
    }

    public TreeMap(Comparator<K> comp){
        super(comp);
        tree.addRoot(null);
    }

    @Override
    public int size(){
        return (tree.size()-1)/2;
    }

    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry){
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    protected Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key){
        if(isExternal(p))
            return p;
        int comp = compare(key, p.getElement());
        if(comp == 0)
            return p;
        else if(comp < 0)
            return treeSearch(left(p), key);
        else
            return treeSearch(right(p), key);
    }

    @Override
    public V get(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);
        if(isExternal(p)) return null;
        return p.getElement().getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException{
        checkKey(key);
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)){
            expandExternal(p, newEntry);
            rebalanceInsert(p);
            return null;
        }else{
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);
            return old;
        }
    }
    
    @Override
    public V remove(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)){
            rebalanceAccess(p);
            return null;
        }else{
            V old = p.getElement().getValue();
            if(isInternal(left(p)) && isInternal(right(p))){
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }
            Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K,V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }

    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p){
        Position <Entry<K,V>> walk = p;
        while(isInternal(walk))
            walk = right(walk);
        return parent(walk);
    }

    @Override
    public Entry<K,V> lastEntry(){
        if(isEmpty()) return null;
        return treeMax(root()).getElement();
    }

    @Override
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException{
        checkKey(key);
        Position <Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p)) return p.getElement();
        while(!isRoot(p)){
            if(p == right(parent(p)))
                return parent(p).getElement();
            else
                p = parent(p);
        }
       return null; 
    }

    @Override
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException{
        checkKey(key);
        Position <Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p) && isInternal(left(p)))
            return treeMax(left(p)).getElement();
        while(!isRoot(p)){
            if(p == right(parent(p)))
                return parent(p).getElement();
            else
                p = parent(p);
        }
       return null; 
    }

    @Override
    public Iterable <Entry<K,V>> entrySet(){
        DoublyLinkedList<Entry<K,V>> buffer = new DoublyLinkedList<>();
        for(Object o: tree.inorder()){
            Position<Entry<K,V>> p = (Position<Entry<K,V>>) o;
            if(isInternal(p)) buffer.add(p.getElement());
        }
        return buffer;
    }

    @Override
    public Iterable <Entry<K,V>> subMap(K fromKey, K toKey){
        DoublyLinkedList<Entry<K,V>> buffer = new DoublyLinkedList<>();
        if(compare(fromKey, toKey) < 0)
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    public void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p, 
            DoublyLinkedList<Entry<K,V>> buffer){
        if(isInternal(p))
            if(compare(p.getElement(), fromKey) < 0)
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else{
                subMapRecurse(fromKey, toKey, left(p), buffer);
                if(compare(p.getElement(), toKey) < 0){
                    buffer.add(p.getElement());
                    subMapRecurse(fromKey, toKey, right(p), buffer);
                }
            }
    }



      // Some notational shorthands for brevity 
      protected Position<Entry<K,V>> root() { return tree.root(); }
      protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) { return tree.parent(p); }
      protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) { return tree.left(p); }
      protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) { return tree.right(p); }
      protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) { return tree.sibling(p); }
      protected boolean isRoot(Position<Entry<K,V>> p) { return tree.isRoot(p); }
      protected boolean isExternal(Position<Entry<K,V>> p) { return tree.isExternal(p); }
      protected boolean isInternal(Position<Entry<K,V>> p) { return tree.isInternal(p); }
      protected void set(Position<Entry<K,V>> p, Entry<K,V> e) { tree.set(p, e); }
      protected Entry<K,V> remove(Position<Entry<K,V>> p) { return tree.remove(p); }
      protected void rotate(Position<Entry<K,V>> p) { tree.rotate(p); }

    @Override
    public Entry<K, V> firstEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected void rebalanceInsert(Position <Entry<K,V>> p){ }
    protected void rebalanceDelete(Position <Entry<K,V>> p){ }
    protected void rebalanceAccess(Position <Entry<K,V>> p){ }

    
    @Override
    public String toString(){
        return tree.toString();
    }
    

    public void inOrder(){
        tree.inOrder();
    }

}

