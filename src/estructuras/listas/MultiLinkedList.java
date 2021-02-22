
package estructuras.listas;


/**
 * @author alejandro
 */

public class MultiLinkedList <B, D1, D2> {

    // MultiLinkedList Attributes
    protected DoublyLinkedList2D<D1, B> dim1;
    protected DoublyLinkedList2D<D2, B> dim2;
    
    
    public MultiLinkedList(){
        dim1 = new DoublyLinkedList2D<>();
        dim2 = new DoublyLinkedList2D<>();
    }

    

    // Insertar nodos
    public void insertDim1Node(D1 d1){
        dim1.addFirst(d1);
    }
    public void insertDim2Node(D2 d2){
        dim2.addFirst(d2);
    }    
    public void insertBaseNode(B b, D1 d1, D2 d2){
        
        // Obtengo los nodos dada la referencia a la info
        DimNode<D1, B> dim1Node = dim1.getDimNode(d1);
        DimNode<D2, B> dim2Node = dim2.getDimNode(d2);
        BaseNode<B> baseNode = new BaseNode<>(b, null, null);
        
        // Se comprueba que exista el elemento en cada dimension
        if(dim1Node==null || dim2Node==null)
            return;
        
        // Se anade el nodo base a dimension 1
        if(dim1Node.getFirstBaseNode() == null)
            dim1Node.setFirstBaseNode(baseNode);
        else{
            BaseNode<B> current = dim1Node.getFirstBaseNode();
            while(current.getNext1() != null){
                current = current.getNext1();
            }
            current.setNext1(baseNode);
        }

        // Se anade el nodo base a dimension 2
        if(dim2Node.getFirstBaseNode() == null)
            dim2Node.setFirstBaseNode(baseNode);
        else{
            BaseNode<B> current = dim2Node.getFirstBaseNode();
            while(current.getNext2() != null){
                current = current.getNext2();
            }
            current.setNext2(baseNode);
        }
    }    
    

    // Recuperar nodos
    public DimNode<D1, B> getDim1Node(D1 d){
        return dim1.getDimNode(d);
    }
    
    public DimNode<D2, B> getDim2Node(D2 d){
        return dim2.getDimNode(d);
    }
    
    public BaseNode<B> getBaseNode(B b){
        return findBaseNodeDim1(b);
    }
    
    
    private BaseNode<B> findBaseNodeDim1(B b){
        DimNode<D1, B> dimTemp = dim1.getHeader();
        
        BaseNode<B> baseTemp ;
        
        while(dimTemp != dim1.getTrailer()){
            baseTemp = dimTemp.getFirstBaseNode();
            while(baseTemp != null){
                if(baseTemp.getInfo().equals(b))
                    return baseTemp;
                baseTemp = baseTemp.getNext1();
            }
            dimTemp = dimTemp.getNextDNode();
        }
        return null;
    }
    
    
    
    private BaseNode<B> findBaseNodeDim2(B b){
        DimNode<D2, B> dimTemp = dim2.getHeader();
        
        BaseNode<B> baseTemp ;
        
        while(dimTemp != dim2.getTrailer()){
            baseTemp = dimTemp.getFirstBaseNode();
            while(baseTemp != null){
                if(baseTemp.getInfo().equals(b))
                    return baseTemp;
                baseTemp = baseTemp.getNext2();
            }
            dimTemp = dimTemp.getNextDNode();
        }
        return null;
    }
    
    
    
    
    
    // Metodos conteo
    public int numNodesDim1(D1 d){
        DimNode<D1, B> dimNode = dim1.getDimNode(d);
        BaseNode<B> currentB = dimNode.getFirstBaseNode();
        int count = 0;
        while(currentB != null){
            currentB = currentB.getNext1();
            count ++ ;
        }
        return count;
    }
    
    public int numNodesDim2(D2 d){
        DimNode<D2, B> dimNode = dim2.getDimNode(d);
        BaseNode<B> currentB = dimNode.getFirstBaseNode();
        int count = 0;
        while(currentB != null){
            currentB = currentB.getNext2();
            count ++ ;
        }
        return count;
    }
    
    
    
    
    
    
    
    
    

    // Metodos toString
    @Override
    public String toString(){
        String s = "MultiLinkedList\n\n";
        s += String.format("Dimension 1 -> %s\n", dim1ToString());
        s += String.format("Dimension 2 -> %s\n", dim2ToString());
        return s;
    }


//    No se puede poner los toString dentro de la misma DoublyLinkedList2D
//    porque el metodo getNext1/2 cambia dependiendo de la dimension
    
    // Dim1 toString
    private String dim1ToString(){
        String s = "DoublyLinkedList\n\n";
        DimNode<D1, B> currentD = dim1.getHeader().getNextDNode();
        while(currentD!=dim1.getTrailer()){
            s += dim1NodeToString(currentD);
            currentD = currentD.getNextDNode();
        }
        s += "\n";
        return s;
    }
    
    
    private String dim1NodeToString(DimNode<D1, B> d1Node){
        String s = String.format("%s\n", d1Node);
        BaseNode<B> currentB = d1Node.getFirstBaseNode();
        while(currentB != null){
            s += String.format("\t%s\n", currentB);
            currentB = currentB.getNext1();
        }
        s += "\n";
        return s;
    }
    
    
    public String dim1NodeToString(D1 d){
        return dim1NodeToString(dim1.getDimNode(d));
    }
    
    
    
    // Dim2 toString
    private String dim2ToString(){
        String s = "DoublyLinkedList\n\n";
        DimNode<D2, B> currentD = dim2.getHeader().getNextDNode();
        while(currentD!=dim2.getTrailer()){
            s += dim2NodeToString(currentD);
            currentD = currentD.getNextDNode();
        }
        s += "\n";
        return s;
    }
    
    
    private String dim2NodeToString(DimNode<D2, B> d2Node){
        String s = String.format("%s\n", d2Node);
        BaseNode<B> currentB = d2Node.getFirstBaseNode();
        while(currentB != null){
            s += String.format("\t%s\n", currentB);
            currentB = currentB.getNext2();
        }
        s += "\n";
        return s;
    }
    
    
    public String dim2NodeToString(D2 d){
        return dim2NodeToString(dim2.getDimNode(d));
    }
    
}
