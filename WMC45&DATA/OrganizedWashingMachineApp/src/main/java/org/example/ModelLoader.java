package org.example;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ModelLoader {

    private J48 tree; // Single tree
    private Instances data;

    public Instances loadModel(String filePath) {
        try {
            // Load the dataset
            DataSource source = new DataSource(filePath);
            data = source.getDataSet();

            // Set the class attribute (last column as composite class)
            data.setClassIndex(data.numAttributes() - 1);

            // Build the J48 tree
            tree = new J48();
            tree.buildClassifier(data);

            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public J48 getTree() {
        return tree;
    }

}
