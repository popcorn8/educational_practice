package graph;

import java.util.*;

public class KruskalAlgorithm {

    private List<Edge> edges;
    private List<Edge> sort_edges;
    private List<Node> nodes;

    public KruskalAlgorithm(Graph graph) {
        this.edges = new ArrayList<>(graph.getEdges());
        this.sort_edges = new ArrayList<>(graph.getEdges());;
        // Sort edges by weight
        this.sort_edges.sort(Comparator.comparingInt(edge -> Integer.parseInt(edge.getLabel())));
        this.nodes = new ArrayList<>(graph.getNodes());;


//        for (Edge edge : this.edges) {
//            System.out.println(edge + " " + this.edges.indexOf(edge));
//        }

        System.out.println();

        for (Edge edge : this.sort_edges) {
            System.out.println(edge + " " + this.edges.indexOf(edge));
        }


    }

    public void KruskalOST(){
        Map<Integer, Integer> KruskalList = new HashMap<>();
        ArrayList<Integer> KruskalOstList = new ArrayList<>();
        for(Edge edge : this.sort_edges){ //создаём сет всех вершин
            KruskalList.put(this.nodes.indexOf(edge.getStart()), this.nodes.indexOf(edge.getStart()));
            KruskalList.put(this.nodes.indexOf(edge.getEnd()), this.nodes.indexOf(edge.getEnd()));
        }

        if(KruskalList.size() != this.nodes.size() || KruskalList.isEmpty()){
            throw new IllegalStateException("Граф несвязный");
        }
        System.out.println(KruskalList);

        for(Edge edge : this.sort_edges){
//            System.out.println(edge);
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
//                System.out.println(edge + " in alg");
                if(KruskalOstList.size() == this.nodes.size() - 1){ //Остовное дерево построено, выходим из алгоритма
                    break;
                }

//                // Check if all keys in KruskalList have the same value
//                if (new HashSet<>(KruskalOstList).size() == 1) {
//                    break;
//                }

            }

        }

        System.out.println(KruskalOstList);
        System.out.println(KruskalList.toString());
//        System.out.println(KruskalList.values());
        if(new HashSet<>(KruskalList.values()).size() == 1){
//            System.out.println(KruskalList);
//            System.out.println();
            System.out.println(KruskalOstList);
        } else{
            throw new IllegalStateException("Граф несвязный");
        }

    }
}
