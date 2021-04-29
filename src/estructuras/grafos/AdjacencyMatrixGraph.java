package estructuras.grafos;

import java.util.ArrayList;

import estructuras.listas.DoublyLinkedList;
import estructuras.tablas_hash.ProbeHashMap;

/**
 * @author alejandro
 */
public class AdjacencyMatrixGraph<V, E> implements Graph<V, E> {

    private class InnerVertex<V> implements Vertex<V> {

        private V element;

        public InnerVertex(V elem) {
            element = elem;
        }

        @Override
        public V getElement() {
            return element;
        }
    }

    private class InnerEdge<E> implements Edge<E> {

        private E element;
        private Vertex<V>[] endpoints;

        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        @Override
        public E getElement() {
            return element;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }
    }

    private boolean isDirected;
    private ArrayList<ArrayList<Edge<E>>> edges;
    private ProbeHashMap<Vertex<V>, Integer> vertices;
    private int numV;
    private int numE;

    public AdjacencyMatrixGraph(int n, boolean directed) {
        this.isDirected = directed;
        this.vertices = new ProbeHashMap<>();
        this.numV = n;
        edges = new ArrayList<>(numV);
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                edges.get(i).add(null);
            }
        }
    }

// 	private void setEdge(int u, int v, Edge<E> e) {
// 		edges.get(u).set(v, e);
// 	}
    private Edge<E> getEdge(int u, int v) {
        return edges.get(u).get(v);
    }

    @Override
    public int numVertices() {
        return numV;
    }

    @Override
    public int numEdges() {
        return numE;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices.keySet();
    }

    @Override
    public Iterable<Edge<E>> edges() {
        DoublyLinkedList<Edge<E>> edgesList = new DoublyLinkedList<>();
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                Edge<E> e = edges.get(i).get(j);
                if (e != null) {
                    edgesList.add(e);
                }
            }
        }
        return edgesList;
    }

    @Override
    public int outDegree(Vertex<V> v) throws IllegalArgumentException {
        int num = 0;
        for (int i = 0; i < numV; i++) {
            Edge<E> e = edges.get(vertices.get(v)).get(i);
            if (e != null) {
                num += 1;
            }
        }
        return num;
    }

    @Override
    public int inDegree(Vertex<V> v) throws IllegalArgumentException {
        int num = 0;
        for (int i = 0; i < numV; i++) {
            Edge<E> e = edges.get(vertices.get(v)).get(i);
            if (e != null) {
                num += 1;
            }
        }
        return num;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
        DoublyLinkedList<Edge<E>> outgoing = new DoublyLinkedList<>();
        for (int i = 0; i < numV; i++) {
            Edge<E> e = edges.get(vertices.get(v)).get(i);
            if (e != null) {
                outgoing.add(e);
            }
            ;
        }
        return outgoing;
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
        DoublyLinkedList<Edge<E>> incoming = new DoublyLinkedList<>();
        for (int i = 0; i < numV; i++) {
            Edge<E> e = edges.get(i).get(vertices.get(v));
            if (e != null) {
                incoming.add(e);
            }
            ;
        }
        return incoming;
    }

    @Override
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
        int u_index = vertices.get(u);
        int v_index = vertices.get(v);
        return edges.get(u_index).get(v_index);
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = (InnerEdge<E>) e;
        return edge.getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = (InnerEdge<E>) e;
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to ths edge");
        }
    }

    public ProbeHashMap<V, Vertex<V>> insertVertices(V[] elems) {
        if (elems.length != numV) {
            return null;
        }
        ProbeHashMap<V, Vertex<V>> vMap = new ProbeHashMap<>();
        for (int i = 0; i < numV; i++) {
            InnerVertex<V> v = new InnerVertex<>(elems[i]);
            vertices.put(v, i);
            vMap.put(elems[i], v);
        }
        return vMap;
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
        // TODO Auto-generated method stub

    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        int u_index = vertices.get(u);
        int v_index = vertices.get(v);
        Edge<E> eOld = getEdge(u_index, v_index);
        if (eOld == null) {
            numE += 1;
            InnerEdge<E> e = new InnerEdge<E>(u, v, element);
            edges.get(u_index).set(v_index, e);
            /*
            if(!isDirected){
                edges.get(v_index).set(u_index, e);
            }
*/
            return e;
        }
        return null;
    }

    @Override
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = (InnerEdge<E>) e;
        int u_index = vertices.get(edge.endpoints[0]);
        int v_index = vertices.get(edge.endpoints[1]);
        edges.get(u_index).set(v_index, null);
        numE -= 1;
    }

    // Practica 8
    public void print() {

        System.out.println("Vertices");
        for (Vertex<V> v : vertices.keySet()) {
            System.out.println(v.getElement().toString());
        }

        System.out.println("\nEdges");
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                InnerEdge<E> e = (InnerEdge<E>) edges.get(i).get(j);
                if (e != null) {
                    System.out.printf("%s - %s\n", e.getEndpoints()[0].getElement().toString(),
                            e.getEndpoints()[1].getElement().toString());

                }
            }
        }
    }

}
