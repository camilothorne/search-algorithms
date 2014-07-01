package lab2;

import java.util.*;

//Towers class

class Towers{

	//1.Fields
	
	//Disks are represented as integers
	//Pegs are represented as lists of integers
	int disks;
	ArrayList init; 
	ArrayList A;
	ArrayList B;
	ArrayList C;
	
    //2. Methods
	
	//Constructor (stores the initial state of the problem)
	Towers(int disks){
		this.disks = disks;
		this.init();//builds the initial peg
		B = new ArrayList();
		C = new ArrayList();
		System.out.println("We succed when B = init = "+init+"\n");
		this.moveTower();
	}
	
	//Initialization procedure
	public void init(){
		init = new ArrayList();
		for(int i=disks; i>=1; i--){
			Integer j = new Integer(i);
			init.add(j);
		}
		A = new ArrayList();
		for(int i=disks; i>=1; i--){
			Integer j = new Integer(i);
			A.add(j);
		}
	}
	
	//Printing procedure
	public void printL(ArrayList L){
		for(int i=0; i<L.size(); i++)
			System.out.println(L.get(i));
	}
	
	//Moving disks in even steps
	public void moveEven(ArrayList peg1, ArrayList peg2,
			ArrayList moved){
		//We get the smallest disk of each peg
		//and move it to the next peg and a list
		//stores copies of moved disks
		if(peg1.size()>0){
			Object d1 = Collections.min(peg1);
			if(!(elem(d1,moved))){
				peg1.remove(d1);
				peg2.add(d1);
				moved.add(d1);
			}
		}
	}
	
	
	//Moving disks in odd steps
	public void moveOdd(ArrayList peg1, ArrayList peg2,
			ArrayList moved){
		if(peg1.size()>0){
			Integer n = (Integer)Collections.min(peg1);
			if(!(elem(n,moved))){
				if(constraint(n, peg2)){
					peg1.remove(n);
					peg2.add(n);
					moved.add(n);
				}
			}
		}
	}
	
	//Testing whether a disk has
	//been already moved
	public boolean elem(Object disk, ArrayList moved){
		boolean val = false;
		for(int i=0; i<moved.size(); i++){
			if(disk.equals(moved.get(i))){
				val = true;
			}
		}
		return val;
	}
	
	//Expressing the constraint - we check if a peg 
	//is non-empty, in which case a disk can be put in
	//only if its smaller than its smaller disk
	public boolean constraint(Integer n, ArrayList L){
		boolean val = false;
		if(L.size()==0){
			val = true;
		}
		else{
			Integer m = (Integer)Collections.min(L);
			int n1 = n.intValue();
			int m1 = m.intValue();
			if(n1 < m1){
				val = true;
			}
		}
		return val;
	}
	    
	//The main loop method (generates the new states)
	public void moveTower(){
		int count = 0;//step counter
		ArrayList moved = new ArrayList();
		while(!(B.equals(init))){//we want B to be equal to init
			System.out.println("\n"+"Step: "+count+"\n");
			if(count%2==0){
				//actions
				moveEven(A,B,moved);
				moveEven(B,C,moved);
				moveEven(C,A,moved);
				//printing out the movements made
				System.out.println("Even A = "+A);
				System.out.println("Even B = "+B);
				System.out.println("Even C = "+C);
			}
			else{
				//printing out the moved disks
				System.out.println("Moved disks = "+moved +"\n");
				//actions
				moveOdd(A,B,moved);
				moveOdd(A,C,moved);
				moveOdd(B,A,moved);
				moveOdd(B,C,moved);
				moveOdd(C,A,moved);
				moveOdd(C,B,moved);
				moved = new ArrayList();
				//printing out the movements made
				System.out.println("Odd A = "+A);
				System.out.println("Odd B = "+B);
				System.out.println("Odd C = "+C);
			}
			count = count + 1;
		}
		System.out.println("\n"+"Success! B = "+B);
	}
	
}

//Tower2 class with main method

public class Tower2{
	    public static void main(String[] args){
	        Towers a = new Towers(4);//we work only with 4 disks
	    }
}
	

