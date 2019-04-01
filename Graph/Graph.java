

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 1. make a graph.  you can use any representation.
 * The graph must have at least 10 nodes and 15 edges.  undirected graph.
 * 2. run Dijkstra's algorithm.
 * submit screen shot and the code.
 */

public class Graph {

    int numVertices;//number fo vertex
    int numEdges;//number of edges
    char[] vertexList;
    LinkedList<Edge>[] adjList;//adjacency List
    private boolean[] visited;//List storing visited nodes
    private int[] distList;//List storing distance from starting point
    private PriorityQueue<pqNode> pq;//priority queue for Dijkstra's algorithm


    public class pqNode{
        /**
         * data structure for priority queue
         */
        int dist;
        int node;

        public pqNode(int dist,int node){
            /**
             * constructor for pqNode
             */
            this.dist = dist;
            this.node = node;
        }

        public int getDist() {
            /**
             * return distance
             */
            return dist;
        }

        public int getIdx() {
            /**
             * return index
             */
            return node;
        }
    }

    public class Edge{

        /**
         * Data structure for edge
         */

        char start;
        char end;
        int distance;

        public Edge(char start, char end, int distance){
            /**
             * constructor for Edge
             */
            this.start = start;
            this.end = end;
            this.distance = distance;
        }
        public int getDistance() {
            /**
             * return distance
             */
            return distance;
        }
        public int getStart(){
            /**
             * return start point
             */
            return getIdx(start);
        }
        public int getEnd(){
            /**
             * return end point
             */
            return getIdx(end);
        }
    }

    public Graph(char ... val){
        /**
         * constructor for graph, using adjacency List here
         */
        this.numVertices= val.length;
        this.adjList = new LinkedList[numVertices];

        if(containDup(val)){
            System.out.println("Duplicates in vertex value");
            System.exit(-1);
        }

        this.vertexList = val;
        for(int i = 0;i<numVertices;i++){
            this.adjList[i] = new LinkedList<>();

        }

        this.numEdges = 0;
        this.distList = new int[this.numVertices];

        this.pq = new PriorityQueue<>(numVertices,new Comparator<pqNode>(){
            @Override
            public int compare(pqNode x, pqNode y) {
                int first = x.getDist();
                int second = y.getDist();
                return first - second;
            }
        });

    }

    private boolean containDup(char ... val){
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i = 0;i < val.length;i++){
            map.put(val[i],map.getOrDefault(val[i],0)+1);
        }
        for(char k:map.keySet()){
            if(map.get(k) > 1){
                return true;
            }
        }
        return false;
    }


    private int getIdx(char c){
        for(int i = 0;i<this.numVertices;i++){
            if(this.vertexList[i] == c){
                return i;
            }
        }
        return -1;
    }

    public void makeEdge(char start,char end,int distance){
        /**
         * make an undirected edge between start and end
         */

        Edge e = new Edge(start,end,distance);
        adjList[getIdx(start)].addFirst(e);
        //undirected graph add two edges in opposite direction
        e = new Edge(end,start,distance);
        adjList[getIdx(end)].addFirst(e);
        numEdges++;
    }

    public int getNumEdges(){
        /**
         * return number of edges
         */
        return numEdges;
    }

    public int getNumVertices(){
        /**
         * return number of nodes
         */
        return numVertices;
    }


    private void shortestPath_init(char start){
        /**
         * Initialize data structure for Dijkstra's algorithm.
         */

        visited = new boolean[numVertices];
        for(int i = 0;i < numVertices;i++){
            distList[i] = Integer.MAX_VALUE;
        }
        //distance to starting point is always zero

        try {
            distList[getIdx(start)] = 0;
        }catch(Exception e){
            System.out.println("can not find starting point");
        }

        pqNode s = new pqNode(distList[0],getIdx(start));
        pq.offer(s);

    }

    public void FindshortestPath(char start){
        /**
         * Dijkstra's algorithm
         */

        shortestPath_init(start);
        while(!pq.isEmpty()){
            pqNode nextmin = pq.poll();
            int curridx = nextmin.getIdx();
            if(visited[curridx] == false){
                visited[curridx] = true;
                LinkedList<Edge> adj = adjList[curridx];
                for(int i = 0;i < adj.size();i++){
                    Edge e = adj.get(i);
                    int adjidx = e.getEnd();
                    if(visited[adjidx] == false){
                        int newdist = distList[curridx] + e.getDistance();
                        if(distList[adjidx] > newdist){
                            pqNode newnode = new pqNode(newdist,adjidx);
                            distList[adjidx] = newdist;
                            pq.offer(newnode);
                        }
                    }
                }
            }

        }
        printShortestDistTree(start);
    }
    private void printShortestDistTree(char start){
        /**
         * print result
         */
        for(int i = 0; i < numVertices ; i++){
            if(distList[i] != Integer.MAX_VALUE) {
                System.out.println(start + " to " + this.vertexList[i] + " = " + distList[i]);
            }
        }
    }
    public static void main(String[] args){
        Graph g = new Graph('a','b','c','d','e','f','g','h','i','j','k');

        g.makeEdge('a','b',3);g.makeEdge('c','j',13);
        g.makeEdge('a','c',5);g.makeEdge('c','k',15);
        g.makeEdge('a','g',1);g.makeEdge('d','e',3);
        g.makeEdge('b','c',4);g.makeEdge('d','h',2);
        g.makeEdge('b','f',6);g.makeEdge('e','f',7);
        g.makeEdge('b','g',9);g.makeEdge('f','h',15);
        g.makeEdge('b','d',3);g.makeEdge('f','i',6);
        g.makeEdge('c','f',7);g.makeEdge('h','j',2);

        System.out.println("number of nodes = " + g.getNumVertices());
        System.out.println("number of edges = " + g.getNumEdges());
        g.FindshortestPath('d');

    }
}
