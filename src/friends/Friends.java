package friends;

import java.util.ArrayList;
import java.util.HashMap;

import structures.Queue;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION

		ArrayList<String> arrayList = new ArrayList<>();
		Queue<Integer> q = new Queue<>();
		boolean[] visited = new boolean[g.members.length];

		HashMap<Integer,Integer> previous = new HashMap<Integer,Integer>();

		if(!g.map.containsKey(p1) || !g.map.containsKey(p2)){
			return arrayList;
		}
		int start = getIndexForName(g, p1);
		int finish = getIndexForName(g, p2);
		int curr = start;

		previous.put(curr,start);
		q.enqueue(curr);
		visited[curr] = true;


		while(!q.isEmpty()) {
			curr = q.dequeue();
			if(curr == finish) {
				break;
			} else {
				for(Friend friend = g.members[curr].first; friend != null; friend = friend.next){
					int fnum = friend.fnum;
					if(!visited[fnum]){
						q.enqueue(fnum);
						visited[fnum] = true;
						previous.put(fnum, curr);
					}
				}
			}
		}

		if(curr != finish) {
			return arrayList;
		}
		arrayList.add(getNameAtIndex(g, finish));
		for(int i=finish; i != start; i=previous.get(i)) {
			arrayList.add(0, getNameAtIndex(g, previous.get(i)));
		}
		return arrayList;
	}

	private static int getIndexForName(Graph g, String name) { return g.map.containsKey(name) ? g.map.get(name) : -1; }

	private static String getNameAtIndex(Graph g, int vnum) { return g.members[vnum].name; }


	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION

		boolean[] visited = new boolean[g.members.length];
		ArrayList<String> arrayList;
		ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();

		for(int v=0; v < visited.length; v++){
			if(!visited[v] && school.equals(g.members[getIndexForName(g, getNameAtIndex(g, v))].school)){
				arrayList = new ArrayList<>();
				arrayLists.add(getClique(g, v, visited, school, arrayList));
			}
		}
		return arrayLists;
	}

	private static ArrayList<String> getClique(Graph g, int v, boolean[] visited, String school, ArrayList<String> arrayList){
        visited[v] = true;
        arrayList.add(g.members[v].name);
        for(Friend friend = g.members[v].first; friend != null; friend = friend.next){
        	if(!visited[friend.fnum] && school.equals(g.members[getIndexForName(g, getNameAtIndex(g, friend.fnum))].school)){
        		getClique(g, friend.fnum, visited, school, arrayList);
			}
		}
		return arrayList;
	}


	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION

		ArrayList<String> arrayList = new ArrayList<String>();
		int dfsNumCounter = 0, backNumCounter = 0;
		boolean[] visited = new boolean[g.members.length];
		int[] dfsNum = new int[g.members.length];
		int[] backNum = new int[g.members.length];
		int[] connectors = new int[g.members.length];

		for (int i = 0; i < connectors.length; i++) {
			connectors[i] = 0;     //0 for not connector
		}

		for (int i = 0; i < g.members.length; i++) {
			if (!visited[i]) {
				connectors[i] = 1;     //1 for starting point but not necessarily connector
				connectorsUsingDFS(g, g.members[i], visited, dfsNum, backNum, dfsNumCounter, backNumCounter, connectors);
			}
		}

		boolean firstTime = true;
		for (int i = 0; i < g.members.length; i++) {

			if (connectors[i] == 3) //3 for connector
			{
				if (firstTime) {
					arrayList.add(g.members[i].name);
					firstTime = false;
				} else {
					arrayList.add(g.members[i].name);
				}
			}
		}
		return arrayList;
	}

	private static void connectorsUsingDFS(Graph g, Person person, boolean[] visited, int[] dfsNum, int[] backNum, int dfsNumCounter, int backNumCounter, int[] connectors) {
		int personIndex = getIndexForName(g, person.name);
		visited[personIndex] = true;
		dfsNum[personIndex] = dfsNumCounter;
		dfsNumCounter++;
		backNum[personIndex] = backNumCounter;
		backNumCounter++;

		Friend friend = person.first;
		while (friend != null) {

			if (!visited[friend.fnum]) {
				Person next = g.members[friend.fnum];
				connectorsUsingDFS(g, next, visited, dfsNum, backNum, dfsNumCounter, backNumCounter, connectors);

				if (dfsNum[personIndex] > backNum[friend.fnum]) {
					backNum[personIndex] = Math.min(backNum[personIndex], backNum[friend.fnum]);
				} else {

					if (connectors[personIndex] == 0) {
						connectors[personIndex] = 3;
					}
					else if (connectors[personIndex] == 1) {
						connectors[personIndex] = 2;
					}
					else if (connectors[personIndex] == 2) {
						connectors[personIndex] = 3;
					}
				}
			}
			else {
				backNum[personIndex] = Math.min(backNum[personIndex], dfsNum[friend.fnum]);
			}
			friend = friend.next;
		}
	}
}

