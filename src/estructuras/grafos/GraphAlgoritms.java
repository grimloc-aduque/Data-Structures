
package estructuras.grafos;

import java.util.Set;
import java.util.HashSet;

import estructuras.arboles_busqueda.Map;
import estructuras.listas.LinkedPositionalList;
import estructuras.listas.PositionalList;
import estructuras.pilas.LinkedStack;
import estructuras.tablas_hash.ProbeHashMap;


/**
* @author alejandro
*/


public class GraphAlgoritms {

    // DFS

    // Iniciar DFS en un vertice
    public static <V,E> void DFS(Graph<V,E> g, Vertex<V> u, Set<Vertex<V>> known, 
            Map<Vertex<V>, Edge<E>> forest, boolean print){
        known.add(u);
        if(print)  System.out.print(u.getElement() + " "); //
        for (Edge<E> e : g.outgoingEdgees(u)) {
            Vertex<V> v = g.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e);
                DFS(g, v, known, forest, print);
            }
        }
    }

    // Path entre dos vertices
    public static <V,E> PositionalList<Edge<E>> 
      constructPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v,
                    Map<Vertex<V>, Edge<E>> forest){

        PositionalList<Edge<E>> path = new LinkedPositionalList<>();
        if (forest.get(v) != null) {
            Vertex<V> walk = v;
            while (walk != u) {
                Edge<E> edge = forest.get(walk);
                path.addFirst(edge);
                walk = g.opposite(walk, edge);
            }
        }
        return path;
    }

    // Componentes Conexos
    public static <V,E> Map <Vertex<V>, Edge<E>> DFSComplete(Graph<V,E> g){
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>, Edge<E>> forest = new ProbeHashMap<>();
        for (Vertex<V> u : g.vertices())
            if (!known.contains(u))
                DFS(g, u, known, forest, false);
        return forest;
    }

    // BFS

    // Iniciar BFS en un vertice
    public static <V,E> void BFS(Graph<V,E> g, Vertex<V> s, Set<Vertex<V>> known, 
            Map<Vertex<V>, Edge<E>> forest, boolean print){
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        known.add(s);
        if(print) System.out.print(s.getElement() + " "); //
        level.addLast(s);
        while (!level.isEmpty()) {
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            for (Vertex<V> u : level)
                for (Edge<E> e : g.outgoingEdgees(u)) {
                    Vertex<V> v = g.opposite(u, e);
                    if (!known.contains(v)) {
                        known.add(v);
                        forest.put(v, e);
                        if(print) System.out.print(v.getElement() + " "); //
                        nextLevel.addLast(v);
                    }
                }
            level = nextLevel;
        }
    }

    // Componentes Conexos
    public static <V, E> Map<Vertex<V>, Edge<E>> BFSComplete(Graph<V, E> g) {
        Map<Vertex<V>, Edge<E>> forest = new ProbeHashMap<>();
        Set<Vertex<V>> known = new HashSet<>();
        for (Vertex<V> u : g.vertices())
            if (!known.contains(u))
                BFS(g, u, known, forest, false);
        return forest;
    }



    // Grafos dirigidos

    // Cierre Transitivo. Floyd - Warshall
    public static <V, E> void transitiveClosure(Graph<V, E> g) {
        for (Vertex<V> k : g.vertices())
            for (Vertex<V> i : g.vertices())
                if (i != k && g.getEdge(i, k) != null)
                    for (Vertex<V> j : g.vertices())
                        if (i != j && j != k && g.getEdge(k, j) != null)
                            if (g.getEdge(i, j) == null)
                                g.insertEdge(i, j, null);
    }

    // Ordenamiento Topologico en DAG
    public static <V,E> PositionalList<Vertex<V>> topologicalSort(Graph<V,E> g){
        PositionalList<Vertex<V>> topo = new LinkedPositionalList<>();
        LinkedStack<Vertex<V>> ready = new LinkedStack<>();
        Map<Vertex<V>, Integer> inCount = new ProbeHashMap<>();

        for(Vertex<V> u: g.vertices()){
            inCount.put(u, g.inDegree(u));
            if(inCount.get(u) == 0)
                ready.push(u);
        }
        while( !ready.isEmpty() ){
            Vertex<V> u = ready.pop();
            topo.addLast(u);
            for(Edge<E> e: g.outgoingEdgees(u)){
                Vertex<V> v = g.opposite(u, e);
                inCount.put(v, inCount.get(v)-1);
                if(inCount.get(v) == 0)
                    ready.push(v);
            }
        }
        return topo;
    }

    // Funciones Propias

    public static <V,E> void minVertexPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v){
        Map<Vertex<V>, Edge<E>> forestBFS = new ProbeHashMap<>();
        BFS(g, u, new HashSet<>(), forestBFS, false);
        LinkedPositionalList<Edge<E>> path;
        path = (LinkedPositionalList<Edge<E>>) constructPath(g, u, v, forestBFS);
        Vertex<V> walk = u;
        for(Edge<E> e: path){
            Vertex<V> next = g.opposite(walk, e);
            System.out.printf("%s -> %s\n", walk.getElement(), next.getElement());
            walk = next;
        }
        System.out.println("\n");
    }

    public static <V,E> void spanningTree(Graph<V,E> g){
        Map<Vertex<V>, Edge<E>> forestBFS = GraphAlgoritms.BFSComplete(g);
        System.out.println("Spanning Tree");
        // System.out.println("Edges");
        for(Edge<E> e: forestBFS.values()){
            Vertex<V> char1 = g.endVertices(e)[0];
            Vertex<V> char2 = g.endVertices(e)[1];
            System.out.printf("%s - %s\n", char1.getElement(), char2.getElement());
        }
    }

    public static <V,E> void DFSAt(Graph<V,E> g, Vertex<V> v){
        DFS(g, v, new HashSet<>(), new ProbeHashMap<>(), true);
        System.out.println("\n");
    }

    public static <V,E> void BFSAt(Graph<V,E> g, Vertex<V> v){
        BFS(g, v, new HashSet<>(), new ProbeHashMap<>(), true);
        System.out.println("\n");
    }
    

}






