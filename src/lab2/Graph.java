package lab2;


import java.util.*;


class Node implements Comparable{
	
	//A node is a number tag
	//together with a flag that
	//says if it has been visited
	//or not; nodes are ordered following
	//order among positive integers
	
	int tag;
	int flag;
	String goal;
	
	public Node(int tag, int flag, String goal){
		this.tag = tag;
		this.flag = flag;
		this.goal = goal;
	}
	
	//Method for sorting nodes
	//(if needed)
	
	public int compareTo(Object o){
		Node n = (Node)o;
		if(this.tag < n.tag){
			return -1;
		}
		else{
			if(this.tag == n.tag){
				return 0;
			}
			else{
				return 1;
			}
		}
	}
	
}

class Stack{
	
	//A stack is a LIFO datastructure
	
	ArrayList L;
	
	public void push(Object o){
		L.add(o);
	}
	
	public void pop(){
		Object o = L.get(L.size()-1);
		L.remove(o);
	}
	
}

class Queue{
	
	//A queue is a FIFO datastructure
	
	ArrayList L;
	
	public void add(Object o){
		L.add(o);
	}
	
	public void rem(){
		Object o = L.get(0);
		L.remove(o);		
	}
}

class Edge{
	
	//An edge is an ordered triple of nodes
	//with a weigth i.e. e = <u,v,cost>
	
	Node v;
	Node u;
	float cost;
	
	public Edge(Node u, Node v, float cost){
		this.u = u;//predecessor
		this.v = v;//successor
		this.cost = cost;
	}
	
}


class DirGraph{
	
	//A graph is a tuple G = <V,E> 
	//of nodes and edges 
	
	Node[] V;
	Edge[] E;
	
	public DirGraph(Node[] V, Edge[] E){
		this.V = V;
		this.E = E;
	}

	//We test whether m is a successor of n
	//in the directed graph G
	
	public boolean succ(Node n, Node m){
		boolean val = false; 
		for(int j=0; j<E.length;j++){
			if((m.equals(E[j].v)) && 
					(n.equals(E[j].u))){
				val = true;
			}
		}
		return val;
	}
	
	//Marking visited expanded nodes
	
	public void flagExp(Node n){
		for(int i=0; i<V.length;i++){
			if(n.equals(E[i].u)){
				E[i].u.flag = 1;
			}
		}
	}
	
	//Marking visited successor nodes
	
	public void flagSucc(Node n){
		for(int i=0; i<E.length;i++){
			if(n.equals(E[i].v)){
				E[i].v.flag = 1;
			}
		}
	}

	//We refresh visted nodes in graph G
	
	public void refresh(){
		for(int i=0; i<E.length;i++){
			E[i].v.flag = 0;
			E[i].u.flag = 0;
		}
	}
	
	//We compute the positive neighbourhood of a
	//node n in a directed graph G
	
	public ArrayList neighbors(Node n){
		ArrayList N = new ArrayList();
		for (int i=0; i<E.length; i++){
			if(n.equals(E[i].u) &&
					!(1 == E[i].v.flag)){
				N.add(E[i].v);
			}
		}
		return N;
	}
	
}


class BreadthSearch{
	
	//The algorithm takes
	//a graph as input and carries on with
	//breadth-first search:
	
	DirGraph G;
	ArrayList fringe = new ArrayList();
	
	public BreadthSearch(DirGraph G){
		this.G = G;
		System.out.println("(**) Breadth-first search:");
		System.out.println();
		G.refresh();
		this.fringeInit();
		this.search();
	}
	
	//Fringe init
	
	public void fringeInit(){
		fringe.add(G.V[0]);
		System.out.print("Initial fringe : ");
		Print.printFringe(fringe);
	}
	
	//We update the fringe (= FIFO queue datastructure)
	//we only put within non visited nodes from the
	//same layer/depth
	
	public void fringeQueue(Node n){
		G.flagExp(n); //we mark the expanded node as visted
		fringe.remove(n);	
		for(int i=0; i<G.V.length;i++){
			if(G.succ(n,G.V[i]) &&
			   !(1 == G.V[i].flag)){//we skip visted nodes
				fringe.add(G.V[i]);				
				G.flagSucc(G.V[i]);//we mark its neighbors as visted
			}
		}
		System.out.print("Current fringe : ");
		Print.printFringe(fringe);
	}
	
	//Breadth first search
	
	public void search(){
		Node n = (Node)fringe.get(0);
		if(n.goal.equals("goal") && 
				fringe.size()==1){
			System.out.println();
			System.out.println("(goal reached : node " + n.tag + "!)");
		}		
		else{
			if(fringe.size()==0){
				System.out.println();
				System.out.println("Fail!");
			}
			else{
				fringeQueue(n);
				search();//recursive call!
			}
		}
	}
	
}


class IterativeDeepening{
	
	DirGraph G;
	ArrayList fringe;
	boolean stop = true;
	
	//The algorithm takes
	//a graph as input and carries on with
	//iterative deepening search
	
	public IterativeDeepening(DirGraph G){
		this.G = G;
		System.out.println("(**) Iterative deepening search:");
		//System.out.println();
		//this.fringeInit();
		//this.search(3);
		this.iterate();
	}
	
	//Fringe init
	
	public void fringeInit(){
		fringe = new ArrayList();
		fringe.add(G.V[0]);
		System.out.println();
		System.out.print("Initial fringe : ");
		Print.printFringe(fringe);
	}
	
	//Updating the fringe (= LIFO stack datastructure)
	
	public void fringeStack(Node n){
		G.flagExp(n);
		ArrayList neigh = G.neighbors(n);
		if(neigh.size()>0){
			Node m = (Node)Collections.min(neigh);
			fringe.add(m);
			G.flagSucc(m);
		}
		else{
			fringe.remove(n);
		}
		System.out.print("Current fringe : ");
		Print.printFringe(fringe);
	}
	
	//Iterating until a solution is found
	
	public void iterate(){
		int c = 0;
		while(stop){
			c = c + 1;
			System.out.println();
			System.out.println("Iteration and maximum depth : "+c);
			G.refresh();
			fringeInit();
			search(c);
		}
	}
		
	//Depth-limited search
	
	public void search(int c){
		if(fringe.size()==0){
			System.out.println();
			System.out.println("(we fail!)");
		}		
		else{
			if(fringe.size()<(c+1)){
				Node n = (Node)fringe.get(fringe.size()-1);
				if(n.goal.equals("goal")){
					System.out.println();
					System.out.println("(goal reached : node " + n.tag + "!)");
					System.out.println();					
					stop = false;
				}
				else{
					fringeStack(n);
					search(c);//recursive call!
				}
			}
		}
		
	}
		
		
}

class Print{
	
	//Printing (on standard output) 
	//facilities
	
	//Printing the graph
	
	public static void printGraph(DirGraph G){
		System.out.println("(**) The graph:");
		System.out.println();		
		for(int i=0;i<G.V.length;i++){
			System.out.println("node: " + G.V[i].tag);	
		}
		System.out.println();
		for(int i=0;i<G.E.length;i++){
			System.out.print("edge: (" + G.E[i].u.tag + ",");
			System.out.print(G.E[i].v.tag + ",");
			System.out.print(G.E[i].cost + ")");
			System.out.println();
		}
		System.out.println();
	}
	
	//Printing the fringe
	
	public static void printFringe(ArrayList fringe){
		ArrayList print = new ArrayList();
		for(int i=0; i<fringe.size();i++){
			Node n = (Node)fringe.get(i);
			Integer s = new Integer(n.tag);
			print.add(s);
		}
		System.out.println(print);
	}
	
}

public class Graph{	
	
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
		
		//Search algorithm
		
		//BreadthSearch S = new BreadthSearch(G);
		IterativeDeepening S1 = new IterativeDeepening(G);
		BreadthSearch S2 = new BreadthSearch(G);
			
	}
	
}

/*

//Extra procedures:

//Depth-first search

public void search(){
	if(fringe.size()==0){
		System.out.println();
		System.out.println("Fail!");
	}		
	else{
		Node n = (Node)fringe.get(fringe.size()-1);
		if(n.goal.equals("goal")){
			System.out.println();
			System.out.println("Goal reached : node " + n.tag + "!");
			stop = false;
		}
		else{
			fringeStack(n);
			search();//recursive call!
		}
	}
}
*/
