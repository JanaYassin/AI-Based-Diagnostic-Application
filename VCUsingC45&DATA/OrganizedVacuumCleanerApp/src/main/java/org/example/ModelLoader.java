package org.example;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ModelLoader {

    private J48 tree;

    // Method to load the model from a specified file path
    public Instances loadModel(String filePath) {
        try {
            // Load data from the specified file path
            DataSource source = new DataSource(filePath);
            //Retrieves the dataset as an Instances object
            Instances data = source.getDataSet();
            // Set the class index to the last attribute
            data.setClassIndex(data.numAttributes() - 1);

            // Initialize and build the J48 decision tree classifier
            tree = new J48();
            tree.buildClassifier(data);

            // Calculate and print the entropy of the dataset
            double entropy = Utils.calculateEntropy(data);
            System.out.println("Entropy of the dataset: " + entropy);

            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Method to get the built J48 tree
    public J48 getTree() {
        return tree;
    }
}

