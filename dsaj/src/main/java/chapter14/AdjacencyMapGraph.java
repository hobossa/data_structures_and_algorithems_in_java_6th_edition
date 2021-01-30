package chapter14;

import chapter07.LinkedPositionalList;
import chapter07.Position;
import chapter07.PositionalList;
import chapter10.Map;
import chapter10.ProbeHashMap;

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {

    // nested InnerVertex and InnerEdge classes defined here...

    /**
     * A vertex of an adjacency map graph representation.
     */
    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> pos;
        private Map<Vertex<V>, Edge<E>> outgoing, incoming;

        public InnerVertex(V element, boolean graphIsDirected) {
            this.element = element;
            outgoing = new ProbeHashMap<>();
            if (graphIsDirected) {
                incoming = new ProbeHashMap<>();
            } else {
                incoming = outgoing;        // if undirected, alias outgoing map
            }
        }

        /**
         * Validates that this vertex instance belongs to the given graph.
         */
        public boolean validate(Graph<V, E> graph) {
            return (AdjacencyMapGraph.this == graph && pos != null);
        }

        @Override
        public V getElement() {
            return element;
        }

        /**
         * Stores the position of this vertex within the graph's vertex list.
         */
        public void setPosition(Position<Vertex<V>> p) {
            this.pos = p;
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


    /**
     * An edge between tow Vertices.
     */
    private class InnerEdge<E> implements Edge<E> {
        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        @SuppressWarnings("unchecked")
        public InnerEdge(Vertex<V> u, Vertex<V> v, E element) {
            this.element = element;
            endpoints = (Vertex<V>[]) new Vertex[]{u, v}; // array of length 2
        }

        /**
         * Validates that this edge instance belongs to the given graph.
         */
        public boolean validate(Graph<V, E> graph) {
            return AdjacencyMapGraph.this == graph && pos != null;
        }

        @Override
        public E getElement() {
            return element;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        public Position<Edge<E>> getPosition() {
            return pos;
        }

        public void setPosition(Position<Edge<E>> pos) {
            this.pos = pos;
        }
    }

    // end of nested InnerVertex and InnerEdge classes

    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private PositionalList<Edge<E>> edges = new LinkedPositionalList<>();

    public AdjacencyMapGraph(boolean isDirected) {
        this.isDirected = isDirected;
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

    /**
     * Returns the edge from u to v, or null if they are not adjacent.
     */
    @Override
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> origin = validate(u);
        return origin.getIncoming().get(v);
    }

    /**
     * Returns the vertices of edge e as an array of length two.
     */
    @Override
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    /**
     * Returns the vertex that is opposite vertex v on edge.
     */
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }


    @Override
    public int outDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    @Override
    public int inDegree(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    /**
     * Returns an iterable collection of edges for which vertex v is the origin.
     */
    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().values();
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> vert = new InnerVertex<>(element, isDirected);
        vert.setPosition(vertices.addLast(vert));
        return vert;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) == null) {
            InnerEdge<E> edge = new InnerEdge<>(u, v, element);
            edge.setPosition(edges.addLast(edge));
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            origin.getOutgoing().put(v, edge);
            dest.getIncoming().put(u, edge);
            return edge;
        } else {
            throw new IllegalArgumentException("Edge from u to v exists");
        }
    }

    @Override
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        // remove all incident edges form the graph
        for (Edge<E> e : vert.getOutgoing().values()) {
            removeEdge(e);
        }
        for (Edge<E> e : vert.getIncoming().values()) {
            removeEdge(e);
        }
        // remove this vertex from the list of vertices
        vertices.remove(vert.getPosition());
        vert.setPosition(null);     // invalidates the vertex
    }

    @Override
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        // remove this edge from vertices' adjacencies
        InnerVertex<V>[] vertices = (InnerVertex<V>[]) edge.getEndpoints();
        vertices[0].getOutgoing().remove(vertices[1]);
        vertices[1].getIncoming().remove(vertices[0]);
        // remove this edge from the list of edges
        edges.remove(edge.getPosition());
        edge.setPosition(null);     // invalidates the edge;
    }

    @SuppressWarnings({"unchecked"})
    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
        InnerVertex<V> vert = (InnerVertex<V>) v;     // safe cast
        if (!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
        return vert;
    }

    @SuppressWarnings({"unchecked"})
    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge)) throw new IllegalArgumentException("Invalid edge");
        InnerEdge<E> edge = (InnerEdge<E>) e;     // safe cast
        if (!edge.validate(this)) throw new IllegalArgumentException("Invalid edge");
        return edge;
    }
}
