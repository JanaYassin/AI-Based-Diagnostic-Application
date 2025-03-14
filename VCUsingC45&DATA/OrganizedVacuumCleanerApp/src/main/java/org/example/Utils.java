package org.example;

import weka.core.Instances;

public class Utils {

    // Method to calculate the entropy of a dataset
    public static double calculateEntropy(Instances data) {
        int[] classCounts = new int[data.numClasses()];// Array to store the count of each class
        for (int i = 0; i < data.numInstances(); i++) {
            int classIndex = (int) data.instance(i).classValue();// Get the class value of the instance
            classCounts[classIndex]++; // Increment the count for this class
        }

        double entropy = 0.0;
        for (int classCount : classCounts) {
            if (classCount > 0) {
                double probability = (double) classCount / data.numInstances();// Calculate the probability of this class
                entropy -= probability * log2(probability);// Update the entropy value
            }
        }
        return entropy;
    }

    // Method to calculate the logarithm base 2 of a value
    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

    // Method to check if a string is numeric
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
