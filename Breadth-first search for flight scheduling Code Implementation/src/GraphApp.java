import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class GraphApp {
	
	static Scanner sc = new Scanner(System.in);
	
	public static String read(String fileName, int line) {
		
		String content = "";
		try {
			content = Files.readAllLines(Paths.get(fileName)).get(line);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static void main(String[] args) throws IOException {
		Graph graph = null;
		String cities = "";
		String edges = "";
		String [] citiesArray;
		String [] edgesArray;
		String startCity = "";
		String endCity = "";
		List<String> valid = null;
		int numOfVertices;
		
	do {
		
		numOfVertices = noOfCitiesInput();
		switch (numOfVertices) {
			case 10:
				cities = read("GraphData/cities.txt",0);
				citiesArray = cities.split("\\|");
				graph = new Graph (citiesArray);
				edges = read("GraphData/edges.txt",0); 
				edgesArray = edges.split("\\|");
				Graph.generateGraph(edgesArray);
				valid = Arrays.asList(citiesArray);
				
				break;
			case 15:
				cities = read("GraphData/cities.txt",1);
				citiesArray = cities.split("\\|");
				graph = new Graph (citiesArray);
				edges = read("GraphData/edges.txt",1);
				edgesArray = edges.split("\\|");
				Graph.generateGraph(edgesArray);
				valid = Arrays.asList(citiesArray);
				break;
			case 20:
				cities = read("GraphData/cities.txt",2);
				citiesArray = cities.split("\\|");
				graph = new Graph (citiesArray);
				edges = read("GraphData/edges.txt",2);
				edgesArray = edges.split("\\|");
				Graph.generateGraph(edgesArray);
				valid = Arrays.asList(citiesArray);
				break;
				
			default:
				System.out.println("Invalid input. Please try again!");
				sc.nextLine();
				numOfVertices = noOfCitiesInput();
				break;
			}
		sc.nextLine();
	
		} while ( numOfVertices <= 0  || (numOfVertices != 10 && numOfVertices != 15 && numOfVertices != 20));
	
		Graph.printGraph();
		
		do
		{
		//do the checking for user input and null here.
		System.out.println("Enter start city:");
		startCity = sc.nextLine();
		System.out.println("Enter end city:");
		endCity = sc.nextLine();
		if (!valid.contains(startCity) || !valid.contains(endCity))
		{
			System.out.println("Invalid city entered. Please Try again.");
		}
		}while (!valid.contains(startCity) && !valid.contains(endCity));
		
		Vertex s = Graph.getVertex(startCity);
		Vertex e = Graph.getVertex(endCity);
		long startTime = System.nanoTime();
		List<Vertex> shortestPath = BFS.breadthFirstSearch(graph, s, e);
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("Total time taken to execute BFS: " + estimatedTime + " nanoseconds.");
		
		if (shortestPath.size() > 2)
		{
			for (int i = shortestPath.size() - 1; i > 0; i--) {
				System.out.print(shortestPath.get(i).getCity() + " -> ");
			}
			System.out.println(shortestPath.get(0).getCity());
			
			System.out.println("Number of stops to go " + endCity + ": " + (shortestPath.size() - 2) );
		}
		else 
		{
			System.out.println(startCity + " -> " + endCity);
			System.out.println("There is a direct flight from "+ startCity + " to " + endCity);
		}
	}
	
	public static int noOfCitiesInput() {
		try {
			System.out.println("Choose the number of cities");
			System.out.println("10");
			System.out.println("15");
			System.out.println("20");
			return sc.nextInt();
			
		}catch (InputMismatchException e) {
			return 0;
		}
		catch (NullPointerException ex) {
			return 0;
		}
	
	}


}
