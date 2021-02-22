/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package	estructuras.pilas;

import estructuras.listas.SinglyLinkedList;

/**
 *
 * @author alejandro
 */
public class LinkedStack<E> {
	SinglyLinkedList<E> list = new SinglyLinkedList<>();

	public int size(){
		return list.size();
	}

	public boolean isEmpty(){
		return list.isEmpty();
	}

	public void push(E elem){
		list.addFirst(elem);
	}

	public E top() {
		return list.first();
	}

	public E pop(){
		return list.removeFirst();
	}

}
