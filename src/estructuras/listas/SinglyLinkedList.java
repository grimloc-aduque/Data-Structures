/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package	estructuras.listas;

/**
 *
 * @author alejandro
 */
public class SinglyLinkedList <E> {

	// Clase interna
	public static class Node <E> {
		private E element;
		private Node <E> next;
		public Node (E e, Node <E> n){
			element = e;
			next = n;
		}
		public E getElement() {
			return element;
		}
		public Node<E> getNext(){
			return next;
		}
		public void setNext(Node<E> n){
			next = n;
		}
	}

        
	// Atributos
	protected Node<E> head = null;
	protected Node<E> tail = null;
	protected int size = 0;
	
        
	// Metodos acceso
	public SinglyLinkedList(){}

	public int size() {
		return size;
	}

	public boolean isEmpty(){
		return size==0;
	}

	public E first(){
		if(isEmpty())
			return null;
		return head.getElement();
	}

	public E last(){
		if (isEmpty()) 
			return null;
		return tail.getElement();
	}
	
        
        // Métodos Inserción
	public void addFirst(E e){
		head = new Node<> (e, head);
		if(size==0)
			tail= head;
		size ++;
	}

	public void addLast(E e){
		Node<E> newest = new Node<>(e, null);
		if(isEmpty())
			head = newest;
		else
			tail.setNext(newest);
		tail = newest;
		size++;
	}

	public E removeFirst(){
		if(isEmpty())
			return null;
		E answer = head.getElement();
		head = head.getNext();
		size--;
		if(size==0)
			tail=null;
		return answer;
	}

	// Adicional
	public Node<E> getHead(){
		return head;
	}
}
