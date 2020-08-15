
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import friends.Friends;
import friends.Graph;


public class FriendshipApp {
	
static Scanner stdin = new Scanner(System.in);
	
	static char getOption() {
		System.out.print("\tChoose action: ");
		System.out.print("\t1) Shortest Path Intro Chain, ");
		System.out.print("\t2) Cliques, ");
		System.out.print("\t3) Connectors, ");
		System.out.print("(q)uit? => ");
		char response = stdin.next().toLowerCase().charAt(0);
		while (response != '1' && response != '2' && response != '3' && response != 'q') {
			System.out.print("\tYou must enter either 1, 2, 3 or q => ");
			response = stdin.next().toLowerCase().charAt(0);
		}
		return response;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(new File("ex1.txt").getAbsolutePath());
		String graphFile = "D:\\system\\intelliJ\\Projects\\Friends\\ex1";//stdin.next();
		Scanner sc = new Scanner(new File(graphFile));
		Graph graph = new Graph(sc);
		Friends friends = new Friends();
		char option;
		while((option = getOption()) != 'q') {
			switch (option) {
				case '1':
					System.out.print("\t\tWho wants the intro? => ");
					String name1 = stdin.next().toLowerCase();
					System.out.print("\t\tWho does "+ name1 + " want to meet? ;) => ");
					String name2 = stdin.next().toLowerCase();
					System.out.println("Shortest Chain here : " + "\n" + friends.shortestChain(graph, name1, name2));
					break;
				case '2':
					System.out.print("\t\tWhich school? => ");
					BufferedReader in1 = new BufferedReader(new InputStreamReader(System.in));
					String school1 = in1.readLine();
					System.out.println("Cliques for " + school1 + " here : " + friends.cliques(graph, school1));
					break;
				case '3':
					System.out.println("Connectors are here : " + friends.connectors(graph));
					break; 
			}
		}

	}
}
