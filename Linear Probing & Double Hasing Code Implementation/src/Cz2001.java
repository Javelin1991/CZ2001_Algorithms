package cz2001;

import java.util.Arrays;

public class Cz2001 {

    private static Integer[] ARR_HASH_TABLE;
    private static Integer[] ARR_INPUTS;
    private static Integer TABLE_SIZE = 1000;
    private static Integer INPUT_SIZE = 1000;
    private static Integer RANDOM_NUMBER_SIZE = 1000;
    private static boolean isDoubleHashing = true;
    private static Integer intNumOfNotInserted = 0;
    private static Integer intNumOfComparison = 0;
    private static Integer intTotalNumOfInsertComparison = 0;

    public static void main(String[] args) {
        // define an integer array of size X
        ARR_HASH_TABLE = new Integer[TABLE_SIZE];
        /*
         default the values of every element in the integer array
         with assumptions that all inputs are positive numbers
         */
        Arrays.fill(ARR_HASH_TABLE, -1);

        // generates random number to store into the integer array
        // this is for dynamic purpose, nothing to do with hasing
        String strConcatInput = "";
        ARR_INPUTS = new Integer[INPUT_SIZE];
        for (int i = 0; i < INPUT_SIZE; i++) {
            Integer intGeneratedNumber = (int) (Math.random() * RANDOM_NUMBER_SIZE);
            Integer intInputIndex = 0;
            // make sure no duplicate of inputs
            while (ARR_INPUTS[intInputIndex] != null && !ARR_INPUTS[intInputIndex].equals(-1)) {
                if (ARR_INPUTS[intInputIndex].equals(intGeneratedNumber)) {
                    intGeneratedNumber = (int) (Math.random() * RANDOM_NUMBER_SIZE);
                    break;
                }
                intInputIndex++;
            }
            ARR_INPUTS[i] = intGeneratedNumber;
            if (i % 10 == 0) {
                strConcatInput += "\n";
            }
            strConcatInput += i + "-" + ARR_INPUTS[i] + "\t\t";
        }
        // print the input for the first time
        System.out.println("Randomly Generated Unique Employee IDs: " + strConcatInput);

        // calculates the time of insertion
        long lngStartTime = System.nanoTime();
        insertIntoHashTable();
        long lngEndTime = System.nanoTime();
        long lngTimeForInsertion = lngEndTime - lngStartTime;

        // print the input for the second time for easy referencing
        System.out.println();
        System.out.println("Randomly Generated Unique Employee IDs: " + strConcatInput);
        System.out.println();

        // print the hashtable content
        System.out.print("Hashtable: ");
        String strConcatHashTable = "";
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (i % 10 == 0) {
                strConcatHashTable += "\n";
            }
            strConcatHashTable += i + "-" + (ARR_HASH_TABLE[i].equals(-1) ? "N" : ARR_HASH_TABLE[i]) + "\t\t";
        }
        System.out.println(strConcatHashTable);
        System.out.println();

        System.out.println("Time taken to insert " + INPUT_SIZE + " Employee IDs: " + lngTimeForInsertion + " nanoseconds.");
        System.out.println("Employee IDs not inserted due to unavailability at calculated hashtable index: " + intNumOfNotInserted + ".");
        System.out.println("Total number of comparisons made for successful/unsuccessful insertion: " + intTotalNumOfInsertComparison + ".");
        System.out.println();

        // get a random number to search for
        Integer intItemToSearch = ((int) (Math.random() * RANDOM_NUMBER_SIZE));
        System.out.println("Searching Employee ID " + intItemToSearch + "...");
        Integer intDefaultHashIndex = intItemToSearch % TABLE_SIZE;

        // try to find the index that the key matches
        // calculates the time of searching
        intNumOfComparison = 1;
        lngStartTime = System.nanoTime();
        Integer intFoundAtHashIndex = isDoubleHashing ? doubleHash("search", intDefaultHashIndex, intItemToSearch) : hash("search", intDefaultHashIndex, intItemToSearch);
        lngEndTime = System.nanoTime();
        long lngTimeForSearching = lngEndTime - lngStartTime;

        if (intFoundAtHashIndex.equals(-1)) {
            System.out.println("Employee ID " + intItemToSearch + " not found in hashtable after " + intNumOfComparison + " comparisons.");
        } else {
            System.out.println("Employee ID " + intItemToSearch + " found at Index " + intFoundAtHashIndex + " after " + intNumOfComparison + " comparisons.");
        }
        System.out.println("Time taken to search for Employee ID " + intItemToSearch + ": " + lngTimeForSearching + " nanoseconds.");
    }

    private static void insertIntoHashTable() {
        // inserting the random generated value into the hashtable
        for (int i = 0; i < INPUT_SIZE; i++) {
            /*
             derives the index to the hashtable
             based on the mod results of the input
             */
            Integer intDefaultHashIndex = ARR_INPUTS[i] % TABLE_SIZE;
            System.out.println();
            System.out.println("Attempting to inserting Employee ID " + ARR_INPUTS[i] + " at hashtable index: " + intDefaultHashIndex + ".");

            /*
             checks if the value is defaulted (as -1)
             if the index is occupied (not the default -1),
             linear probing will be performed to look for
             next available unused index.
             */
            intNumOfComparison = 1;
            Integer intProbingIndex = isDoubleHashing ? doubleHash("insert", intDefaultHashIndex, ARR_INPUTS[i]) : hash("insert", intDefaultHashIndex, ARR_INPUTS[i]);
            intTotalNumOfInsertComparison += intNumOfComparison;
            if (intProbingIndex == -1) {
                intNumOfNotInserted++;
                System.out.println("Employee ID " + ARR_INPUTS[i] + " NOT INSERTED after " + intNumOfComparison + " comparisons.");
            } else {
                // adds to the specified index of the hash table
                ARR_HASH_TABLE[intProbingIndex] = ARR_INPUTS[i];
                System.out.println("Employee ID " + ARR_INPUTS[i] + " INSERTED at hashtable index " + intProbingIndex + " after " + intNumOfComparison + " comparisons.");
            }
        }
    }

    private static Integer hash(String strOperation, Integer intDefaultHashIndex, Integer intKey) {
        int intProbingIndex = intDefaultHashIndex;

        /*
         if-else over here so that each has dedicated while loop;
         significant lesser condition checking as compared to having if-else inside while loop
         */
        if (strOperation.equals("insert")) {
            // checks if is unused
            String strCollisionIndices = "";
            while (ARR_HASH_TABLE[intProbingIndex] != -1) {
                intNumOfComparison++;
                strCollisionIndices += intProbingIndex + ", ";
                intProbingIndex++;

                /*
                 if the probing index is at last index and incremented
                 to larger the size of the array, set the
                 probing to continue at 0
                 */
                if (intProbingIndex == TABLE_SIZE) {
                    intProbingIndex = 0;
                }
            }
            if (!strCollisionIndices.equals("")) {
                System.out.println("Employee ID " + intKey + " COLLIDED at hashtable indices " + strCollisionIndices);
            }
        } else if (strOperation.equals("search")) {
            // checks if is equal
            String strComparedIndices = "";
            while (!ARR_HASH_TABLE[intProbingIndex].equals(intKey)) {
                intNumOfComparison++;
                strComparedIndices += intProbingIndex + ", ";
                intProbingIndex++;

                /*
                 if the probing index is at last index and incremented
                 to larger the size of the array, set the
                 probing to continue at 0
                 */
                if (intProbingIndex == TABLE_SIZE) {
                    if (intDefaultHashIndex == 0) {
                        /*
                         if default hash index is zero, and that it has reaches
                         a probing index of input size, means it has traversed through
                         the whole hash table already. therefore break;
                         */
                        intProbingIndex = -1;
                        break;
                    } else {
                        intProbingIndex = 0;
                    }
                } else {
                    // if probing has completed it's round and came back same spot;
                    if (intProbingIndex == intDefaultHashIndex) {
                        intProbingIndex = -1;
                        break;
                    }
                }
            }
            System.out.println("Employee ID " + intKey + " compared with hashtable indices " + strComparedIndices);
        }
        return intProbingIndex;
    }

    private static Integer doubleHash(String strOperation, Integer intDefaultHashIndex, Integer intKey) {
        int intProbingIndex = intDefaultHashIndex;

        /*
         if-else over here so that each has dedicated while loop;
         significant lesser condition checking as compared to having if-else inside while loop
         */
        if (strOperation.equals("insert")) {
            // checks if is unused
            String strCollisionIndices = "";
            while (ARR_HASH_TABLE[intProbingIndex] != -1) {
                intNumOfComparison++;
                strCollisionIndices += intProbingIndex + ", ";
                intProbingIndex = ((intProbingIndex % TABLE_SIZE) + 5) % TABLE_SIZE;

                // if probing has completed it's round and came back same spot;
                if (intProbingIndex == intDefaultHashIndex) {
                    intProbingIndex = -1;
                    break;
                }
            }
            if (!strCollisionIndices.equals("")) {
                System.out.println("Employee ID " + intKey + " COLLIDED at hashtable indices " + strCollisionIndices);
            }
        } else if (strOperation.equals("search")) {
            // checks if is equal
            String strComparedIndices = "";
            while (!ARR_HASH_TABLE[intProbingIndex].equals(intKey)) {
                intNumOfComparison++;
                strComparedIndices += intProbingIndex + ", ";
                intProbingIndex = ((intProbingIndex % TABLE_SIZE) + 5) % TABLE_SIZE;

                // if probing has completed it's round and came back same spot;
                if (intProbingIndex == intDefaultHashIndex) {
                    intProbingIndex = -1;
                    break;
                }
            }
            System.out.println("Employee ID " + intKey + " compared with hashtable indices " + strComparedIndices);
        }
        return intProbingIndex;
    }
}
