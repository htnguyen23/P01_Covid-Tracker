//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: COVIDTrackerTester.java
// Course: CS 300 Fall 2020
//
// Author: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

public class COVIDTrackerTester {

  public static void main(String[] args) {
    if (testAddTest()) {
      System.out.println("addTest() test passed.\n");
    } else {
      System.out.println("addTest() test failed.\n");
    }
    if (testRemoveIndividual()) {
      System.out.println("removeIndividual() test passed.\n");
    } else {
      System.out.println("removeIndividual() test failed.\n");
    }
    if (testGetPopStats()) {
      System.out.println("getPopStats() test passed.\n");
    } else {
      System.out.println("getPopStats() test failed.\n");
    }
    if (testGetIndividualStats()) {
      System.out.println("getPopStats() test passed.\n");
    } else {
      System.out.println("getPopStats() test failed.\n");
    }
  }

  /**
   * Checks whether addTest() works as expected
   * 
   * @return true if method functionality is verified, false otherwise
   */
  public static boolean testAddTest() {
    // CASE 1: two empty arrays -> true; also checking that arrays were updated properly
    String[] pos = new String[2];
    String[] neg = new String[2];
    if (!COVIDTracker.addTest(pos, neg, "AB1234", false) || neg[0] == null) {
      System.out.println("Problem: case 1.");
      return false;
    }
    if (!COVIDTracker.addTest(pos, neg, "CD2345", true) || pos[0] == null) {
      System.out.println("Problem: case 1.");
      return false;
    }
    // CASE 2: two arrays with space -> true
    if (!COVIDTracker.addTest(pos, neg, "CD2345", false) || neg[1] == null) {
      System.out.println("Problem: case 2.");
      return false;
    }
    // CASE 3: one full array but adding to one with space -> true
    if (!COVIDTracker.addTest(pos, neg, "EF3456", true) || pos[1] == null) {
      System.out.println("Problem: case 3.");
      return false;
    }
    // CASE 4: one array with space but adding to full one -> false
    String[] pos2 = new String[2];
    if (COVIDTracker.addTest(pos2, neg, "EF3456", false)) {
      System.out.println("Problem: case 4.");
      return false;
    }
    // CASE 5: two full arrays -> false
    if (COVIDTracker.addTest(pos, neg, "EF3456", true)) {
      System.out.println("Problem: case 5.");
      return false;
    }
    return true;
  }

  /**
   * Checks whether removeIndividual() works as expected
   * 
   * @return true if method functionality is verified, false otherwise
   */
  public static boolean testRemoveIndividual() {
    // CASE 1: empty arrays -> false (can't use 0 length array)
    String[] pos = new String[0];
    String[] neg = new String[0];
    if (COVIDTracker.removeIndividual(pos, neg, "AB1234")) {
      System.out.println("Problem: case 1.");
      return false;
    }
    // CASE 2: arrays without items -> false
    String[] pos2 = new String[4];
    String[] neg2 = new String[4];
    if (COVIDTracker.removeIndividual(pos2, neg2, "CD2345")) {
      System.out.println("Problem: case 2.");
      return false;
    }
    // CASE 3: arrays without matching id -> false
    String[] pos3 = {"AB1234", "AB1234", "GH4567", "IJ5678"};
    String[] neg3 = {"AB1234", "MN7890", "OP8901", "IJ5678"};
    if (COVIDTracker.removeIndividual(pos3, neg3, "EF3456")) {
      System.out.println("Problem: case 3.");
      return false;
    }
    // CASE 4: id found in one array -> true (checks that array correctly rearranged)
    if (!COVIDTracker.removeIndividual(pos3, neg3, "MN7890") || neg3[2] != "IJ5678"
      || neg3[3] != null) {
      System.out.println("Problem: case 4.");
      return false;
    }
    // CASE 5: id found in both array -> true
    if (!COVIDTracker.removeIndividual(pos3, neg3, "IJ5678") || pos3[3] != null) {
      System.out.println("Problem: case 5.");
      return false;
    }
    // CASE 6: id found in both array, multiple times -> true
    if (!COVIDTracker.removeIndividual(pos3, neg3, "AB1234") || neg3[1] != null
      || pos3[1] != null) {
      System.out.println("Problem: case 6.");
      System.out.println("pos: " + pos3[0] + pos3[1] + pos3[2] + pos3[3] + "\nneg: " + neg3[0]
        + neg3[1] + neg3[2] + neg3[3]);
      return false;
    }
    return true;
  }

  /**
   * Checks whether getPopStats() works as expected
   * 
   * @return true if method functionality is verified, false otherwise
   */
  public static boolean testGetPopStats() {
    // CASE 1: full arrays
    String[] pos = {"AB1234", "AB1234", "CD2345", "EF3456", "GH4567", "IJ5678"};
    String[] neg = {"AB1234", "IJ5678", "KL6789", "KL6789", "MN7890", "OP8901"};
    String result = COVIDTracker.getPopStats(pos, neg);
    String expected =
      "Total tests: 12\nTotal individuals tested: 8\nPercent positive tests: 50.0%\nPercent positive individuals: 62.5%";
    if (!result.equals(expected)) {
      System.out.println(result);
      return false;
    }
    // CASE 2: empty arrays
    String[] pos2 = new String[6];
    String[] neg2 = new String[6];
    result = COVIDTracker.getPopStats(pos2, neg2);
    expected =
      "Total tests: 0\nTotal individuals tested: 0\nPercent positive tests: 0.0%\nPercent positive individuals: 0.0%";
    if (!result.equals(expected)) {
      System.out.println(result);
      return false;
    }
    // CASE 3: all unique ids
    String[] pos3 = {"AB1234", "CD2345", "EF3456", "GH4567", "IJ5678", "KL6789"};
    String[] neg3 = {"MN7890", "OP8901", "QR9012", "ST0123", "UV1234", "WX2345"};
    result = COVIDTracker.getPopStats(pos3, neg3);
    expected = 
      "Total tests: 12\nTotal individuals tested: 12\nPercent positive tests: 50.0%\nPercent positive individuals: 50.0%";
    if (!result.equals(expected)) {
      System.out.println(result);
      return false;
    }
    return true;
  }

  /**
   * Checks whether getIndividualStats() works as expected
   * 
   * @return true if method functionality is verified, false otherwise
   */
  public static boolean testGetIndividualStats() {
    // CASE 1: full arrays
    String[] pos = {"AB1234", "AB1234", "CD2345", "EF3456", "GH4567", "IJ5678"};
    String[] neg = {"AB1234", "IJ5678", "KL6789", "KL6789", "MN7890", "OP8901"};
    String result = COVIDTracker.getIndividualStats(pos, neg, "EF3456");
    String expected = "Total tests: 1\nPositive: 1\nNegative: 0";
    if (!result.equals(expected)) {
      System.out.println(result);
      return false;
    }
    // CASE 2: no id found
    result = COVIDTracker.getIndividualStats(pos, neg, "QR9012");
    expected = "Total tests: 0\nPositive: 0\nNegative: 0";
    if (!result.equals(expected)) {
      System.out.println(result);
      return false;
    }
    // CASE 3: empty arrays
    String[] pos2 = new String[6];
    String[] neg2 = new String[6];
    result = COVIDTracker.getIndividualStats(pos2, neg2, "AB1234");
    expected = "Total tests: 0\nPositive: 0\nNegative: 0";
    if (!result.equals(expected)) {
      System.out.println(result);
      return false;
    }
    return true;
  }
}


