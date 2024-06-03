package com.orthoarkansas.reconciler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Class handling the main business logic.
 */
public class Reconciler {

    private HashSet<String> inFile1NotFile2;
    private HashSet<String> inFile2NotFile1;

    /**
     * Reconciles the files referenced by filename1 and filename2. Populates the inFile1NotFile2 and inFile2NotFile1 hash sets with the results.
     * @param filename1 The path to the file to reconcile with the file referenced by filename2
     * @param filename2 The path to the file to reconcile with the file referenced by filename1
     */
    public void reconcile(String filename1, String filename2) {
        // put the entries into the hash sets
        try {
            inFile1NotFile2 = buildSet(filename1);
            inFile2NotFile1 = buildSet(filename2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // find the set difference for each of them
        reconcile(inFile1NotFile2, inFile2NotFile1);
    }

    /**
     * Generates a HashSet containing all the entries in the given csv file
     * @param filename The csv file from which to pull the entries
     * @return A HashSet containing all the entries in the csv file
     * @throws FileNotFoundException If the underlying Scanner throws FileNotFoundException
     */
    private HashSet<String> buildSet(String filename) throws FileNotFoundException {
        // let's prepare our return variable
        HashSet<String> out = new HashSet<>();

        try (Scanner f1 = new Scanner(new File(filename))) {
            // all files have a header, we can skip it
            f1.nextLine();

            // now we can enter the loop
            while(f1.hasNextLine()) {
                String line = f1.nextLine();

                // some files may have multiple values on one line. split by comma and take the first value
                String[] sections = line.split(",");
                line = sections[0];

                // some entries may be in a dot-separated domain format (ex: name.root.ortho). All we are concerned with is
                // that first "name" part. Split again by period and take the first value.
                sections = line.split("\\.");
                line = sections[0];

                // we don't care about case
                line = line.toUpperCase();

                // now we're ready to throw in the set
                out.add(line);
            }

        }

        return out;
    }

    /**
     * Modifies the sets such that they only contain the elements that are not common to each other.
     * @param set1 Will be modified to set1 \ set2
     * @param set2 Will be modified to set2 \ set1
     */
    private void reconcile(HashSet<String> set1, HashSet<String> set2) {
        set1.removeIf(set2::remove);
        set2.removeIf(set1::remove);
    }

    /**
     * Retrieves the results of the reconciliation for entries that appear in file1 but not in file2
     * @return A HashSet of Strings containing all the entries in file1 but not file2. Will be null if reconcile() has yet to be called.
     */
    public HashSet<String> getInFile1NotFile2() {
        return inFile1NotFile2;
    }

    /**
     * Retrieves the results of the reconciliation for entries that appear in file2 but not in file1
     * @return A HashSet of Strings containing all the entries in file2 but not file1. Will be null if reconcile() has yet to be called.
     */
    public HashSet<String> getInFile2NotFile1() {
        return inFile2NotFile1;
    }
}
