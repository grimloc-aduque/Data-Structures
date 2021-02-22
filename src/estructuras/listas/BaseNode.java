
package estructuras.listas;

/**
 * @author alejandro
 */

public class BaseNode<B> {

    // Attributes BaseNode
    private B info;
    private BaseNode<B> next1;
    private BaseNode<B>  next2;


    public BaseNode(B b, BaseNode<B> n1, BaseNode<B> n2){
     info = b;
     next1 = n1;
     next2 = n2;
    }

    // Setters BaseNode
    public void setInfo(B info) {
        this.info = info;
    }
    public void setNext1(BaseNode<B> next1) {
        this.next1 = next1;
    }
    public void setNext2(BaseNode<B> next2) {
        this.next2 = next2;
    }

    // Getters BaseNode
    public B getInfo() {
        return info;
    }
    public BaseNode<B> getNext1() {
        return next1;
    }
    public BaseNode<B> getNext2() {
        return next2;
    }
    
    public String toString(){
        //return String.format("BaseNode ->  %s", info);
        return info.toString();
    }
}
