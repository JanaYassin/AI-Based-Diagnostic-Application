package org.example;
import weka.core.Instances;

public class Utils {

    public static double calculateEntropy(Instances data) {
        int[] classCounts = new int[data.numClasses()];// Array to store the count of each class
        for (int i = 0; i < data.numInstances(); i++) {
            int classIndex = (int) data.instance(i).classValue();// Get the class value of each instance
            classCounts[classIndex]++;// Increment the count for the corresponding class
        }


        double entropy = 0.0;
        for (int classCount : classCounts) {
            if (classCount > 0) {
                double probability = (double) classCount / data.numInstances();
                entropy -= probability * log2(probability);
            }
        }
        return entropy;
    }

    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

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

