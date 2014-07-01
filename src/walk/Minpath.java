package walk;

import java.util.*;

/*
 * The greedy minpath algorithm (Dkjistra) 
 * single source. Given as input a dirgraph G
 * and a vertex u of G, it finds all the minimal
 * paths from s to all the other vertices v
 * of G (no negative distances)
 */

public class Minpath {
	
	//1. Fields:
	
	Integer source; //source vertex
	Graph G; //input graph	
	Pot[] pots;//potentials
	
	ArrayList<Integer> Q; //priority queue (vertices to minimize)
	ArrayList<Integer> S; //shortest path
	Integer pred; //current predecessor
	
	//2. Class constructor:
	
	public Minpath(Integer source, Graph G){
		this.source = source;
		this.G = G;
		this.buildpot(source);
		this.minpaths();
		this.pripot(pots);
	}
	
	//3. Methods:
	
	//Potentials (to be updated):
	public void buildpot(Integer source){
		Integer inf = Integer.MAX_VALUE; //infinity
		pots = new Pot[G.vertices.length];
		for (int i=0;i<G.vertices.length;i++){
				pots[i] = new Pot(G.vertices[i],inf);
		}
		for (int i=0;i<G.vertices.length;i++){
			if(pots[i].v == source){
				pots[i].pot = 0;
			}
		}
	}
	
	//Neighborhood computation:
	public ArrayList<Integer> neighbor(Integer u){
		ArrayList<Integer> N = new ArrayList<Integer>();
		for (int i=0;i<G.edges.length;i++){
			if (u == G.edges[i].in){
				N.add(G.edges[i].out);
			}
		}
		return N;
	}
	
	//Minimum vertex of Q:
	public Integer minQ(ArrayList<Integer> Q){
		Integer min = null;
		int i = 0;
		if (Q.size() == 1){
			min = Q.get(0);
			Q.remove(0);
		}
		else{
			while ((i+1)<Q.size()){
				min = Q.get(i);
				if (poten(min) >= poten(Q.get(i+1))){
					min = Q.get(i+1);
					Q.remove(i+1);
					}
				else{
					Q.remove(i);
				}
				i = i + 1;
				}
		}
		System.out.println(": curr min(Q) = " + min);
		return min;
	}
	
	//Membership:
	public boolean member(Integer n, ArrayList<Integer> L){
		boolean val = false;
		for (int i=0;i<L.size();i++){
			if (n == L.get(i)){
				val = val | true;
			}
		}
		return val;
	}
	
	//Print list:
	public void pri(ArrayList<Integer> L, String str){
		String s = "";
		for (int i=0;i<L.size();i++){
			s = s + L.get(i) + " ";
		}
		System.out.println(": "+str+" = { "+s+"}");
	}
	
	//Print pot:
	public void pripot(Pot[] p){
		System.out.println("\n************************");
		System.out.println("***** Potentials: ******");
		System.out.println("************************\n");
		for (int i=0;i<p.length;i++){
			System.out.println(": p(" + source + "," + p[i].v +") = "+p[i].pot);
		}
	}
	
	//Cost accessor:
	public Integer costacc(Integer u, Integer v){
		Integer cost = null;
		for (int i=0;i<G.edges.length;i++){
			if (u == G.edges[i].in & v == G.edges[i].out){
				cost = G.edges[i].cost;
			}
		}
		return cost;
	}
	
	//Potential accessor:
	public Integer poten(Integer u){
		Integer pot = null;
		for (int i=0;i<pots.length;i++){
			if (u == pots[i].v){
				pot = pots[i].pot;
			}
		}
		return pot;
	}
	
	//Potential mutator:
	public void uppoten(Integer u, Integer pot){
		for (int i=0;i<pots.length;i++){
			if (u == pots[i].v){
				pots[i].pot = pot;
			}
		}
	}
	
	//Relaxation:
	public void relax(Integer u){
		ArrayList<Integer> ne = neighbor(u);
		pri(ne,"neigh("+u+")");
		for (int i=0;i<ne.size();i++){
			Integer v = ne.get(i);
			System.out.println("\n: consider "+v+",");
			if (member(v,ne) != false){
				Integer cost = costacc(u,v);
				System.out.println(": p("+ v +") = " + poten(v));
				System.out.println(": c("+ u +","+ v +") = " + cost);
				if (poten(v) > poten(u) + cost){
					System.out.println(": p("+poten(v)+") > p("+poten(u)+") " +
							"+ c("+ u +","+ v +"),");
					Integer pot = poten(u) + cost;
					uppoten(v,pot);
					System.out.println(": => min p("+ v +") = " + poten(v));
					pred = u;
					System.out.println(": => pred("+ v +") = " + pred);
					System.out.println(": => p(pred("+ v +")) = " + poten(pred));
					Q.add(v);
				}
			}
		}
		
	}
	
	//Main loop:
	public void minpaths(){
		Q = new ArrayList<Integer>();
		S = new ArrayList<Integer>();
		Q.add(source);
		int count = 0;

		System.out.println("************************");
		System.out.println("******* Algo run: ******");
		System.out.println("************************\n");
		pri(Q,"init Q");//initial Q
		pri(S,"init S");//initial S
		System.out.println(": init vertex = " + source);//source vertex
		System.out.println(": init pred = " + pred);//init predecessor

		while (Q.size() != 0){
			count = count + 1;
			System.out.println("\n************************");
			System.out.println(": iter = " + count);
			System.out.println("************************\n");
			pri(Q,"curr Q");//current Q
			Integer u = minQ(Q);//min of Q
			S.add(u);//we update S
			pri(S,"curr S");//current S
			relax(u);//the relaxation updates Q!
			if (count > ((G.vertices.length)*(G.vertices.length))){
				break;//in case the algo doesn't behaved as desired!
			}
		}
	}
	
	//4. Main method:
	
	public static void main(String [] args){
		
		DirEdge e1 = new DirEdge(1,2,2);
		DirEdge e2 = new DirEdge(1,3,1);
		DirEdge e3 = new DirEdge(3,6,3);
		DirEdge e4 = new DirEdge(2,3,1);
		DirEdge e5 = new DirEdge(6,5,2);
		DirEdge e6 = new DirEdge(5,2,2);
		DirEdge e7 = new DirEdge(5,4,1);
		DirEdge e8 = new DirEdge(4,2,2);
		DirEdge e9 = new DirEdge(5,7,3);
		DirEdge e10 = new DirEdge(7,8,1);
		DirEdge e11 = new DirEdge(8,7,2);
	
		Integer[] vert = new Integer[8];
		DirEdge[] edge = new DirEdge[11];
		
		vert[0] = 1;
		vert[1] = 2;
		vert[2] = 3;
		vert[3] = 4;
		vert[4] = 5;
		vert[5] = 6;
		vert[6] = 7;
		vert[7] = 8;	
		
		edge[0] = e1;
		edge[1] = e2;
		edge[2] = e3;
		edge[3] = e4;
		edge[4] = e5;
		edge[5] = e6;
		edge[6] = e7;
		edge[7] = e8;
		edge[8] = e9;
		edge[9] = e10;
		edge[10] = e11;
		
		Graph G = new Graph(vert,edge);
		Minpath path = new Minpath(1,G);
				
	}
}

//Auxiliary classes:

//1. Directed Edges:
class DirEdge{
	//A directed edge is a triple <v,u,c(u,v)>
	Integer in;
	Integer out;
	Integer cost;
	public DirEdge(int in, int out, int cost){
		this.in = in; //predecessor
		this.out = out; //successor
		this.cost = cost; //weight
	}
}

//2. Potentials:
class Pot{
	//A potential is a pair <v,p(v)>
	Integer v;
	Integer pot;
	public Pot(Integer v, Integer pot){
		this.v = v; //vertex
		this.pot = pot; //potential
	}
}

//2. Graphs:
class Graph{
	//An directed graph is a pair <V,E>
	Integer[] vertices;
	DirEdge[] edges;
	public Graph(Integer[] vertices, DirEdge[] edges){
		this.vertices = vertices; //V
		this.edges = edges; //E
	}
}