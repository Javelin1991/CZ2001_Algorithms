import java.util.*;

public class CZ2001_Lab2 {

	private static boolean isDoubleHashing = true;
	private static int hashTsize = 1013;	// prime number hash table size
	private static int dataSize = 0;		// number of key
	private static long startTime = 0;
	private static long endTime = 0;
	private static int searchKey = 0;
	private static int traverseCount = 1;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean invalidInput = true;
		
		do{
			System.out.println("==========================================================================");
			System.out.println("a) 0.25");
			System.out.println("b) 0.50");
			System.out.println("c) 0.75");

			System.out.print("Please select a LOAD FACTOR: ");
			char loadChoice = sc.next().charAt(0);
	
			switch (loadChoice) {
			case 'a':
				dataSize = (int) (hashTsize * 0.25);
				invalidInput = false;
				break;
			case 'b':
				dataSize = (int) (hashTsize * 0.5);
				invalidInput = false;
				break;
			case 'c':
				dataSize = (int) (hashTsize * 0.75);
				invalidInput = false;
				break;
			default:
				System.out.println("*******************************");
				System.out.println("Invalid input! Please re-enter!");
				System.out.println("*******************************");
				invalidInput = true;
			}
		} while (invalidInput);

		int[] key = new int[dataSize];
		int[] hashTable = new int[hashTsize];
		for (int i = 1; i <= hashTsize; i++) {
			hashTable[i - 1] = -1; // Set every pos to -1 (-1 represent empty)
		}

		randomNumberGenerator(key, dataSize);
		
		do{
			System.out.println("==========================================================================");
			System.out.println("a) Linear Probing");
			System.out.println("b) Double Hashing");
			System.out.print("Please select a HASHING ALGORITHM: ");
			char hashChoice = sc.next().charAt(0);
			if (hashChoice == 'a'){
				isDoubleHashing = false;
				invalidInput = false;
			}
			else if (hashChoice == 'b'){
				isDoubleHashing = true;
				invalidInput = false;
			}
			else{
				System.out.println("*******************************");
				System.out.println("Invalid input! Please re-enter!");
				System.out.println("*******************************");
				invalidInput = true;
			}
		} while(invalidInput);
		
		System.out.println("==========================================================================");
		System.out.println("Key-Slot");
		
		/***********/
		/* HASHING */
		/***********/
		startTime = System.nanoTime();
		if (isDoubleHashing){
			doubleHash(key, hashTable, dataSize, hashTsize); // populate hashtable using double hash
		}
		else if (!isDoubleHashing){
			linearHash(key, hashTable, dataSize, hashTsize); // populate hashtable using Linear Probing
		}
		endTime = System.nanoTime();
		
		System.out.println();
		System.out.println("Total time taken to insert:  " + (endTime - startTime) + " nanoseconds");
		System.out.println("==========================================================================");
		
		/*************/
		/* SEARCHING */
		/*************/
		System.out.print("Please enter the key to search: ");
		searchKey = sc.nextInt();
		
		startTime = System.nanoTime();
		if (isDoubleHashing){
			traverseCount = doubleHashSearch(searchKey, hashTable);	// Search using Double hash
		}
		else if (!isDoubleHashing){
			traverseCount = linearProbingSearch(searchKey, hashTable); // Search using Linear probing
		}
		endTime = System.nanoTime();

		System.out.println("Total time taken to search: " + (endTime - startTime) + " nanoseconds");
		
		if (traverseCount == (hashTsize + 1)) {
			System.out.println("**Key is NOT found!**");
		}
		System.out.println("Total number of comparison: " + traverseCount);
	}

	public static int[] randomNumberGenerator(int[] key, int dataSize) {
		// To generate random number
		int z = 0;
		while (z < dataSize) {
			boolean isDuplicate = false;
			int random = (0 + (int) (Math.random() * ((10000 - 1) + 1))) * 97;

			for (int j = 0; j < z; j++) {
				if (key[j] == random) {
					isDuplicate = true;
					break;
				}
			}
			if (isDuplicate) continue;
			else key[z++] = random;
		}
		return key;
	}

	public static int[] linearHash(int[] key, int[] hashTable, int dataSize, int size) {
		for (int i = 1; i <= dataSize; i++) {
			int slot = key[i - 1] % size;
			int n;
			// keep increasing the hash index by 1
			for (n = slot; hashTable[n] != -1; n = (n + 1) % size) { 

			}
			hashTable[n] = key[i - 1]; // insert the key into hash table
			
			System.out.print(key[i - 1] + "-" + n + "\t"); 
			
			// print 7 results in a row
			if (i % 7 == 0) System.out.println();
		}
		return hashTable;
	}

	public static int linearProbingSearch(int searchkey, int[] hashTable) {
		int slot = searchkey % 1013;
		int traverseCount = 1;

		while (traverseCount != (hashTsize + 1)) {
			if (searchkey == hashTable[slot]) {
				System.out.println("It was FOUND at Slot " + slot);
				break;
			}
			traverseCount++;
			slot = (slot + 1) % 1013;
		}
		return traverseCount;
	}

	public static int[] doubleHash(int[] key, int[] hashTable, int dataSize, int size) {
		for (int i = 1; i <= dataSize; i++) {
			int slot = key[i - 1] % size;
			int d = (key[i - 1] % 8) + 1;
			int n;
			for (n = slot; hashTable[n] != -1; n = (n + d) % size) {
			}
			hashTable[n] = key[i - 1]; // insert the key into hash table
			
			System.out.print(key[i - 1] + "-" + n + "\t"); 
						
			// print 7 results in a row
			if (i % 7 == 0) System.out.println();
		}
		return hashTable;
	}



	public static int doubleHashSearch(int searchkey, int[] hashTable) {
		int slot = searchkey % 1013;
		int traverseCount = 1;
		int d = (searchkey % 8) + 1;
		while (traverseCount != (hashTsize + 1)) {
			if (searchkey == hashTable[slot]) {
				System.out.println("It was FOUND at Slot " + slot);
				break;
			}
			traverseCount++;
			slot = (slot + d) % 1013;
		}
		return traverseCount;
	}
}
