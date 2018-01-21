import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
	
	Vertex [] vertices;
	private static LinkedHashMap<Vertex, LinkedList<Vertex>> neighbours;
	private static LinkedHashMap<String, Vertex> referenceToVertex; 
	
	public Graph(String [] cities) {
		neighbours = new LinkedHashMap<Vertex, LinkedList<Vertex>>();
		referenceToVertex = new LinkedHashMap<String, Vertex>();
		
		for (int i = 0; i < cities.length; i++) {
			Vertex vertex = new Vertex(cities[i]);
			neighbours.put(vertex, new LinkedList<Vertex>());
			referenceToVertex.put(cities[i],vertex);
		}
	}
	
	public static void addEdge (String srcCity, String destCity) {
			
		if (referenceToVertex.containsKey(srcCity) && referenceToVertex.containsKey(destCity)) {
			Vertex startVertex = referenceToVertex.get(srcCity);
			Vertex endVertex = referenceToVertex.get(destCity);
			
			//since it's a undirected graph, each of the vertex is added as the neighbour of the other
			neighbours.get(startVertex).add(endVertex);
			neighbours.get(endVertex).add(startVertex);
		}	
	}
	
	public static void generateGraph(String [] edges) {
		String [] edge;
		for (int i = 0; i < edges.length; i ++) {
			
			edge = edges[i].split(",");
			addEdge(edge[0], edge[1]);
		}
	}
	
	public static LinkedList<Vertex> getNeighbours(Vertex vertex) {
		return neighbours.get(vertex);
	}
	public boolean isNeighbour(Vertex v, Vertex vx) {
		
		return (neighbours.get(v).contains(vx) || neighbours.get(vx).contains(v)) ;
	}
	public static Vertex getVertex(String city) {
		return referenceToVertex.get(city);
	}
	public static void printGraph() {
		 Set<Vertex> keys = neighbours.keySet();
		 for (Vertex v : keys) {
			 System.out.print(v.getCity() + " -> ");
			 for (Vertex vx : neighbours.get(v))
			 {
				 if (neighbours.get(v).getLast().equals(vx))
				 {
					 System.out.print(vx.getCity());
				 }
				 else
				 {
					 System.out.print(vx.getCity() + ", ");
				 }
			 }
			 System.out.println();
		 }
	}

}
