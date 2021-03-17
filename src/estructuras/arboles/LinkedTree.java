
package estructuras.arboles;

import java.util.Iterator;
import estructuras.listas.DoublyLinkedList;
import estructuras.colas.LinkedQueue;

/**
 * @author alejandro
 */

public class LinkedTree<E> extends AbstractTree<E>{

    protected static class Node<E> implements Position<E>{
        private E element;
        private Node<E> parent;
        private DoublyLinkedList<Node<E>> children;

        public Node(E e, Node<E> above){
            element = e;
            parent = above;
            children = new DoublyLinkedList<>();
        }

        public E getElement(){
            return element;
        }
        public Node<E> getParent(){
            return parent;
        }
        public DoublyLinkedList<Node<E>> getChildren(){
            return children;
        }

        public void setElement(E e){
            element = e;
        }
        public void setParent(Node<E> parentNode){
            parent = parentNode;
        }
        public void setChildren(DoublyLinkedList<Node<E>> childrenNodes){
            children = childrenNodes;
        }

    }


    protected Node<E> createNode(E e, Node<E> parent){
        return new Node<E>(e, parent);
    }


    // Atributos
    protected Node<E> root = null;
    private int size = 0;


    public LinkedTree(){}


    protected Node<E> validate(Position<E> p) throws IllegalArgumentException{
        if(!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if(node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in tree");
        return node;
    }


    // Metodos de Acceso
    @Override
    public int size(){
        return size;
    }


    @Override
    public Position<E> root(){
        return root;
    }


    @Override
    public Position<E> parent(Position<E> p){
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p){
        Node<E> node = (Node<E>) p;
        return node.getChildren();
    }

    @Override
    public int numChildren(Position<E> p){
        Node<E> node = (Node<E>) p;
        return node.getChildren().size();        
    }


    // Metodos de Actualizacion
    public Position<E> addRoot(E e){
        root = createNode(e, null);
        size = 1;
        return root;
    }


    public Position<E> addChild(Position<E> p, E e) {
        Node<E> parent = validate(p);
        Node<E> child = createNode(e, parent);
        parent.getChildren().add(child);
        size ++;
        return child;
    }



    // Elimina Position si solo tiene un hijo
    public E remove(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        if(node.getChildren().size()>=2)
            throw new IllegalArgumentException("p has more than 1 children");
        
        if(node.getChildren().size()==1){
            Node<E> parent = node.getParent();
            Node<E> child = node.getChildren().removeLast();
            parent.getChildren().add(child);
        } 
        size --;
        E temp = node.getElement();
        node.setElement(null);
        node.setChildren(null);
        node.setParent(node);
        return temp;
    }


    private class LinkedTreeIterator implements Iterator<E> {
        private LinkedQueue<E> nodeQueue = new LinkedQueue<>();

        private void preOrder(Node<E> v){
            nodeQueue.enqueue(v.getElement());
            for(Object o: v.getChildren()){
                Node<E> node = (Node<E>) o;
                preOrder(node);    
            }
        }

        LinkedTreeIterator(){
            preOrder(root);
        }

        @Override
        public boolean hasNext() { 
            return nodeQueue.first()!=null;
        }   

        @Override
        public E next(){
          return nodeQueue.dequeue();
        }

    } 


    @Override
    public Iterator<E> iterator() {
        return new LinkedTreeIterator(); 
    }

    @Override
    public Iterable<Position<E>> positions(){
        DoublyLinkedList<Position<E>> iterable = new DoublyLinkedList<>();
        preOrder(root, iterable);
        return iterable;
    }

    protected void preOrder(Node<E> v, DoublyLinkedList<Position<E>> list){
        list.add(v);
        for(Object o: v.getChildren()){
            Node<E> node = (Node<E>) o;
            preOrder(node, list);    
        }
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
            s += node.getElement().toString() + "(\n";  // Se visita el nodo
            indent ++;
            for(Object o: node.getChildren()){
                Node<E> nodeTemp = (Node) o;
                s += toString(nodeTemp, indent);  // Se llama a preOrder
            }
            indent --;
            for(int i=0; i<indent; i++)
                s += "\t"; 
            s += ")\n";
        }else
            s += node.getElement().toString() + "\n";
        
        return s;
    }
    
}
