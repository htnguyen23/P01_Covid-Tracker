//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: COVIDTracker.java
// Course: CS 300 Fall 2020
//
// Author: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: Rudy Banerjee - Helped with an idea of how to compute the number of unique individuals
// in an array, and how to deal with null items.
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

/**
 * COVID Test Tracker methods that help manage data on COVID tests with a unique identifer and
 * wether or not the test is positive or negative.
 * 
 * @author Huong Nguyen
 */
public class COVIDTracker {

  /**
   * Adds ID to the appropriate test array if there is room.
   * 
   * @param pos   The current array of positive tests
   * @param neg   The current array of negative tests
   * @param id    The tested individual’s unique identifier String
   * @param isPos true if the test was positive, false otherwise
   * @return true if the new record was added, false otherwise
   */
  public static boolean addTest(String[] pos, String[] neg, String id, boolean isPos) {
    // checks if pos array and id is able to be used, and if there's space to for id to be added
    if (isPos) {
      if (pos.length == 0 || id == null || id.length() == 0) {
        return false;
      }
      if (pos[pos.length - 1] != null) {
        return false;
      } else {
        for (int i = 0; i < pos.length; i++) {
          if (pos[i] == null) {
            pos[i] = id;
            return true;
          }
        }
        return true;
      }
    } else {
      // checks if neg array and id is able to be used, and if there's space to for id to be added
      if (neg.length == 0 || id == null || id.length() == 0) {
        return false;
      }
      if (neg[neg.length - 1] != null) {
        return false;
      } else {
        for (int i = 0; i < pos.length; i++) {
          if (neg[i] == null) {
            neg[i] = id;
            return true;
          }
        }
        return true;
      }
    }
  }


  /**
   * Removes all occurrences of the provided indiviudal from both arrays, and compacts the arrays.
   * 
   * @param pos The current array of positive tests
   * @param neg The current array of negative tests
   * @param id  The tested individual’s unique identifier String
   * @return true if one or more records were removed, false if the ID was not found in either array
   */
  public static boolean removeIndividual(String[] pos, String[] neg, String id) {
    if (pos.length == 0 || neg.length == 0 || id == null || id.length() == 0) {
      return false;
    } else {
      int idFoundPos = 0;
      int idFoundNeg = 0;
      // checking and removing id from pos array
      for (int i = 0; i < pos.length; i++) {
        if (pos[i] == id) {
          idFoundPos += 1;
        }
      }
      if (idFoundPos > 0) {
        for (int x = 0; x <= idFoundPos; x++) {
          for (int i = 0; i < pos.length; i++) {
            if (pos[i] == id) {
              for (int y = i; y < pos.length - 1; y++) {
                pos[y] = pos[y + 1];
              }
            }
          }
          pos[pos.length - 1] = null;
        }
      }
      // checking and removing id from neg array
      for (int i = 0; i < neg.length; i++) {
        if (neg[i] == id) {
          idFoundNeg += 1;
        }
      }
      if (idFoundNeg > 0) {
        for (int x = 0; x <= idFoundNeg; x++) {
          for (int i = 0; i < neg.length; i++) {
            if (neg[i] == id) {
              for (int y = i; y < neg.length - 1; y++) {
                neg[y] = neg[y + 1];
              }
            }
          }
          neg[neg.length - 1] = null;
        }
      }
      if (idFoundNeg > 0 || idFoundPos > 0) {
        return true;
      } else {
        return false;
      }
    }
  }

  /**
   * Gives information on the total number of positive and negative tests, total number of unique
   * individuals across both arrays, proportion of positive tests, proportion of positive
   * individuals.
   * 
   * @param pos The current array of positive tests
   * @param neg The current array of negative tests
   * @return a formatted string that includes the test statistics including totals and proportions.
   */
  public static String getPopStats(String[] pos, String[] neg) {
    String popStats = "";
    // determine total number of pos and neg tests
    int numPos = 0;
    int numNeg = 0;
    for (int i = 0; i < pos.length; i++) {
      if (pos[i] != null)
        numPos += 1;
    }
    for (int i = 0; i < neg.length; i++) {
      if (neg[i] != null)
        numNeg += 1;
    }
    int totalTest = numPos + numNeg;
    // determine the total number of unique individuals across both arrays
    int numUnique = getNumUnique(pos, neg)[0];
    // determine the proportion of positive tests
    double posPro = 0;
    double posIndPro = 0;
    if (totalTest != 0) {
      posPro = ((double) numPos / (double) totalTest) * 100.0;
      // determine the proportion of individuals who have tested positive
      posIndPro = ((double) getNumUnique(pos, neg)[1] / (double) numUnique) * 100.0;
    }
    popStats = "Total tests: " + totalTest + "\nTotal individuals tested: " + numUnique
      + "\nPercent positive tests: " + posPro + "%\nPercent positive individuals: " + posIndPro
      + "%";
    return popStats;
  }

  /**
   * Gives the number of unique ids across both arrays.
   * 
   * @param pos The current array of positive tests
   * @param neg The current array of negative tests
   * @return an array containing values for [total unique id, total positive unique id, total
   *         negative unique id]
   */
  private static int[] getNumUnique(String[] pos, String[] neg) {
    int numUnique = 0;
    int posUnique = 0;
    int negUnique = 0;
    String[] unique = new String[pos.length + neg.length];
    int matchCount = 0;
    // look through pos array for unique id to add onto a combined array
    for (int i = 0; i < pos.length; i++) {
      matchCount = 0;
      if (pos[i] != null) {
        for (int y = 0; y < unique.length; y++) {
          if (pos[i] == unique[y])
            matchCount += 1;
        }
        if (matchCount == 0) {
          unique[i] = pos[i];
          posUnique += 1;
        }
      }
    }
    // look through neg array for unique id to add onto a combined array
    for (int i = 0; i < unique.length - pos.length; i++) {
      matchCount = 0;
      if (neg[i] != null) {
        for (int y = 0; y < unique.length; y++) {
          if (neg[i] == unique[y])
            matchCount += 1;
        }
        if (matchCount == 0) {
          unique[i + pos.length] = neg[i];
          negUnique += 1;
        }
      }
    }
    // count total number of unique elements in combined array
    for (int i = 0; i < unique.length; i++) {
      if (unique[i] != null)
        numUnique += 1;
    }
    int[] totals = {numUnique, posUnique, negUnique};
    return totals;
  }

  /**
   * Gives information on the total number of tests for an ID, and the total number of positive and
   * negative tests for that ID.
   * 
   * @param pos The current array of positive tests
   * @param neg The current array of negative tests
   * @param id  The tested individual’s unique identifier String
   * @return a formatted string that includes the individual's tests statistics
   */
  public static String getIndividualStats(String[] pos, String[] neg, String id) {
    String indStats = "";
    int totalTests = 0;
    int totalPos = 0;
    int totalNeg = 0;
    for (int i = 0; i < pos.length; i++) {
      if (id == pos[i]) {
        totalPos += 1;
      }
    }
    for (int i = 0; i < neg.length; i++) {
      if (id == neg[i]) {
        totalNeg += 1;
      }
    }
    totalTests = totalPos + totalNeg;
    indStats = "Total tests: " + totalTests + "\nPositive: " + totalPos + "\nNegative: " + totalNeg;
    return indStats;
  }
}

