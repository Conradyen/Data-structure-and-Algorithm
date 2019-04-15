
import java.util.LinkedList;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;

public class TopologicalSort {
    /**
     * Write program to do Topological sort (DFS).
     *
     * your graph must have at lease 10 nodes and 15 edges.
     * It must have multiple edges going in and also coming out from some of the edges.
     *
     * 1. Run the program on a graph with no cycles.
     * Your print out would show all the edges of the graph.
     * Then it will show the list of vertices in the topological order.
     *
     * 2. Run the program on a graph that has a cycle.
     * Your program catches the cycle and print the list of edges that form the cycle.
     *
     * Submit code and screen shots of the execution.
     */

    int numVertices;//number fo vertex
    int numEdges;//number of edges
    char[] vertexList;
    LinkedList<Edge>[] adjList;//adjacency List
    private boolean[] visited;//List storing visited nodes
    private HashSet<Integer> revisited;
    private Stack<Integer> stack;
    boolean cycle = false;

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

    public TopologicalSort(char ... val){
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

    }

    public void TPSort_init(){

        /**
         * set up variables for topological sort
         */
        visited = new boolean[numVertices];
        for(int i = 0;i < numVertices;i++){
            visited[i] = false;
        }
        stack = new Stack();
        revisited = new HashSet<>();
    }

    public void _TPSort(int idx){
        /**
         * topological sort helper function
         */

        LinkedList<Edge> tempList = adjList[idx];
        revisited.add(idx);
        visited[idx] = true;
        for(int i = 0;i < tempList.size();i++){
            Edge e = tempList.get(i);
            int adjidx = e.getEnd();

            if(visited[adjidx] == false){
                _TPSort(adjidx);
            }
            if(revisited.contains(adjidx)){
                printCycle();
                cycle = true;
                break;
            }
        }
        revisited.remove(idx);
        stack.push(idx);
    }

    public void printEdges(){
        /**
         * print all edges
         */
        System.out.println("Edges :");
        int count = 0;
        for(int i = 0;i < numVertices;i++){
            LinkedList<Edge> tempList = adjList[i];
            if(tempList.size() != 0) {
                for (Edge j : tempList) {
                    System.out.print(getChar(j.getStart()) + " -> " + getChar(j.getEnd())+" ");
                    count++;
                    if((count != 0) && (count %3 == 0)){
                        System.out.println();
                    }else{
                        System.out.print(" , ");
                    }
                }
            }

        }
        System.out.println();
    }

    public void TPSort(){
        /**
         * do topological sort
         */

        TPSort_init();
        for(int i = 0;i < numVertices;i++){
            if(visited[i] == false){
                _TPSort(i);
            }
        }
    }

    public void printCycle(){
        /**
         * print edges forms the cycle
         */
        System.out.println("contain cycle :");
        int prev = -1;
        int count = 0;
        for(int c:revisited){
            if(prev == -1){
                prev = c;
            }else {
                System.out.print(getChar(prev)+ " -> " + getChar(c) + " ");
                prev = c;
                count++;
                if((count != 0) && (count %3 == 0)){
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    public void printSort(){
        /**
         * print topological order of graph
         */
        if(cycle != true) {
            while (!stack.empty()) {
                System.out.print(getChar(stack.pop()) + " ");
            }
            System.out.println();
        }else{
            System.out.println("contain cycle");
        }
    }

    private char getChar(int i){
        /**
         * return char of the index
         */
        return vertexList[i];
    }

    private int getIdx(char c){
        /**
         * return index of the character
         */
        for(int i = 0;i<this.numVertices;i++){
            if(this.vertexList[i] == c){
                return i;
            }
        }
        return -1;
    }

    private boolean containDup(char ... val){
        /**
         * check if input contain duplicate vertex
         */
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

    public void makeEdge(char start,char end,int distance){
        /**
         * make an undirected edge between start and end
         */

        Edge e = new Edge(start,end,distance);
        adjList[getIdx(start)].addFirst(e);
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

    public static void main(String args[]){

        //graph with no cycle
        TopologicalSort g = new TopologicalSort('a','b','c','d','e','f','g','h','i','j','k');

        g.makeEdge('a','b',3);g.makeEdge('c','j',13);
        g.makeEdge('a','c',5);g.makeEdge('c','k',15);
        g.makeEdge('a','g',1);g.makeEdge('d','e',3);
        g.makeEdge('b','c',4);g.makeEdge('d','h',2);
        g.makeEdge('b','f',6);g.makeEdge('e','f',7);
        g.makeEdge('b','g',9);g.makeEdge('f','h',15);
        g.makeEdge('b','d',3);g.makeEdge('f','i',6);
        g.makeEdge('c','f',7);g.makeEdge('h','j',2);
        System.out.println("number of node : " + g.getNumEdges());
        System.out.println("number of edges : " + g.getNumVertices());
        g.printEdges();
        g.TPSort();
        g.printSort();

        //make graph with cycle
        TopologicalSort g_cycle = new TopologicalSort('a','b','c','d','e','f','g','h','i','j','k');

        g_cycle.makeEdge('a','h',3);g_cycle.makeEdge('c','a',13);
        g_cycle.makeEdge('a','b',5);g_cycle.makeEdge('c','k',15);
        g_cycle.makeEdge('a','g',1);g_cycle.makeEdge('d','e',3);
        g_cycle.makeEdge('b','c',4);g_cycle.makeEdge('d','h',2);
        g_cycle.makeEdge('b','f',6);g_cycle.makeEdge('e','f',7);
        g_cycle.makeEdge('b','g',9);g_cycle.makeEdge('f','h',15);
        g_cycle.makeEdge('b','d',3);g_cycle.makeEdge('f','i',6);
        g_cycle.makeEdge('c','f',7);g_cycle.makeEdge('h','j',2);

        System.out.println("number of node : " + g_cycle.getNumEdges());
        System.out.println("number of edges : " + g_cycle.getNumVertices());
        g_cycle.printEdges();
        g_cycle.TPSort();
        g_cycle.printSort();
    }

}
