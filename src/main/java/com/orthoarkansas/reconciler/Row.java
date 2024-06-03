package com.orthoarkansas.reconciler;

/**
 * Container class to make the data more amicable to a CellValueFactory
 */
public class Row {
    private String s1;
    private String s2;

    /**
     * Constructor for a Row
     * @param s1 The entry on the left (file1) side of the row
     * @param s2 The entry on the right (file2) side of the row
     */
    public Row(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * Getter for the left (file1) side of the Row
     * @return the String s1
     */
    public String getS1() {
        return s1;
    }

    /**
     * Getter for the right (file2) side of the Row
     * @return the String s2
     */
    public String getS2() {
        return s2;
    }
}
