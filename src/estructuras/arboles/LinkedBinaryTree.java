
package estructuras.arboles;

import java.util.Iterator;
import estructuras.colas.LinkedQueue;
import estructuras.listas.DoublyLinkedList;

/**
 * @author alejandro
 */

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {


    protected static class Node<E> implements Position<E>{
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e){
            element = e;
        }
        
        
        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild){
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }

        public E getElement(){
            return element;
        }
        public Node<E> getParent(){
            return parent;
        }
        public Node<E> getLeft(){
            return left;
        }
        public Node<E> getRight(){
            return right;
        }

        public void setElement(E e){
            element = e;
        }
        public void setParent(Node<E> parentNode){
            parent = parentNode;
        }
        public void setLeft(Node<E> leftChild){
            left = leftChild;
        }
        public void setRight(Node<E> rightChild){
            right = rightChild;
        }

    }


    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right){
        return new Node<E>(e, parent, left, right);
    }


    // Atributos
    protected Node<E> root = null;
    private int size = 0;


    public LinkedBinaryTree(){}


    protected Node<E> validate(Position<E> p) throws IllegalArgumentException{
        if(!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if(node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in tree");
        return node;
    }


    // Metodos de Acceso
    public int size(){
        return size;
    }


    public Position<E> root(){
        return root;
    }


    public Position<E> parent(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getParent();
    }


    public Position<E> left(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getLeft();
    }


    public Position<E> right(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getRight();
    }



    // Metodos de Actualizacion
    public Position<E> addRoot(E e)throws IllegalArgumentException{
        if(!isEmpty()) 
            throw new IllegalArgumentException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }


    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if(parent.getRight()!=null)
            throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size ++;
        return child;
    }


    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if(parent.getRight()!=null)
            throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size ++;
        return child;
    }



    // Replace node
    public E set(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }


    // Hace a t1 y t2 los subarboles de p
    public void attach(Position<E> p, LinkedBinaryTree<E> t1,
                        LinkedBinaryTree<E> t2) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(isInternal(p)) 
            throw new IllegalArgumentException("p must be a leaf");
        size += t1.size() + t2.size();
        if((!t1.isEmpty())){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if((!t2.isEmpty())){
            t2.root.setParent(node);
            node.setLeft(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }


    // Elimina Position si solo tiene un hijo
    public E remove(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(numChildren(p)==2)
            throw new IllegalArgumentException("p has two children");
        Node <E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if(child != null)
            child.setParent(node.getParent());
        if(node == root)
            root = child;
        else{
            Node<E> parent = node.getParent();
            if(node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size --;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }
    
    

    protected class LinkedBinaryTreeIterator implements Iterator<E> {
        private DoublyLinkedList<Position<E>> nodes = new DoublyLinkedList<>();

        LinkedBinaryTreeIterator(){
            inorderSubtree(root, nodes);
        }

        @Override
        public boolean hasNext() { 
            return nodes.isEmpty();
        }   

        @Override
        public E next(){
          return nodes.last().getElement();
        }

    } 


    @Override
    public Iterator<E> iterator(){
        return new LinkedBinaryTreeIterator();
    }


    @Override
    public Iterable<Position<E>> positions(){
        DoublyLinkedList<Position<E>> iterable = new DoublyLinkedList<>();
        inorderSubtree(root, iterable);
        return iterable;
    }


    

    @Override
    public String toString(){
        return "PreOrder Representation of Tree\n" + this.toString(root, 0);
    }
    
    
    public String toString(Node<E> node, int indent){
        
        String s = "";
        for(int i=0; i<indent; i++)
            s += "\t"; 

        if(this.isInternal(node)){
            s += node.getElement().toString() + "(\n";  
            indent ++;
            if(node.getLeft() != null)
                s += toString(node.getLeft(), indent); 
            if(node.getRight() != null)
                s += toString(node.getRight(), indent);  
            indent --;
            for(int i=0; i<indent; i++)
                s += "\t"; 
            s += ")\n";
        }
        else{
            if(node.getElement()!=null)
                s += node.getElement().toString() + "\n";
            else
                s += "-> null \n";
        }
            
        
        return s;
    }

    public DoublyLinkedList<Position<E>> inorder(){
        DoublyLinkedList<Position<E>> list = new DoublyLinkedList<>();
        inorderSubtree(root, list);
        return list; 
    }

    private void inorderSubtree(Position<E> p, DoublyLinkedList<Position<E>> snapshot){
        if(left(p) != null)
            inorderSubtree(left(p), snapshot);
        snapshot.add(p);
        if(right(p) != null)
            inorderSubtree(right(p), snapshot);
    }
}
