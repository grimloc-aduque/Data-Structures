
package estructuras.arboles;

import estructuras.colas.Entry;

/**
 * @author alejandro
 */

public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {

    protected class BSTNode<E> extends Node<E> {
        int aux = 0;

        BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
            super(e, parent, leftChild, rightChild);
        }

        public int getAux() {
            return aux;
        }

        public void setAux(int value) {
            aux = value;
        }
    }

    public int getAux(Position<Entry<K, V>> p) {
        return ((BSTNode<Entry<K, V>>) p).getAux();
    }

    public void setAux(Position<Entry<K, V>> p, int value) {
        ((BSTNode<Entry<K, V>>) p).setAux(value);
    }

    @Override
    protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left,
            Node<Entry<K, V>> right) {
        return new BSTNode<>(e, parent, left, right);
    }

    
    /// Relinks a parent node with its oriented child node.
    private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeleftChild) {
        child.setParent(parent);
        if (makeleftChild)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    
    /// Rotates Position p above its parent. 
    public void rotate(Position<Entry<K, V>> p) {
        Node<Entry<K, V>> x = validate(p);
        Node<Entry<K, V>> y = x.getParent();
        Node<Entry<K, V>> z = y.getParent();
        if (z == null) {
            root = x;
            x.setParent(null);
        } else {
            relink(z, x, y == z.getLeft());
        }
        if (x == y.getLeft()) {
            relink(y, x.getRight(), true);
            relink(x, y, false);
        } else {
            relink(y, x.getLeft(), false);
            relink(x, y, true);
        }
    }

    
    // Performs a trinode restructuring of Position x with its parent/grandparent. 
    public Position<Entry<K, V>> reestructure(Position<Entry<K, V>> x) {
        Position<Entry<K, V>> y = parent(x);
        Position<Entry<K, V>> z = parent(y);
        if ((x == right(y)) == (y == right(z))) {
            rotate(y);
            return y;
        } else {
            rotate(x);
            rotate(x);
            return x;
        }
    }

    
    @Override
    public String toString(){
        return super.toString();
    }
    
    
    public void inOrder(){
        for(Object o : super.inorder()){
            Node<Entry<K,V>> node = (Node<Entry<K,V>>) o ;
            if(node.getElement()!=null)
                System.out.println(node.getElement().toString());
        }
    }
    
}
