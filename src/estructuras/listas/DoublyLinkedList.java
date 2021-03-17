
package estructuras.listas;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author alejandro
 * @param <E>
 */

public class DoublyLinkedList<E> implements Iterable{
    public static class Node<E>{
        private E element;
        private Node <E> prev;
        private Node <E> next;
        public Node(E e, Node<E> p, Node<E> n){
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }  
        
        public String toString(){
            return String.format("Node. Element:%s", element);
        }
    }
    
    private Node <E> header;
    private Node <E> trailer;
    private int size = 0;
    
    public DoublyLinkedList(){
        header = new Node <>(null, null, null);
        trailer = new Node <> (null, header, null);
        header.setNext(trailer);
        
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public E first(){
        if(isEmpty())
            return null;
        return header.getNext().getElement();
    }

    public E last(){
        if(isEmpty()) 
            return null;
        return trailer.getPrev().getElement();
    }

    public void addFirst(E e){
        addBetween(e,header,header.getNext());
    }

    public void addLast(E e){
        addBetween(e, trailer.getPrev(), trailer);
    }
    
    public void add(E e){
        addLast(e);
    }

    public E removeFirst(){
        if(isEmpty())
            return null;
        return remove(header.getNext());
    }

    public E removeLast(){
        if(isEmpty())
            return null;
        return remove(trailer.getPrev());
    }
    
    private void addBetween(E e, Node<E> predecessor, Node<E> successor){
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size ++;
    }

    public E remove(Node<E> node){
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size -- ;
        return node.getElement();

    }
    
    
    
    // Metodos propios   
    
    public Node<E> getHeader(){
        return header;
    }
    
    public Node<E> getTrailer(){
        return trailer;
    }

    public Node<E> getNode(E e){
        Node<E> current = header.getNext();
        while(current != trailer){
            if ((current.getElement()).equals(e)){
                return current;
            }
            current = current.getNext();
        }
        return current;
    }
    

    
    private class LinkedListIterator implements Iterator<E> {
        private Node<E> iterator = header.getNext();                   

        @Override
        public boolean hasNext() { 
            return iterator.getNext()!=null; 
        }   

        @Override
        public E next() throws NoSuchElementException {
          if (iterator.getNext()==null)
              throw new NoSuchElementException("No next element");
          E temp = iterator.getElement();
          iterator = iterator.getNext();
          return temp;
        }
    } 
    
    @Override
    public Iterator<E> iterator(){
        return new LinkedListIterator();
    }
    
    
    public E get(int i){
        Node<E> temp = header;
        for(int j =0; j<=i; j++)
            temp = temp.getNext();
        
        return temp.getElement();
    }
    
    public E getNext(E elem){
        Node<E> temp = header;
        for(int i =0; i<size; i++){
            temp = temp.getNext();
            if(elem == temp.getElement())
                return temp.getNext().getElement();
        }
        return null;
    }
    
    

    
    
    @Override
    public String toString(){
        String s = "";
        Node<E> current = header.getNext();
        while(current!=trailer){
            s += current.getElement().toString();
            current = current.getNext();
        }
        return s;
        
    }
}
