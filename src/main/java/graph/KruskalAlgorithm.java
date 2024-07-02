package graph;

import java.util.*;

public class KruskalAlgorithm {

    private List<Edge> edges;
    private List<Edge> sort_edges;
    private List<Node> nodes;

    public KruskalAlgorithm(Graph graph) {
        this.edges = new ArrayList<>(graph.getEdges());
        this.sort_edges = new ArrayList<>(graph.getEdges());
        // Sort edges by weight
        this.sort_edges.sort(Comparator.comparingInt(edge -> Integer.parseInt(edge.getLabel())));
        this.nodes = new ArrayList<>(graph.getNodes());

        for (Edge edge : this.sort_edges) {
            System.out.println(edge.getLabel() + " " + this.edges.indexOf(edge));
        }
    }

    public ArrayList<Integer> KruskalOST(){
        Map<Integer, Integer> KruskalList = new HashMap<>();
        ArrayList<Integer> KruskalOstList = new ArrayList<>();
        for(Edge edge : this.sort_edges){ //создаём сет всех вершин
            KruskalList.put(this.nodes.indexOf(edge.getStart()), this.nodes.indexOf(edge.getStart()));
            KruskalList.put(this.nodes.indexOf(edge.getEnd()), this.nodes.indexOf(edge.getEnd()));
        }

        if(KruskalList.size() != this.nodes.size() || KruskalList.isEmpty()){
            System.out.println("Граф несвязный!");
            return new ArrayList<>();
        }
//        System.out.println(KruskalList);

        for(Edge edge : this.sort_edges){
            int src = this.nodes.indexOf(edge.getStart());
            int dest = this.nodes.indexOf(edge.getEnd());
            if(!(KruskalList.get(src).equals(KruskalList.get(dest)))){
                Integer max = Math.max(KruskalList.get(src), KruskalList.get(dest));
                Integer min = Math.min(KruskalList.get(src), KruskalList.get(dest));
                for(Map.Entry<Integer, Integer> entry: KruskalList.entrySet()){
                    if(entry.getValue().equals(min)){
                        entry.setValue(max);
                    }
                }
                KruskalOstList.add(this.edges.indexOf(edge));
                if(KruskalOstList.size() == this.nodes.size() - 1){ //Остовное дерево построено, выходим из алгоритма
                    break;
                }
            }

        }

        System.out.println(KruskalOstList);
        System.out.println(KruskalList.toString());
        if(new HashSet<>(KruskalList.values()).size() == 1){
            System.out.println(KruskalOstList);
        } else{
            return new ArrayList<>();
        }
        return KruskalOstList;
    }
}
