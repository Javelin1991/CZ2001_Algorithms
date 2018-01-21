import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFS {
	
	private static List<Vertex> route = new ArrayList<Vertex>();
	
	public static List<Vertex> breadthFirstSearch(Graph g, Vertex startCity, Vertex endCity) {
	
		if (startCity.getCity().equals(endCity.getCity()))
			return null;
	
		int value = 1;
		
		Queue <Vertex> q = new LinkedList<Vertex>();
		Map<Vertex,Integer> tree = new HashMap<Vertex,Integer>();
		tree.put(startCity, value);
		q.add(startCity);
		startCity.mark();
		
		//BFS
		outerloop:
		while (!q.isEmpty()) {
			Vertex v = q.remove();
			value = tree.get(v) + 1;
			LinkedList<Vertex> neighbours = Graph.getNeighbours(v);
			for (Vertex n : neighbours) {
				
				if (!n.getMarkedStatus()) {		
					n.mark();
					q.add(n);
					tree.put(n , value);
					if (n.getCity().equals(endCity.getCity()))
						break outerloop;		
				}
			}
		}
		
		//find shortest path
		route.add(endCity);
		Vertex start = endCity;
		
		outerloop1:
		while (!start.getCity().equals(startCity.getCity())) {
			for (Vertex v : tree.keySet()) {
				start = route.get(route.size()-1);
				if (start.getCity().equals(startCity.getCity()))
						break outerloop1;
				if (g.isNeighbour(v, start) && (tree.get(v).intValue() ==  (tree.get(start).intValue() - 1))) {
					route.add(v);
				}
				
			}
		}
		return route;
	}
}
