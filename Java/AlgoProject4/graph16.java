import java.util.*;
import java.io.*;
/**
 *
 * @author josephtleiferman
 */
class graph16 {
	
    public class vertex {
        public int index;           // each vertex has an integer index number
        public int x;               // the x-position of the vertex
        public int y;               // the y-position of the vertex

        public boolean wasVisited; // used by search
        public int nextNeighbor;   // used by search
        public int parent;         // used by search

        public vertex(int i, int x, int y) {  // constructor
           index = i;
           this.x = x;
           this.y = y;
           wasVisited = false;
           nextNeighbor = 0;
           parent = -1;
        }

        public void reset() {
            wasVisited = false;
            nextNeighbor = 0;
            parent = -1;
        }
        public void resetDiscover() {
            wasVisited = false;
        }
        @Override 
        public boolean equals(Object o) {
            vertex other = (vertex) o;
            return this.index == other.index;
        }
        // for easier debugging
        @Override 
        public String toString() {
            return "[" + index + "]" + "(" + x + "," + y + ")";
        }
    } // end of class vertex
    // will hold all the undirected edges of adjMat
    class Edge {
        int u;
        int v;
        boolean visited;
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
            visited = false;
            
        }
        // return other end point 
        public int other(int o) {
            if(u==o) return v;
            if(v==0) return u;
            else return -1;
        }
        
        @Override 
        public boolean equals(Object o) {
            Edge other = (Edge) o;
            if(this.u == other.u && this.v == other.v) {
                return true;
            }
            else if (this.u == other.v && this.v == other.u) {
                return true;
            }
            else return false;
        }
        @Override 
        public String toString() {
            return "[" + u + "," + v + "] "  + visited;
        }
    }
    
    private int V;                 // number of vertices
    private vertex vertexList[];   // list of vertices
    private float adjMat[][];      // adjacency matrix
    public ArrayList<Integer> Etour = new ArrayList<>(); // hold EulerTour of MST
    
    public graph16()  {                   // constructor
	    this(20);
    }

    public graph16(int n)  {             // constructor
        V = n;

        vertexList = new vertex[V];                                          
        adjMat = new float[V][V];  // adjacency matrix	    
        
        Random randomGenerator = new Random();
        for(int j = 0; j < V; j++) {      // fill VertexList
            int x = randomGenerator.nextInt(20*V);
            int y = randomGenerator.nextInt(20*V);
            vertexList[j] = new vertex(j, x, y);
        }
        
        for(int j=0; j<V; j++) {     // set adjacency matrix to distance
            vertex v1 = vertexList[j];
            for(int k=j; k<V; k++)  { 
                if (k == j) adjMat[j][k] = 0;
                else {
                   vertex v2 = vertexList[k];
                   float dx = (v1.x-v2.x);
                   float dy = (v1.y-v2.y);
                   adjMat[j][k] = (float) Math.sqrt(dx*dx + dy*dy); 
                   adjMat[k][j] = adjMat[j][k];
                    }
            }
        }
        // finding MST///////////////////////////////
        PrimJarnikMST();
        ArrayList<Integer[]> MST = new ArrayList<>();
        System.out.println("MST Edges: ");
        for (int i = 0; i < V; i++) {
                vertex v1 = vertexList[i];
                int j = v1.parent;
                if (j != -1)
                    MST.add(new Integer[] {j, i});
                    
	}
        final Comparator<Integer[]> listComparator = new Comparator<Integer[]>() {
            
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[0].compareTo(o2[0]);
            }
            
        };
        Collections.sort(MST,listComparator);
        System.out.println(Arrays.deepToString(MST.toArray()));
        
///////// // find euler cycle from MST ///////////////////////////////////
        
        System.out.println("Euler Traversal of MST: ");
        ArrayList<Edge> edges = new ArrayList<>();
        // create edges to help DFS of MTS
        for(Integer[] i: MST) {
            edges.add(new Edge(i[0],i[1]));
        }
        // reset each vertex discovery
        for(vertex v: vertexList) {
            v.resetDiscover();
        }
        
        eulerTour(0,edges);
        System.out.println(Arrays.toString(Etour.toArray()));
        
////////// find TSP from using eTour ////////////////
        System.out.println("TSP Edges: ");
        float tspCost;
        ArrayList<Integer> TSPpath = new ArrayList<>();
        TSPpath.add(0);
        tspCost = TSP(TSPpath,Etour);
        TSPpath.add(0);
        tspCost+= adjMat[TSPpath.get(TSPpath.size()-1)][0];
        System.out.println(Arrays.toString(TSPpath.toArray()));
        System.out.println("TSP Cost: " + tspCost);
        
        
        
        
    }  // end constructor
    
    public float TSP(ArrayList<Integer> tsp, ArrayList<Integer> s) {
        float cost = 0;
        for(Integer i: s) {
            if(!tsp.contains(i)) {
                // add cost of edge to total cost
                cost += adjMat[tsp.get(tsp.size()-1)][i];
                // add vertex to tsp path
                tsp.add(i);
                
                
            }
                
        }
        
        return cost;
    }
    
    public void eulerTour(int u,ArrayList<Edge> e) {

        Etour.add(u);
        vertexList[u].wasVisited = true;
        for(int v=0;v<V; v++) {
            // check if edge exists and was not vistied
            if(!vertexList[v].wasVisited && (e.contains(new Edge(u,v)))) {
                //System.out.println(v);
                
                eulerTour(v,e);
            }
        }
        Etour.add(u);
    }
    public void PrimJarnikMST() {
        // hold vertexs in T
        ArrayList<vertex> T = new ArrayList<>();
        // hold integer representation of vertex in T
        ArrayList<Integer> vertexInT = new ArrayList<>();
        vertexList[0].wasVisited = true;
        vertexList[0].parent = -1;
       
        T.add(vertexList[0]);
        vertexInT.add(0);
        int to;
        int from;
        while(T.size() < V) {
            float minEdge = Integer.MAX_VALUE;
            // to and from will hold next edge
            to = -1;
            from = -1;
            for(int v: vertexInT) {
                if(vertexList[v].wasVisited == true) {
                    
                    for(int i=0;i<V;i++) {
                        // find the closest vertex to the cloud that is not in the cloud 
                        if(v!=i && vertexList[i].wasVisited == false && adjMat[v][i] < minEdge) {
                            
                            minEdge = adjMat[v][i];
                            from = v;
                            to = i;
                        }
                            
                    }
                }
            }
            //System.out.println(Arrays.toString(T.toArray()));
            //System.out.println("From: " + from + ", To:" + to);
            T.add(vertexList[to]);
            vertexInT.add(to);
            vertexList[to].wasVisited = true;
            vertexList[from].nextNeighbor = to;
            vertexList[to].parent = from;
        } 
    }
    public void display() {
        if (V == 0) { System.out.println("G is empty"); return; }
        if (V > 20) { 
            System.out.println("G = (V, E), where V = { 0, ..., " + (V-1) + " } and E is a full set of edges too large to display...\n"); 	
            return; 
        }

        System.out.println("G = (V, E), where V = { 0, ..., " + (V-1) + " } and E is a full set of edges with distance:"); 	   
        System.out.print("     ");
        for(int j=0; j<V; j++) 
            System.out.format("%8d", j);
        System.out.println();

        /**
        System.out.print("posi.: ");
        for(int j=0; j<V; j++) {
            vertex v1 = vertexList[j];
            System.out.format("(%2d,%2d) ", v1.x, v1.y);
        }
        System.out.println();
       */

        for (int i = 0; i < V; i++) {
            System.out.format("%5d", i);
                for(int j=0; j<V; j++) 
                        System.out.format("%8.2f", adjMat[i][j]);
                System.out.println();
        }
        System.out.println();
    }

    public String treeDisplay() {  // return the printing of the set of tree edges 
	   if (V == 0) return "G is empty";
	   String x = "The tree: ";
	   for (int i = 0; i < V; i++) {
                vertex v1 = vertexList[i];
                int j = v1.parent;
                System.out.println(j);
                if (j != -1)
                    x = x + "(" + j + ", " + i + ") ";
	   }
	   return x+"\n";   
    }
 
    // returns an unvisited vertex adj to v
    public int getAdjUnvisitedVertex(int v) {
	    for(int j=vertexList[v].nextNeighbor; j<V; j++)
	        if (vertexList[j].wasVisited==false) {
		        vertexList[v].nextNeighbor = j+1;
		        return j;
	        }
	    vertexList[v].nextNeighbor = V;
	    return -1;
    }  // end getAdjUnvisitedvertex()
   
    
    public static void main(String[] args) {
        int size=20;
        System.out.println("Size of graph: " + size + "\n");
        // Graph to MST to Euler Tour to Traveling sales person is all done upon graph creation

        graph16 theGraph = new graph16(size);
        size = 50;
        System.out.println("\nSize of graph: " + size + "\n");
        theGraph = new graph16(size);
        size = 100;
        System.out.println("\nSize of graph: " + size + "\n");
        System.out.println();
        theGraph = new graph16(size);
        
        
	
        
        
    }  // end main
    
}  // end class graph16


