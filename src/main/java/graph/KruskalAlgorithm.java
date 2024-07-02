package graph;

import java.util.*;

public class KruskalAlgorithm {
    List<Graph.Edge> edges;
    List<Graph.Edge> mstEdges;
    List<Graph.Node> nodes;

    // Constructor
    public KruskalAlgorithm(List<Graph.Edge> edges, List<Graph.Node> nodes) {
        this.edges = new ArrayList<>(edges);
        this.nodes = nodes;

        // Sort edges by weight (parsed from label)
        this.edges.sort(Comparator.comparingInt(edge -> Integer.parseInt(edge.getLabel())));
        this.mstEdges = new ArrayList<>();
    }

    public List<Graph.Edge> computeMST() {
        Map<Graph.Node, Graph.Node> parent = new HashMap<>();
        for (Graph.Node node : nodes) {
            parent.put(node, node);
        }

        for (Graph.Edge edge : edges) {
            Graph.Node root1 = find(parent, edge.getStart());
            Graph.Node root2 = find(parent, edge.getEnd());

            if (root1 != root2) {
                mstEdges.add(edge);
                parent.put(root1, root2);
            }

            if (mstEdges.size() == nodes.size() - 1) {
                break;
            }
        }

        if (mstEdges.size() != nodes.size() - 1) {
            throw new IllegalStateException("Graph is disconnected");
        }

        return mstEdges;
    }

    private Graph.Node find(Map<Graph.Node, Graph.Node> parent, Graph.Node node) {
        if (parent.get(node) != node) {
            parent.put(node, find(parent, parent.get(node)));
        }
        return parent.get(node);
    }
}
