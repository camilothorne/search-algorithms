package lab2;

import java.util.ArrayList;

public class BreadthSearchA{

	//	The algorithm takes
	//a graph as input and carries on with
	//breadth-first search:
	
	DirGraph G;
	ArrayList fringe = new ArrayList();
	
	public BreadthSearchA(DirGraph G){
		this.G = G;
		System.out.println("Breadth-first search:");
		System.out.println();
		this.fringeInit();
		this.search();
	}
	
	//Fringe init
	
	public void fringeInit(){
		fringe.add(G.V[0]);
		System.out.println("Start node: " + G.V[0].tag);
		System.out.println();
		System.out.print("initial fringe: ");
		Print.printFringe(fringe);
		System.out.println();
	}
	
	
	//Marking visited expanded nodes
	
	public void flagExp(Node n){
		for(int i=0; i<G.V.length;i++){
			if(n.equals(G.E[i].u)){
				G.E[i].u.flag = 1;
			}
		}
	}
	
	//Marking visited successor nodes
	
	public void flagSucc(Node n){
		for(int i=0; i<G.E.length;i++){
			if(n.equals(G.E[i].v)){
				G.E[i].v.flag = 1;
			}
		}
	}
	
	//We update the fringe (= FIFO queue datastructure)
	//we only put within non visited nodes from the
	//same layer/depth
	
	public void fringeQueue(Node n){
		flagExp(n); //we mark the expanded node as visted
		fringe.remove(n);	
		for(int i=0; i<G.V.length;i++){
			if(G.succ(n,G.V[i]) &&
			   !(1 == G.V[i].flag)){//we skip visted nodes
				fringe.add(G.V[i]);				
				flagSucc(G.V[i]);//we mark its neighbors as visted
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
			System.out.println("Goal reached : node " + n.tag + "!");
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




