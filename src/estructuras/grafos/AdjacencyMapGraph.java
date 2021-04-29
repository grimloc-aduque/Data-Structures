
package estructuras.grafos;

import estructuras.arboles.Position;
import estructuras.arboles_busqueda.Map;
import estructuras.listas.LinkedPositionalList;
import estructuras.listas.PositionalList;
import estructuras.tablas_hash.ProbeHashMap;

/**
 * @author alejandro
 */

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {

    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> pos;
        private Map<Vertex<V>, Edge<E>> outgoing, incoming;

        public InnerVertex(V elem, boolean graphIsDirected) {
            element = elem;
            outgoing = new ProbeHashMap<>();
            if (graphIsDirected)
                incoming = new ProbeHashMap<>();
            else
                incoming = outgoing;
        }

        public boolean validate(Graph<V, E> graph) {
            return (AdjacencyMapGraph.this == graph && pos != null);
        }

        @Override
        public V getElement() {
            return element;
        }

        public void setPosition(Position<Vertex<V>> p) {
            pos = p;
        }

        public Position<Vertex<V>> getPosition() {
            return pos;
        }

        public Map<Vertex<V>, Edge<E>> getOutgoing() {
            return outgoing;
        }

        public Map<Vertex<V>, Edge<E>> getIncoming() {
            return incoming;
        }

    }

    private class InnerEdge<E> implements Edge<E> {
        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = (Vertex<V>[]) new Vertex[] { u, v };
        }

        @Override
        public E getElement() {
            return element;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        public boolean validate(Graph<V, E> graph) {
            return (AdjacencyMapGraph.this == graph && pos != null);
        }

        public void setPosition(Position<Edge<E>> p) {
            pos = p;
        }

        public Position<Edge<E>> getPosition() {
            return pos;
        }
    }

    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private PositionalList<Edge<E>> edges = new LinkedPositionalList<>();

    public AdjacencyMapGraph(boolean directed) {
        isDirected = directed;
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    @Override
    public int outDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    @Override
    public int inDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().values();
    }

    @Override
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v);
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v)
            return endpoints[1];
        else if (endpoints[1] == v)
            return endpoints[0];
        else
            throw new IllegalArgumentException("v is not incident to ths edge");
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element, isDirected);
        v.setPosition(vertices.addLast(v));
        return v;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) == null) {
            InnerEdge<E> e = new InnerEdge<>(u, v, element);
            e.setPosition(edges.addLast(e));
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            origin.getOutgoing().put(v, e);
            dest.getIncoming().put(u, e);
            return e;
        } else
            throw new IllegalArgumentException("Edge from u to v exists");
    }

    @Override
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        for (Edge<E> e : vert.getOutgoing().values())
            removeEdge(e);
        for (Edge<E> e : vert.getIncoming().values())
            removeEdge(e);
        vertices.remove(vert.getPosition());
    }

    @Override
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        // Error en la clase del libro
        // InnerVertex<V>[] verts = (InnerVertex<V>[]) edge.getEndpoints();
        // verts[0].getOutgoing().remove(verts[1]);
        // verts[1].getIncoming().remove(verts[0]);
        InnerVertex<V> v0 = validate(edge.getEndpoints()[0]);
        InnerVertex<V> v1 = validate(edge.getEndpoints()[1]);
        v0.getOutgoing().remove(v1);
        v1.getIncoming().remove(v0);
        edges.remove(edge.getPosition());
        edge.setPosition(null);
    }

    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex))
            throw new IllegalArgumentException("Invalid vertex");
        InnerVertex<V> vert = (InnerVertex<V>) v;
        if (!vert.validate(this))
            throw new IllegalArgumentException("Invalid vertex");
        return vert;
    }

    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge))
            throw new IllegalArgumentException("Invalid edge");
        InnerEdge<E> edge = (InnerEdge<E>) e;
        if (!edge.validate(this))
            throw new IllegalArgumentException("Invalid edge");
        return edge;
    }
    
    
    // Practica 8
    public void removeEdge(Vertex<V> u, Vertex<V> v){
        removeEdge(getEdge(u, v));
    }
    
    public ProbeHashMap<V, Vertex<V>> insertVertexList(V elem[]){
        ProbeHashMap<V, Vertex<V>> vertMap = new ProbeHashMap<>();
        for(int i=0; i<elem.length; i++){
            InnerVertex<V> vert = (InnerVertex<V>) insertVertex(elem[i]);
            vertMap.put(elem[i], vert);
        }
        return vertMap; 
    }
    
    public void insertEdgeList(ProbeHashMap<V, Vertex<V>> vertMap, V elem1[], V elem2[], E peso[]){
        InnerVertex<V> v1, v2;
        for(int i=0; i<elem1.length; i++){
            v1 = (InnerVertex<V>) vertMap.get(elem1[i]);
            v2 = (InnerVertex<V>) vertMap.get(elem2[i]);
            if(peso == null)
                this.insertEdge(v1, v2, null); 
            else
                this.insertEdge(v1, v2, peso[i]);
        } 
    }
    

    public void print(){
        System.out.println("Vertices");
        for(Vertex<V> v: vertices){
            System.out.println(v.getElement().toString());
        }
        
        String vert = "-";
        if(isDirected) vert = "->" ;
        
        System.out.println("\nEdges");
        for(Edge<E> e: edges){
            Vertex<V> endVert[] = this.endVertices(e);
            
            System.out.printf("%s %s %s : %d\n", endVert[0].getElement().toString(), 
                    vert, endVert[1].getElement().toString(), (Integer)e.getElement());
        }
    }
    
    
    
}
