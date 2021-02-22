
package estructuras.listas;

/**
 * @author alejandro
 */

public class DimNode<D, B> {
    // Attributes DNode
    private D info;
    private DimNode <D, B> prevDNode;
    private DimNode <D, B> nextDNode;
    private BaseNode <B> firstBaseNode;

    // Constructor DNode
    public DimNode(D d, DimNode<D, B> p, DimNode<D, B> n){
        info = d;
        prevDNode = p;
        nextDNode = n;
        firstBaseNode = null;
    }

    // Setters DNode
    public void setInfo(D info) {
        this.info = info;
    }
    public void setPrevDNode(DimNode<D, B> prevDNode) {
        this.prevDNode = prevDNode;
    }
    public void setNextDNode(DimNode<D, B> nextDNode) {
        this.nextDNode = nextDNode;
    }
    public void setFirstBaseNode(BaseNode<B> firstBaseNode) {
        this.firstBaseNode = firstBaseNode;
    }

    // Getters DNode
    public D getInfo() {
        return info;
    }
    public DimNode<D, B> getPrevDNode() {
        return prevDNode;
    }
    public DimNode<D, B> getNextDNode() {
        return nextDNode;
    }
    public BaseNode<B> getFirstBaseNode() {
        return firstBaseNode;
    }           

    public String toString(){
        //return String.format("DimNode -> %s", info);
        return info.toString();
    }
}
