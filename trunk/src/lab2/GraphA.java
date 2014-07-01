package lab2;

public class GraphA extends Graph{	
	
	//Class with main method

	public static void main(String[] args){
		
		//Nodes
		
		Node a = new Node(1,0,"start"); 
		Node b = new Node(2,0,""); 
		Node c = new Node(3,0,""); 
		Node d = new Node(4,0,""); 
		Node e = new Node(5,0,""); 
		Node f = new Node(6,0,""); 
		Node g = new Node(7,0,"goal"); 
		
		//Set V of nodes
		
		//Ordered by lexicographic
		//ordering or by the usual
		//ordering over positive 
		//integers
		
		//Convention
		
		//a is the start node and
		//g is the goal node
		
		Node[] V = {a,b,c,d,e,f,g};
		
		//Edges
		
		Edge e1 = new Edge(a,b,4);
		Edge e2 = new Edge(a,c,1);
		Edge e3 = new Edge(b,d,3);
		Edge e4 = new Edge(b,e,8);
		Edge e5 = new Edge(c,c,1);
		Edge e6 = new Edge(c,d,2);
		Edge e7 = new Edge(c,f,6);
		Edge e8 = new Edge(d,c,2);
		Edge e9 = new Edge(d,e,4);
		Edge e10 = new Edge(e,g,2);
		Edge e11 = new Edge(f,g,8);
		
		//Set E of edges
		
		Edge[] E = {e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11};
		
		//Directed graph
		
		DirGraph G = new DirGraph(V,E);
		
		//Printing the graph in STDOUT
		
		Print.printGraph(G);
		
		//Search algorithms
		
		BreadthSearchA S = new BreadthSearchA(G);
		//IterativeDeepeningA S = new IterativeDeepeningA(G);
			
	}
	
}
