
package estructuras.listas;

/**
 * @author alejandro
 */

public class DoublyLinkedList2D<D, B> {

    // Attributes DoublyLinkedList2D
    private DimNode<D, B> header;
    private DimNode<D, B> trailer;
    private int size = 0;

    public DoublyLinkedList2D(){
        header = new DimNode <>(null, null, null);
        trailer = new DimNode <> (null, header, null);
        header.setNextDNode(trailer);
    }

    // Methods DoublyLinkedList2D
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public D first(){
        if(isEmpty())
            return null;
        return header.getNextDNode().getInfo();
    }

    public D last(){
        if(isEmpty()) 
            return null;
        return trailer.getPrevDNode().getInfo();
    }

    public void addFirst(D d){
        addBetween(d,header,header.getNextDNode());
    }

    public void addLast(D d){
        addBetween(d, trailer.getPrevDNode(), trailer);
    }

    public D removeFirst(){
        if(isEmpty())
            return null;
        return remove(header.getNextDNode());
    }

    public D removeLast(){
        if(isEmpty())
            return null;
        return remove(trailer.getNextDNode());
    }

    private void addBetween(D d, DimNode<D, B> predecessor, DimNode<D, B> successor){
        DimNode<D, B> newest = new DimNode<>(d, predecessor, successor);
        predecessor.setNextDNode(newest);
        successor.setPrevDNode(newest);
        size ++;
    }

    public D remove(DimNode<D, B> node){
        DimNode<D, B> predecessor = node.getPrevDNode();
        DimNode<D, B> successor = node.getPrevDNode();
        predecessor.setNextDNode(successor);
        successor.setPrevDNode(predecessor);
        size -- ;
        return node.getInfo();
    }
    
    
    // Metodos Propios   
    public DimNode<D, B> getDimNode(D d){
        DimNode<D, B> current = header.getNextDNode();
        while(current != trailer){
            if ((current.getInfo()).equals(d)){
                return current;
            }
            current = current.getNextDNode();
        }
        return current;
    }

    public DimNode<D, B> getHeader() {
        return header;
    }

    public DimNode<D, B> getTrailer() {
        return trailer;
    }
    
    
    
}
