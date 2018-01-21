import java.io.*;
import java.nio.file.*;
import java.util.*;


import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;

public class SortApp {
	
	 public static int[] numData = {10, 20, 30, 100, 100000};
	 public static final Path dataPath = Paths.get(System.getProperty("user.dir"),"data");
	 public static int threshold;
	 
		public static void main (String[] args)
		{	
			
			optionMenu();
			
			
		}
		
		
		public static void optionMenu()
		{
			int choice;
			
			Scanner sc = new Scanner(System.in);
			
			
			System.out.println("Note: To change the no. of dataset to generate, edit the int[] numData.");
			System.out.println("Note: Data generated will be in data folder in CSV format.");
			System.out.println("");
			System.out.println("<<<<<<<<<<<<<<Choose an option>>>>>>>>>>>>>>");
			System.out.println("Option 1: Generate Dataset");
			System.out.println("Option 2: Sort Dataset");
			
			choice = sc.nextInt();
			
			switch(choice){
			
				case 1: GenerateRandom();
					break;
				case 2: 
					System.out.println("Specify threshold level: ");
					threshold = sc.nextInt();
					SortRandom();
					break;
			
			}
			
		}
		
		
		
		//Generate random data 
		public static void GenerateRandom() {
			
			ArrayList<Integer> ArrayRandomNumbers = new ArrayList<Integer>();
			Path filePath;
			
			
		    for (int maxArrayLength : numData) {
				
		    	ArrayRandomNumbers.clear();
		    	
		    	for (int j = 1; j<=maxArrayLength; j++)
		    	{
		    		ArrayRandomNumbers.add(1+(int)(Math.random()*maxArrayLength));

		    	}
		    	
		    	filePath = Paths.get(dataPath.toString(), maxArrayLength + " data.csv");
		    	
		    	try {
		    		writeArrayListToCSV(filePath.toString(),ArrayRandomNumbers);
		    	}
		    	catch(IOException e) {
		    	}
		    	
		    	
			}  
		    
			optionMenu();
		}
		
		//Sort random data
		public static void SortRandom() {
			
			int[] ArrayRandomNumbers = null;

			Path filePath;
			
			
			//read data from csv file first
			for(int maxArrayLength : numData) {
				filePath = Paths.get(dataPath.toString(), maxArrayLength + " data.csv");
				
				try {
					ArrayRandomNumbers = readCSVToArray(filePath.toString(), maxArrayLength);
				}catch(IOException e) {
					
				}
				
				//start counting cpu time for merge & mergeInsertion sort
				recordMergeSortCPUTime(ArrayRandomNumbers);
				recordMergeInsertionSortCPUTime(ArrayRandomNumbers);
			}
			
			optionMenu();
		}
		
		
		public static void recordMergeSortCPUTime(int[] ArrayRandomNumbers) {
			long startTime, endTime, totalTime = 0;
			int[] ArrayRandomNumbersClone = null;
			Path filePath;
			
			MergeSort m = new MergeSort();
			
			//copy array so that it doesn't affect original array
			ArrayRandomNumbersClone = ArrayRandomNumbers.clone();
			
			//start counting cpu time for mergesort
			startTime = System.nanoTime();
			m.mergeSort(0, ArrayRandomNumbersClone.length-1, ArrayRandomNumbersClone);
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			
			
			//write sorted array back to csv file
			filePath = Paths.get(dataPath.toString(), ArrayRandomNumbersClone.length + " sorted data.csv");
	    	
	    	try {
	    		writeArrayToCSV(filePath.toString(),ArrayRandomNumbersClone);
	    	}
	    	catch(IOException e) {
	    	}    	
			
			System.out.println("CPU time for MergeSort for: " + ArrayRandomNumbersClone.length + " data is " + totalTime + " nanoseconds");
			System.out.println("No. of comparison for MergeSort for: " + ArrayRandomNumbersClone.length + " data is " + m.getNumberOfComparison() + " comparisons");
			System.out.println("");
			
			m.resetNumberOfComparison();
		}
		
		public static void recordMergeInsertionSortCPUTime(int[] ArrayRandomNumbers) {
			long startTime, endTime, totalTime;
			int[] ArrayRandomNumbersClone = null;
			Path filePath;
			
			MergeInsertionSort mi = new MergeInsertionSort();
			
			//copy array so that it doesn't affect original array
			ArrayRandomNumbersClone = ArrayRandomNumbers.clone();
			
			//start counting cpu time for mergeInsertion sort
			startTime = System.nanoTime();
			mi.mergeInsertionSort(0, ArrayRandomNumbersClone.length-1, ArrayRandomNumbersClone, threshold);
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			
			//write sorted array back to csv file
			filePath = Paths.get(dataPath.toString(), ArrayRandomNumbersClone.length + " sorted data.csv");
	    	
	    	try {
	    		writeArrayToCSV(filePath.toString(),ArrayRandomNumbersClone);
	    	}
	    	catch(IOException e) {
	    	}
			
			
			System.out.println("CPU time for MergeInsertionSort for: " + ArrayRandomNumbersClone.length + " data is " + totalTime + " nanoseconds");
			System.out.println("No. of comparison for MergeInsertionSort for: " + ArrayRandomNumbersClone.length + " data is " + mi.getNumberOfComparison() + " comparisons");
			System.out.println("");
			
			mi.resetNumberOfComparison();
		}
		
		
		
		//read csvfile to array
		private static int[] readCSVToArray(String filepath, int size) throws IOException{
			int[] ArrayRandomNumbers = new int[size];
			CSVReader reader = new CSVReader(new FileReader(filepath));
			String[] csvRow = null;
			for(int i = 0; i<size; i++) {
				csvRow = reader.readNext();
				if (csvRow == null) {
					break;
				}		
				ArrayRandomNumbers[i] = Integer.parseInt(csvRow[0]);
			}
			
			reader.close();
			return ArrayRandomNumbers;
		}
		
		//Write arraylist to a csv file before sorting
		public static void writeArrayListToCSV(String filepath, ArrayList<Integer> ArrayRandomNumbers) throws IOException
		{
			CSVWriter writer = new CSVWriter(new FileWriter(filepath));
			for (int num : ArrayRandomNumbers) {
				String[] data = new String[] {Integer.toString(num)};
				writer.writeNext(data);
			}
			writer.close();
		}
		
		//Write array to a csv file after sorting 
		public static void writeArrayToCSV(String filepath, int[] ArrayRandomNumbers) throws IOException
		{
			CSVWriter writer = new CSVWriter(new FileWriter(filepath));
			for (int num : ArrayRandomNumbers) {
				String[] data = new String[] {Integer.toString(num)};
				writer.writeNext(data);
			}
			writer.close();
		}
		
}
