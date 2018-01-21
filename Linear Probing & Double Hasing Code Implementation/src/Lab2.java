
import java.util.*;

public class Lab2 {

	public static void main(String[] args) {

		int size = 1009; // hash table size
		int dataSize = 0; // number of key

		System.out.println("Select the load factor :"); // ask for load factor
		System.out.println("1)0.25");
		System.out.println("2)0.50");
		System.out.println("3)0.75");

		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();

		switch (choice) {
		case (1):
			dataSize = (int) (size * 0.25);
			break;
		case (2):
			dataSize = (int) (size * 0.5);
			break;
		case (3):

			dataSize = (int) (size * 0.75);
			break;
		}

		int[] key = new int[dataSize];
		int[] hashTable = new int[size];
		for (int i = 1; i <= size; i++) {
			hashTable[i - 1] = -1; // Set every pos to -1 (-1 represent empty)
		}

		genRandomNum(key, dataSize);

		System.out.println("Select the hashing algorithm :");
		System.out.println("1)Linear Probing");
		System.out.println("2)Double Hashing");
		int hashAlgo = sc.nextInt();

		if (hashAlgo == 1) {

			double startTime = System.nanoTime();

			// populate hashtable using Linear Probing
			linearHash(key, hashTable, dataSize, size);
			double endTime = System.nanoTime();

			System.out.println("Total time taken to insert:  " + (endTime - startTime) + "nanoseconds");

			// Search using Linear probing
			System.out.println("Enter key to search");

			int searchkey = sc.nextInt();
			linearSearch(searchkey, hashTable);
			//System.out.println("Lsearch is "+lSearch(searchkey, hashTable));	//Lecture slide linear search algo

		} else if (hashAlgo == 2) {

			double startTime1 = System.nanoTime();

			// populate hashtable using double hash
			doubleHash(key, hashTable, dataSize, size);
			double endTime1 = System.nanoTime();

			System.out.println("Total time taken to insert:  " + (endTime1 - startTime1) + "nanoseconds");

			// Search using Double hash
			System.out.println("Enter key to search");
			int searchkey = sc.nextInt();
			doubleSearch(searchkey, hashTable);
			//System.out.println("Dsearch is "+dSearch(searchkey, hashTable));
		}

	}

	public static int[] genRandomNum(int[] key, int dataSize) {
		//// Generate unique random numbers
		int z = 0;

		// int key[] = new int[dataSize]; // contains diff numbers

		while (z < dataSize) {
			boolean isDuplicate = false;
			int random = (0 + (int) (Math.random() * ((10000 - 1) + 1))) * 97;

			for (int j = 0; j < z; j++) {
				if (key[j] == random) {
					isDuplicate = true;
					break;
				}

			}
			if (isDuplicate) {
				continue;
			} else {
				key[z++] = random;
			}

		}
		///

		return key;
	}

	public static int[] linearHash(int[] key, int[] hashTable, int dataSize, int size) {
		/// linear probe hashing algo
		for (int i = 1; i <= dataSize; i++) {
			int slot = key[i - 1] % size;
			int n;
			// keep increasing the hash index by 1
			for (n = slot; hashTable[n] != -1; n = (n + 1) % size) { 

			}
			hashTable[n] = key[i - 1];
			// inserting key into hash table
			System.out.println(("The key " + key[i - 1] + " is stored in slot " + n)); 

		}
		///
		return hashTable;
	}

	public static void linearSearch(int searchkey, int[] hashTable) {
		int slot = searchkey % 1009;
		int traverseCount = 0;

		int m;
		double startTime3 = System.nanoTime();
		while (traverseCount != 1008) {
			if (searchkey == hashTable[slot]) {
				System.out.println("Found at slot " + (slot));

				break;
			}
			traverseCount++;
			slot = (slot + 1) % 1009;

		}
		double endTime3 = System.nanoTime();

		System.out.println("Total time taken to search:  " + (endTime3 - startTime3) + "nanoseconds");

		if (traverseCount == (1009 - 1)) {
			System.out.println("Key Not found");
		}
		// if traverseCount = 1008 means it has run through the whole hash table and cannot be found
		// if traverseCount = 0 means no collision
		// if traverseCount > 0 means the number of key comparison it took to get to the location
		System.out.println("Total number of comparison : " + traverseCount);

	}

	public static int[] doubleHash(int[] key, int[] hashTable, int dataSize, int size) {
		for (int i = 1; i <= dataSize; i++) {

			int slot = key[i - 1] % size;
			int d = (key[i - 1] % 8) + 1;
			int n;
			for (n = slot; hashTable[n] != -1; n = (n + d) % size) {
			}
			hashTable[n] = key[i - 1];
			// inserting key into hash table
			System.out.println(("The key " + key[i - 1] + " is stored in slot " + n));
		}
		///

		return hashTable;
	}



	public static void doubleSearch(int searchkey, int[] hashTable) {
		/// double hash searching algo

		int slot = searchkey % 1009;
		int traverseCount = 0;
		int d = (searchkey % 8) + 1;
		int m;
		double startTime4 = System.nanoTime();
		while (traverseCount != 1008) {
			if (searchkey == hashTable[slot]) {
				System.out.println("Found at slot" + (slot));
				break;
			}
			traverseCount++;

			slot = (slot + d) % 1009;

		}
		double endTime4 = System.nanoTime();

		System.out.println("Total time taken to search:  " + (endTime4 - startTime4) + "nanoseconds");

		if (traverseCount == (1009 - 1)) {
			System.out.println("Key Not found");
		}
		// if traverseCount = 1008 means it has run through the whole hash table and cannot be found
		// if traverseCount = 0 means no collision
		// if traverseCount > 0 means the number of key comparison it took to get to the location
		System.out.println("Total number of comparison : " + traverseCount);
	}
	
	/* Lecture slide linear search algo
	public static int lSearch(int key, int[] hashTable) {
		int code = key % 1009;
		int loc = code;
		int ans=-1;
		while (hashTable[loc] != -1) {
			if (hashTable[loc] == key) {
				ans = loc;
				break;
			}
			
			else {
				loc = (loc + 1) % 1009;
				if (loc==code)break;
				
			}

		}
		return ans;
	}
	*/
	
	/* Lecture slide double hash search algo
		public static int dSearch(int key, int[] hashTable) {
			int code = key % 1009;
			int loc = code;
			int ans=-1;
			int d = (key % 8) + 1;
			while (hashTable[loc] != -1) {
				if (hashTable[loc] == key) {
					ans = loc;
					break;
				}
				
				else {
					loc = (loc + d) % 1009;
					if (loc==code)break;
					
				}

			}
			return ans;
		}
		*/

}
