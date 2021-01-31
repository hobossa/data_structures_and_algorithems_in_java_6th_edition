package chapter14;

import chapter06.LinkedStack;
import chapter06.Stack;
import chapter07.LinkedPositionalList;
import chapter07.Position;
import chapter07.PositionalList;
import chapter09.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphAlgorithms {
    /**
     * Performs depth-first search of Graph g starting at Vertex u.
     */
    public static <V, E> void DFS(Graph<V, E> g, Vertex<V> u,
                                  Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest) {
        known.add(u);           // u has been discovered
        for (Edge<E> e : g.outgoingEdges(u)) {
            Vertex<V> v = g.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e);
                DFS(g, v, known, forest);       // recursively explore form v
            }
        }
    }

    /**
     * Returns an ordered list of edges comprising the directed path from u to v.
     */
    public static <V, E> PositionalList<Edge<E>> constructPath(
            Graph<V, E> g, Vertex<V> u, Vertex<V> v, Map<Vertex<V>, Edge<E>> forest) {
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

    /**
     * Performs DFS for the entire graph and returns the DFS forest as a map.
     */
    public static <V, E> Map<Vertex<V>, Edge<E>> DFSComplete(Graph<V, E> g) {
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>, Edge<E>> forest = new HashMap<>();
        for (Vertex<V> u : g.vertices()) {
            if (!known.contains(u)) {
                DFS(g, u, known, forest);
            }
        }
        return forest;
    }

    /**
     * Performs breadth-first search of Graph g starting at Vertex u.
     */
    public static <V, E> void BFS(Graph<V, E> g, Vertex<V> s, Set<Vertex<V>> known,
                                  Map<Vertex<V>, Edge<E>> forest) {
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        known.add(s);
        level.addLast(s);
        while (!level.isEmpty()) {
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            for (Vertex<V> u : level) {
                for (Edge<E> e : g.outgoingEdges(u)) {
                    Vertex<V> v = g.opposite(u, e);
                    if (!known.contains(v)) {
                        known.add(v);
                        forest.put(v, e);
                        nextLevel.addLast(v);
                    }
                }
            }
            level = nextLevel;
        }
    }

    public static <V, E> Map<Vertex<V>, Edge<E>> BFSComplete(Graph<V, E> g) {
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>, Edge<E>> forest = new HashMap<>();
        for (Vertex<V> u : g.vertices()) {
            if (!known.contains(u)) {
                BFS(g, u, known, forest);
            }
        }
        return forest;
    }

    /**
     * Converts graph g into its transitive closure.
     */
    public static <V, E> void transitiveClosure(Graph<V, E> g) {
        for (Vertex<V> k : g.vertices()) {
            for (Vertex<V> i : g.vertices()) {
                // verify that edge (i,k) exists in the partial closure
                if (i != k && g.getEdge(i, k) != null) {
                    for (Vertex<V> j : g.vertices()) {
                        // verify that edge (k,j) exists in the partial closure
                        if (i != j && j != k && g.getEdge(k, j) != null) {
                            // if (i,j) not yet included ,add it to the closure
                            if (g.getEdge(i, j) == null) {
                                g.insertEdge(i, j, null);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a list of vertices of directed acyclic graph g in topological order.
     */
    public static <V, E> PositionalList<Vertex<V>> topologicalSort(Graph<V, E> g) {
        // list of vertices placed in topological order
        PositionalList<Vertex<V>> topo = new LinkedPositionalList<>();
        // container of vertices that have no remaining constraints
        Stack<Vertex<V>> ready = new LinkedStack<>();
        // map keeping track of remaining in-degree for each vertex
        Map<Vertex<V>, Integer> inCount = new HashMap<>();
        for (Vertex<V> u : g.vertices()) {
            inCount.put(u, g.inDegree(u));  //  initialize with actual in-degree
            if (inCount.get(u) == 0) {      // if u has no incoming degrees,
                ready.push(u);              // it is free of constraints
            }
        }
        while (!ready.isEmpty()) {
            Vertex<V> u = ready.pop();
            topo.addLast(u);
            for (Edge<E> e : g.outgoingEdges(u)) {
                // consider all outgoing neighbors of u
                Vertex<V> v = g.opposite(u, e);
                inCount.put(v, inCount.get(v) - 1);   // v has one less constraint with out u
                if (inCount.get(v) == 0) {
                    ready.push(v);
                }
            }
        }
        return topo;
    }


    /**
     * finds a shortest path - Dijkstra's algorithm.
     * we assume that e.getElement() for edge e represents the weight of that edge.
     */
    public static <V> Map<Vertex<V>, Integer> shortestPathLengths(Graph<V, Integer> g,
                                                                  Vertex<V> src) {
        // d.get(v) is upper bound on distance from src to v
        Map<Vertex<V>, Integer> d = new HashMap<>();
        // map reachable v to its d value
        Map<Vertex<V>, Integer> cloud = new HashMap<>();
        // pq will have vertices as elements, with d.get(v) as key
        AdaptablePriorityQueue<Integer, Vertex<V>> pq;
        pq = new HeapAdaptablePriorityQueue<>();
        // maps from vertex to its pq locator
        Map<Vertex<V>, Entry<Integer, Vertex<V>>> pqTokens;
        pqTokens = new HashMap<>();

        // for each vertex v of the graph, add an entry to the priority queue, with
        // the source having distance 0 and all others having infinite distance
        for (Vertex<V> v : g.vertices()) {
            if (v == src) {
                d.put(v, 0);
            } else {
                d.put(v, Integer.MAX_VALUE);
            }
            pqTokens.put(v, pq.insert(d.get(v), v));    // save entry for future updates
        }
        // now begin adding reachable vertices to the cloud
        while (!pq.isEmpty()) {
            Entry<Integer, Vertex<V>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u, key);      // this is actual distance to u
            pqTokens.remove(u);
            for (Edge<Integer> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u, e);
                if (cloud.get(v) == null) {
                    // perform relaxation step on edge(u, v)
                    int wgt = e.getElement();
                    if (d.get(u) + wgt < d.get(v)) {    // better path to v ?
                        d.put(v, d.get(u) + wgt);       // update the distance
                        pq.replaceKey(pqTokens.get(v), d.get(v));   // update the pq entry
                    }
                }
            }
        }
        return cloud;       // this onl includes reachable vertices
    }

    /**
     * Reconstructs a shortest-path tree rooted at vertex s, given distance map d.
     * The tree is represented as a map from each reachable vertex v (other than s)
     * to the edge e = (u,v) that is used to reach v from its parent u in the tree.
     */
    public static <V> Map<Vertex<V>, Edge<Integer>> spTree(
            Graph<V, Integer> g, Vertex<V> s, Map<Vertex<V>, Integer> d) {
        Map<Vertex<V>, Edge<Integer>> tree = new HashMap<>();
        for (Vertex<V> v : d.keySet()) {
            if (v != s) {
                for (Edge<Integer> e : g.incomingEdges(v)) {
                    // consider INCOMING edges
                    Vertex<V> u = g.opposite(v, e);
                    int wgt = e.getElement();
                    if (d.get(v) == d.get(u) + wgt) {
                        tree.put(v, e);     // edge is used to reach v
                    }
                }
            }
        }
        return tree;
    }

    /**
     * Minimum Spanning Trees - Kruskal's algorithm
     * A tree that contains every vertex of a connected graph G is said to be a
     * spanning tree. A spanning tree with smallest total weight is known as the
     * minimum spanning tree (or MST).
     */
    public static <V> PositionalList<Edge<Integer>> MST(Graph<V, Integer> g) {
        // tree is where we will store result as it is computed
        PositionalList<Edge<Integer>> tree = new LinkedPositionalList<>();
        // pq entries are edges of graph, with weights as keys
        PriorityQueue<Integer, Edge<Integer>> pq = new HeapPriorityQueue<>();
        // union-find forest of components of the graph
        Partition<Vertex<V>> forest = new Partition<>();
        // map each vertex to the forest position
        Map<Vertex<V>, Position<Vertex<V>>> positions = new HashMap<>();

        for (Vertex<V> v : g.vertices()) {
            positions.put(v, forest.makeCluster(v));
        }

        for (Edge<Integer> e : g.edges()) {
            pq.insert(e.getElement(), e);
        }

        int size = g.numVertices();
        // while tree not spanning and unprocessed edges remain...
        while (tree.size() != size - 1 && !pq.isEmpty()) {
            Entry<Integer, Edge<Integer>> entry = pq.removeMin();
            Edge<Integer> edge = entry.getValue();
            Vertex<V>[] endpoints = g.endVertices(edge);
            Position<Vertex<V>> a = forest.find(positions.get(endpoints[0]));
            Position<Vertex<V>> b = forest.find(positions.get(endpoints[1]));
            if (a != b) {
                tree.addLast(edge);
                forest.union(a, b);
            }
        }
        return tree;
    }
}
